/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.ui.impl.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.jboss.forge.ui.converter.Converter;

public class ConverterProducer
{

   @Produces
   @Default
   @SuppressWarnings("rawtypes")
   public Converter produceConverter(InjectionPoint injectionPoint)
   {
      ParameterizedType ptype = (ParameterizedType) injectionPoint.getType();
      Type[] actualTypeArguments = ptype.getActualTypeArguments();
      return ConverterRegistryImpl.INSTANCE.getConverter((Class<?>) actualTypeArguments[0],
               (Class<?>) actualTypeArguments[1]);
   }
}
