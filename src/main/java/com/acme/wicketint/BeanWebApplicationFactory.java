package com.acme.wicketint;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.wicket.protocol.http.IWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;

public class BeanWebApplicationFactory implements IWebApplicationFactory
{
   public WebApplication createApplication(WicketFilter filter)
   {
      BeanManager bm;
      try
      {
         System.out.println("Trying java:comp/BeanManager...");
         bm = (BeanManager) new InitialContext().lookup("java:comp/BeanManager");
      }
      catch (NamingException e)
      {
         try
         {
            System.out.println("Trying java:comp/env/BeanManager...");
            bm = (BeanManager) new InitialContext().lookup("java:comp/env/BeanManager");
         }
         catch (NamingException e2)
         {
            throw new RuntimeException("Could not locate BeanManager in JNDI");
         }
      }
      
      Bean<WebApplicationBeanResolver> resolverBean = (Bean<WebApplicationBeanResolver>) bm.resolve(bm.getBeans(WebApplicationBeanResolver.class));
      CreationalContext<WebApplicationBeanResolver> cc = bm.createCreationalContext(resolverBean);
      WebApplicationBeanResolver resolver = (WebApplicationBeanResolver) bm.getReference(resolverBean, WebApplicationBeanResolver.class, cc);
      WebApplication webapp = resolver.resolveWebApplication();
      cc.release();
      return webapp;
   }
}
