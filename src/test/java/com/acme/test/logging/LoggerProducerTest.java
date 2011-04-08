package com.acme.test.logging;

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
import org.slf4j.Logger;

import com.acme.logging.Category;
import com.acme.logging.LoggerProducer;

@RunWith(Arquillian.class)
public class LoggerProducerTest {
    @Deployment
    public static Archive<?> createArchive() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClass(LoggerProducer.class)
            .addPackage(RegistrationLogger.class.getPackage())
            .addClass("org.jboss.seam.solder.logging.LoggerProducer")
            .addClass("org.jboss.seam.solder.logging.TypedMessageLoggerProducer")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject @Category Logger logger;
    
    @Inject @Category("another") Logger anotherLogger;
    
    @Inject @org.jboss.seam.solder.logging.Category("registration") RegistrationLogger registrationLogger;
    
    @Test
    public void shouldInjectLogger() {
        Assert.assertEquals(LoggerProducerTest.class.getName(), logger.getName());
        logger.info("Hey there!");
        
        Assert.assertEquals("another", anotherLogger.getName());
        anotherLogger.warn("Hey there, again!");
        
        registrationLogger.userRegistered("ike");
        registrationLogger.minorRegistered("junior");
    }
}
