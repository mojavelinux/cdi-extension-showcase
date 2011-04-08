package com.acme.test.transaction;

import javax.enterprise.inject.spi.Extension;
import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.spec.cdi.beans.BeansDescriptor;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acme.transaction.TransactionExtension;
import com.acme.transaction.TransactionInterceptor;
import com.acme.transaction.Transactional;

@RunWith(Arquillian.class)
public class TransactionInterceptorTest {
    @Deployment
    public static Archive<?> createArchive() {
        BeansDescriptor beansXml = Descriptors.create(BeansDescriptor.class);
        return ShrinkWrap.create(WebArchive.class)
            .addPackage(Transactional.class.getPackage())
            .addClasses(Business.class, EjbLighterBusiness.class)
            .addAsServiceProvider(Extension.class, TransactionExtension.class)
            // work around SHRINKWRAP-266
            .addAsResource(new StringAsset(TransactionExtension.class.getName()), "META-INF/services/" + Extension.class.getName())
            .addAsWebResource(new StringAsset(beansXml.interceptor(TransactionInterceptor.class).exportAsString()), beansXml.getDescriptorName());
    }

    @Inject Business business;
    
    @Inject EjbLighterBusiness ejbLighterBusiness;
    
    @Test
    public void shouldWrapInTransaction() {
        business.operateInTransaction();
        ejbLighterBusiness.operateInTransaction();
    }
}
