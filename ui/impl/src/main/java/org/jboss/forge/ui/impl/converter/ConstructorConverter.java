/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.ui.impl.converter;

import java.lang.reflect.Constructor;

import javax.enterprise.inject.Vetoed;

import org.jboss.forge.ui.converter.Converter;

/**
 * Converter that uses a constructor
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 *
 * @param <S>
 * @param <T>
 */

@Vetoed
public class ConstructorConverter<S, T> implements Converter<S, T>
{
   private final Constructor<T> constructor;

   public ConstructorConverter(Constructor<T> constructor)
   {
      super();
      this.constructor = constructor;
   }

   public T convert(S source) throws Exception
   {
      return constructor.newInstance(source);
   }
}
