package com.acme.test.beanrequires;

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

import com.acme.beanrequires.Requires;
import com.acme.beanrequires.RequiresExtension;

@RunWith(Arquillian.class)
public class RequiresExtensionTest {
    @Deployment
    public static Archive<?> createArchive() {
        return ShrinkWrap.create(JavaArchive.class)
            .addPackage(Requires.class.getPackage())
            .addClasses(SampleBeanA.class, SampleBeanB.class)
            .addAsServiceProvider(Extension.class, RequiresExtension.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject Instance<SampleBeanA> a;
    
    @Inject Instance<SampleBeanB> b;
    
    @Test
    public void shouldNotVetoBeanIfRequiredClassIsPresent() {
        Assert.assertFalse(a.isUnsatisfied());
    }
    
    @Test
    public void shouldVetoBeanIfRequiredClassMissing() {
        Assert.assertTrue(b.isUnsatisfied());
    }
}
