package org.jboss.forge.addon.manager.impl.commands;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jboss.forge.addon.manager.AddonManager;
import org.jboss.forge.addon.ui.UICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIValidationContext;
import org.jboss.forge.addon.ui.input.UIInput;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.util.Metadata;
import org.jboss.forge.furnace.addons.AddonId;

@Singleton
public class AddonInstallCommand implements UICommand
{

   @Inject
   private AddonManager addonManager;

   @Inject
   private UIInput<String> groupId;

   @Inject
   private UIInput<String> name;

   @Inject
   private UIInput<String> version;

   @Override
   public UICommandMetadata getMetadata()
   {
      return Metadata.forCommand(getClass()).name(AddonCommandConstants.ADDON_INSTALL_COMMAND_NAME)
               .description(AddonCommandConstants.ADDON_INSTALL_COMMAND_DESCRIPTION);
   }

   @Override
   public boolean isEnabled(UIContext context)
   {
      return true;
   }

   @Override
   public void initializeUI(UIBuilder builder) throws Exception
   {
      initializeGroupIdInput(builder);
      initializeNameInput(builder);
      initializeVersionInput(builder);
   }

   private void initializeGroupIdInput(UIBuilder builder)
   {
      groupId.setLabel("Group Id:");
      groupId.setRequired(true);
      builder.add(groupId);
   }

   private void initializeNameInput(UIBuilder builder)
   {
      name.setLabel("Name:");
      name.setRequired(true);
      builder.add(name);
   }

   private void initializeVersionInput(UIBuilder builder)
   {
      version.setLabel("Version:");
      version.setRequired(true);
      builder.add(version);
   }

   @Override
   public void validate(UIValidationContext context)
   {
   }

   @Override
   public Result execute(UIContext context)
   {
      String coordinates = getCoordinates();
      try
      {
         addonManager.install(AddonId.fromCoordinates(coordinates)).perform();
         return Results.success("Addon " + coordinates + " was installed succesfully.");
      }
      catch (Throwable t)
      {
         return Results.fail("Addon " + coordinates + " could not be installed.", t);
      }
   }

   protected String getCoordinates()
   {
      return groupId.getValue() + ':' + name.getValue() + ',' + version.getValue();
   }

}