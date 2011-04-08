package com.acme.transaction;

import javax.ejb.TransactionAttribute;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.jboss.seam.solder.reflection.annotated.AnnotatedTypeBuilder;

public class TransactionExtension implements Extension {
    <X> void processAnnotatedType(@Observes ProcessAnnotatedType<X> e) {
        AnnotatedType<X> type = e.getAnnotatedType();
        AnnotatedTypeBuilder<X> builder = null;
        boolean ejb = EjbApi.isEjb(type);
        if (type.isAnnotationPresent(TransactionAttribute.class) && !ejb) {
            builder = new AnnotatedTypeBuilder<X>()
                    .readFromType(type).addToClass(TransactionalLiteral.INSTANCE);
        }
        for (AnnotatedMethod<? super X> m : type.getMethods()) {
            if (m.isAnnotationPresent(TransactionAttribute.class) && !ejb) {
                if (builder == null) {
                    builder = new AnnotatedTypeBuilder<X>().readFromType(type);
                }
                builder.addToMethod(m, TransactionalLiteral.INSTANCE);
            }
        }

        if (builder != null) {
            e.setAnnotatedType(builder.create());
        }
    }
}