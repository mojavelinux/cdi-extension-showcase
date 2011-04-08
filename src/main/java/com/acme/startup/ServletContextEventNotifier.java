package com.acme.startup;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletContextEventNotifier implements ServletContextListener {

    @Inject @Any
    private Event<ServletContext> servletContextEvent;
    
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        servletContextEvent.select(InitializedLiteral.INSTANCE).fire(event.getServletContext());
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        servletContextEvent.select(DestroyedLiteral.INSTANCE).fire(event.getServletContext());
    }
}
