package com.acme.test.transaction;

import javax.ejb.TransactionAttribute;

@TransactionAttribute
public class EjbLighterBusiness {
    public void operateInTransaction() {
        System.out.println("Running operation in " + EjbLighterBusiness.class.getSimpleName());
    }
}