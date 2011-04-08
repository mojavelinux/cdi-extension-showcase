package com.acme.beanrequires;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.jboss.seam.solder.reflection.Reflections;

public class RequiresExtension implements Extension {
    <X> void processAnnotatedType(@Observes ProcessAnnotatedType<X> event) {
        AnnotatedType<X> type = event.getAnnotatedType();
        if (type.isAnnotationPresent(Requires.class)) {
            for (String required : type.getAnnotation(Requires.class).value()) {
                try {
                    Reflections.classForName(required, type.getJavaClass().getClassLoader());
                } catch (ClassNotFoundException e) {
                    event.veto();
                }
            }
        }
    }
}
