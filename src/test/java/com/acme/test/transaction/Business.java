package com.acme.test.transaction;

import com.acme.transaction.Transactional;

@Transactional
public class Business {
    
    public void operateInTransaction() {
        System.out.println("Running operation in " + Business.class.getSimpleName());
    }
}
