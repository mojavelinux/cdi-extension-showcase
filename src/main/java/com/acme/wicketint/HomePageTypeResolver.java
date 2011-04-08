package com.acme.wicketint;

import java.util.Set;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;

public class HomePageTypeResolver
{
   @Inject
   private BeanManager beanManager;
   
   @Inject
   private Instance<Class<? extends Page>> homePageTypeProvider;
   
   @SuppressWarnings("unchecked")
   public Class<? extends Page> resolveHomePageType()
   {
      Set<Bean<?>> candidates = beanManager.getBeans(Page.class, HomePageLiteral.INSTANCE);
      if (candidates.size() == 1)
      {
         return (Class<? extends Page>) candidates.iterator().next().getBeanClass();
      }
      
      if (candidates.size() > 1)
      {
         throw new WicketRuntimeException("Only one Page class can be marked with the @WicketHomePage annotation.");
      }

      if (homePageTypeProvider.isUnsatisfied())
      {
         throw new WicketRuntimeException("Must provide a producer of type Class<? extends Page> or mark a Page bean with the @WicketHomePage annotation.");
      }
      
      if (homePageTypeProvider.isAmbiguous())
      {
         throw new WicketRuntimeException("Home page type is ambigious");
      }
      
      return homePageTypeProvider.get();
   }
}
