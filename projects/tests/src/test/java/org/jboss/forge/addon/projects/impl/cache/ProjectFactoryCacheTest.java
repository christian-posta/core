package org.jboss.forge.addon.projects.impl.cache;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.resource.DirectoryResource;
import org.jboss.forge.arquillian.AddonDependencies;
import org.jboss.forge.arquillian.AddonDependency;
import org.jboss.forge.arquillian.archive.AddonArchive;
import org.jboss.forge.furnace.container.simple.Service;
import org.jboss.forge.furnace.container.simple.lifecycle.SimpleContainer;
import org.jboss.forge.furnace.util.Predicate;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ProjectFactoryCacheTest
{
   @Deployment
   @AddonDependencies({
            @AddonDependency(name = "org.jboss.forge.furnace.container:simple"),
            @AddonDependency(name = "org.jboss.forge.addon:maven"),
            @AddonDependency(name = "org.jboss.forge.addon:projects")
   })
   public static AddonArchive getDeployment()
   {
      return ShrinkWrap
               .create(AddonArchive.class)
               .addAsServiceProvider(Service.class, ProjectFactoryCacheTest.class);
   }

   private ProjectFactory projectFactory;

   @Before
   public void setUp()
   {
      projectFactory = SimpleContainer.getServices(getClass().getClassLoader(), ProjectFactory.class).get();
   }

   @Test
   public void testFindProjectFromCache() throws Exception
   {
      Project project = projectFactory.createTempProject();

      Assert.assertNotNull(project);
      Project found = projectFactory.findProject(project.getRoot());
      Assert.assertNotNull(found);
      Assert.assertSame(project, found);

      Assert.assertNull(projectFactory.findProject(project.getRoot(), new Predicate<Project>()
      {
         @Override
         public boolean accept(Project type)
         {
            return false;
         }
      }));

      Project found2 = projectFactory
               .findProject(project.getRoot().reify(DirectoryResource.class).getChildDirectory("src/main/java"));
      Assert.assertNotNull(found2);
      Assert.assertSame(found, found2);

      Project project2 = projectFactory.createTempProject();
      Assert.assertNotSame(found2, project2);
      Assert.assertNotEquals(found2.getRoot().getFullyQualifiedName(), project2.getRoot()
               .getFullyQualifiedName());

      project.getRoot().delete(true);
   }

}
