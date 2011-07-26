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

import com.acme.vetobean.Veto;
import com.acme.vetobean.VetoExtension;

@RunWith(Arquillian.class)
public class VetoExtensionTest {
    @Deployment
    public static Archive<?> createArchive() {
        return ShrinkWrap.create(JavaArchive.class)
            .addPackage(Veto.class.getPackage())
            .addClass(SampleBean.class)
            .addAsServiceProvider(Extension.class, VetoExtension.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject Instance<SampleBean> sampleBean;
    
    @Test
    public void shouldVetoBean() {
        Assert.assertTrue(sampleBean.isUnsatisfied());
    }
}
