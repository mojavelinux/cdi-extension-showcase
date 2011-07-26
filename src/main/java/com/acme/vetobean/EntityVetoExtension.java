package com.acme.vetobean;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.persistence.Entity;

public class EntityVetoExtension implements Extension {
    <X> void processAnnotatedType(@Observes ProcessAnnotatedType<X> event) {
        AnnotatedType<X> type = event.getAnnotatedType();
        if (type.isAnnotationPresent(Entity.class)) {
            event.veto();
        }
    }
}
