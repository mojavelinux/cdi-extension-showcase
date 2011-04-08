package com.acme.test.jsfcontext;

import java.util.Date;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class PageBean {
    private Date created;
    
    @Inject
    public void initialize() {
        created = new Date();
    }
    
    public Date getCreated() {
        return created;
    }
}
