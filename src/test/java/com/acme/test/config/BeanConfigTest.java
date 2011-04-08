package com.acme.test.config;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class BeanConfigTest {
    @Deployment
    public static Archive<?> createArchive() {
        String seamBeansXml = IOUtil.getContents("src/test/resources/com/acme/test/config/seam-beans.xml");
        
        return ShrinkWrap.create(JavaArchive.class)
            .addClass(StatusUpdater.class)
            .addAsManifestResource(new StringAsset(seamBeansXml), "seam-beans.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject StatusUpdater statusUpdater;
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldSetBeanProperty() {
        String status = "I'm on stage speaking about CDI extensions. However, this post is too long.";
        statusUpdater.post("dan", status);
    }
}
