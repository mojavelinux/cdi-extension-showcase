package com.acme.jsfcontext;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

public class FacesContextProducer {
    @Produces @RequestScoped
    public FacesContext proxyFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}
