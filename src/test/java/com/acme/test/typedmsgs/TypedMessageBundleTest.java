package com.acme.test.typedmsgs;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.solder.messages.MessageBundle;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class TypedMessageBundleTest {
    @Deployment
    public static Archive<?> createArchive() {
        return ShrinkWrap.create(JavaArchive.class)
            .addPackage(AccountMessages.class.getPackage())
            .addClass("org.jboss.seam.solder.messages.TypedMessageBundleProducer")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject @MessageBundle
    private AccountMessages messages;
    
    @Test
    public void shouldReturnInterpolatedMessage() {
        Assert.assertEquals("Insufficient funds. Overdrafted by 100.00!", messages.insufficientFunds(new BigDecimal(100)));
    }
}
