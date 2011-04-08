package com.acme.test.conversation;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import com.acme.conversation.Begin;
import com.acme.conversation.End;

@Named
@ConversationScoped
public class BookingAgent {
    
    private Integer packageId;
    
    @Begin
    public void selectPackage(Integer id) {
        System.out.println("Selecting package id " + id);
        this.packageId = id;
    }
    
    @End
    public void purchase() {
        System.out.println("Purchasing package id " + packageId);
    }
}
