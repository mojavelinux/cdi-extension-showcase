package com.acme.test.exactbean;

import javax.enterprise.inject.spi.Extension;
import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acme.exactbean.Exact;
import com.acme.exactbean.ExactExtension;

@RunWith(Arquillian.class)
public class ExactExtensionTest {
    @Deployment
    public static Archive<?> createArchive() {
        return ShrinkWrap.create(JavaArchive.class)
            .addPackage(Exact.class.getPackage())
            .addClasses(Processor.class, Service.class, ServiceA.class, ServiceB.class)
            .addAsServiceProvider(Extension.class, ExactExtension.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject Processor processor;
    
    @Test
    public void shouldUseExactBean() {
        Assert.assertTrue(processor.isUsingService(ServiceA.class));
    }
}
