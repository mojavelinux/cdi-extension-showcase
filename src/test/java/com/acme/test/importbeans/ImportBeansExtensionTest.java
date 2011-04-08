package com.acme.test.importbeans;

import java.security.SecureRandom;

import javax.enterprise.inject.Instance;
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

import com.acme.importbeans.ImportBeansExtension;

@RunWith(Arquillian.class)
public class ImportBeansExtensionTest {
    @Deployment
    public static Archive<?> createArchive() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClass(ImportBeansExtension.class)
            .addAsServiceProvider(Extension.class, ImportBeansExtension.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject Instance<SecureRandom> random;
    
    @Test
    public void shouldRegisterSecureRandomAsBean() {
        Assert.assertFalse(random.isUnsatisfied());
        System.out.println(random.get().nextInt());
    }
}
