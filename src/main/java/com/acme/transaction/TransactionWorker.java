package com.acme.transaction;

import javax.transaction.UserTransaction;

public abstract class TransactionWorker {
    public abstract Object doWork() throws Exception;
    
    public Object workInTransaction(UserTransaction tx) throws Exception {
        Object result = null;
        System.out.println("Beginning transaction");
        tx.begin();
        try {
            result = doWork();
            System.out.println("Committing transaction");
            tx.commit();
        }
        catch (Exception e) {
            System.out.println("Rolling back transaction");
            tx.rollback();
        }
        
        return result;
    }
}
