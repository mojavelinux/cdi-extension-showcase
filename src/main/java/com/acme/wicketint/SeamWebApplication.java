package com.acme.wicketint;

import javax.inject.Inject;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

@BuiltIn
public class SeamWebApplication extends WebApplication
{
   @Inject
   private HomePageTypeResolver homePageResolver;
   
   @Override
   public Class<? extends Page> getHomePage()
   {
      return homePageResolver.resolveHomePageType();
   }
}
