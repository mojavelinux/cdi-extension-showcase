package com.acme.test.jsfcontext;

import javax.enterprise.inject.Instance;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
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

import com.acme.jsfcontext.FacesContextProducer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Arquillian.class)
public class FacesContextProducerTest {
    @Deployment
    public static Archive<?> createArchive() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClass(FacesContextProducer.class)
            .addClass(MockFacesContextHelper.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject Instance<FacesContext> facesContext;
    
    @Test
    public void shouldProxyFacesContext() {
        FacesContext ctx = mock(FacesContext.class);
        when(ctx.getCurrentPhaseId()).thenReturn(PhaseId.RESTORE_VIEW);
        MockFacesContextHelper.setFacesContext(ctx);
        Assert.assertEquals(PhaseId.RESTORE_VIEW, facesContext.get().getCurrentPhaseId());
    }
}
