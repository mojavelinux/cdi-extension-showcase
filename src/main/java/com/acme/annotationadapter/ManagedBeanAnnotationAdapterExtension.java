package com.acme.annotationadapter;

import javax.annotation.ManagedBean;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.inject.Named;

import org.jboss.seam.solder.literal.NamedLiteral;
import org.jboss.seam.solder.reflection.annotated.AnnotatedTypeBuilder;

public class ManagedBeanAnnotationAdapterExtension implements Extension {
    <X> void processType(@Observes ProcessAnnotatedType<X> event) {
        AnnotatedType<X> original = event.getAnnotatedType();
        if (original.isAnnotationPresent(ManagedBean.class) &&
                !original.isAnnotationPresent(Named.class)) {
            AnnotatedType<X> modified = new AnnotatedTypeBuilder<X>()
                  .readFromType(event.getAnnotatedType(), true)
                  .addToClass(new NamedLiteral(original.getAnnotation(ManagedBean.class).value()))
                  .create();
            event.setAnnotatedType(modified);
        }
    }
}
