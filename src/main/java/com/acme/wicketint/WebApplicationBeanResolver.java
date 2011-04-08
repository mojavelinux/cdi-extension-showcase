package com.acme.wicketint;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.WebApplication;

public class WebApplicationBeanResolver
{
   @Inject
   private Instance<WebApplication> webApplicationProvider;
   
   @Inject @BuiltIn
   private Instance<WebApplication> builtInWebApplicationProvider;
   
   public WebApplication resolveWebApplication()
   {
      if (webApplicationProvider.isAmbiguous())
      {
         throw new WicketRuntimeException("WebApplication is ambiguous");
      }
      else if (webApplicationProvider.isUnsatisfied())
      {
         return builtInWebApplicationProvider.get();
      }
      else
      {
         return webApplicationProvider.get();
      }
   }
}