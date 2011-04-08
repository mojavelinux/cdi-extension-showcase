package com.acme.test.exactbean;

import javax.inject.Inject;

import com.acme.exactbean.Exact;

public class Processor {
    @Inject @Exact(ServiceA.class)
    private Service service;
    
    public boolean isUsingService(Class<? extends Service> clazz) {
        return clazz.getSimpleName().equals(service.toString());
    }
}
