package com.acme.test.startup;

import javax.enterprise.event.Observes;

import com.acme.startup.Initialized;

public class ApplicationInitializer {
    public void onStartup(@Observes @Initialized Object ctx) {
        System.out.println("Performing initialization at application startup");
    }
}
