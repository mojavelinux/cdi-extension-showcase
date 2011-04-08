package com.acme.importbeans;

import java.security.SecureRandom;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

public class ImportBeansExtension implements Extension {
    void beforeBeanDiscovery(@Observes BeforeBeanDiscovery event, BeanManager beanManager) {
        event.addAnnotatedType(beanManager.createAnnotatedType(SecureRandom.class));
    }
}
