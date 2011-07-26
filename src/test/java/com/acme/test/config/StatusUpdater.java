package com.acme.test.config;

public class StatusUpdater {
    private int maxStatusLength = 140;
    
    public void post(String username, String status) {
        if (status.length() > maxStatusLength) {
            throw new IllegalArgumentException("Post too long!");
        }
        System.out.println(status);
    }
}
