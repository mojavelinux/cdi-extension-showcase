package com.acme.test.vetobean;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.Extension;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acme.vetobean.EntityVetoExtension;

@RunWith(Arquillian.class)
public class EntityVetoExtensionTest {
    @Deployment
    public static Archive<?> createArchive() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClass(EntityVetoExtension.class)
            .addClass(SampleEntity.class)
            .addAsServiceProvider(Extension.class, EntityVetoExtension.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject Instance<SampleEntity> sampleEntity;
    
    @Test
    public void shouldVetoEntity() {
        Assert.assertTrue(sampleEntity.isUnsatisfied());
    }
}