package com.acme.test.jsfcontext;

import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextWrapper;

public abstract class MockFacesContextHelper extends FacesContextWrapper {
    public static void setFacesContext(FacesContext ctx) {
        setCurrentInstance(ctx);
    }
}
