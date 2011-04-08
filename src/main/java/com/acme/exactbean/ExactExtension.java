package com.acme.exactbean;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.jboss.seam.solder.reflection.annotated.AnnotatedTypeBuilder;

public class ExactExtension implements Extension {
    <X> void processAnnotatedType(@Observes ProcessAnnotatedType<X> e)
    {
       AnnotatedType<X> type = e.getAnnotatedType();
       AnnotatedTypeBuilder<X> builder = null;
       for (AnnotatedField<? super X> f : type.getFields())
       {
          if (f.isAnnotationPresent(Exact.class))
          {
             Class<?> exactType = f.getAnnotation(Exact.class).value();
             if (builder == null) {
                builder = new AnnotatedTypeBuilder<X>().readFromType(type);
             }
             builder.overrideFieldType(f, exactType);
          }
       }

       if (builder != null) {
          e.setAnnotatedType(builder.create());
       }
    }
}
