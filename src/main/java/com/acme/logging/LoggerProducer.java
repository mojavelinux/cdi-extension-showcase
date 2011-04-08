package com.acme.logging;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerProducer
{
   @Produces @Category
   public Logger produceLog(InjectionPoint injectionPoint)
   {
      String category = null;
      category = injectionPoint.getAnnotated().getAnnotation(Category.class).value();
      if (category.isEmpty())
      {
         category = injectionPoint.getMember().getDeclaringClass().getName();
      }
      return LoggerFactory.getLogger(category);
   }
}