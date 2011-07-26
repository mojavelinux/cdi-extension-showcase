package com.acme.test.annotationadapter;

import static org.junit.Assert.assertTrue;

import javax.enterprise.inject.spi.Extension;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acme.annotationadapter.FacesAnnotationsAdapterExtension;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com>Lincoln Baxter, III</a>
 */
@RunWith(Arquillian.class)
public class FacesAnnotationsAdapterExtensionTest {

    @Deployment
    public static JavaArchive createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class).addClasses(ImproperlyAnnotatedBean.class, FacesAnnotationsAdapterExtension.class)
            .addAsServiceProvider(Extension.class, FacesAnnotationsAdapterExtension.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void testImproperlyAnnotatedClassIsCaptured() {
        assertTrue(FacesAnnotationsAdapterExtension.getAliasedbeans().containsKey(ImproperlyAnnotatedBean.class));
    }
}
