package com.acme.test.annotationadapter;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acme.annotationadapter.ManagedBeanAnnotationAdapterExtension;

@RunWith(Arquillian.class)
public class ManagedBeanAnnotationAdapterTest {
    @Deployment
    public static Archive<?> createArchive() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClass(ManagedBeanAnnotationAdapterExtension.class)
            .addClasses(BeanWithExplicitName.class, BeanWithImplicitName.class)
            .addAsServiceProvider(Extension.class, ManagedBeanAnnotationAdapterExtension.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject BeanManager beanManager;
    
    @Test
    public void shouldRegisterBeanName() {
        Bean<?> explicit = beanManager.resolve(beanManager.getBeans(BeanWithExplicitName.class));
        Assert.assertFalse(explicit.getBeanClass().isAnnotationPresent(Named.class));
        Assert.assertFalse(beanManager.getBeans("explicit").isEmpty());
        Assert.assertFalse(beanManager.getBeans("beanWithImplicitName").isEmpty());
    }
}
