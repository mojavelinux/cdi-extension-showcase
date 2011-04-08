package com.acme.test.logging;

import org.jboss.logging.Logger.Level;
import org.jboss.seam.solder.messages.Message;
import org.jboss.seam.solder.logging.Log;
import org.jboss.seam.solder.logging.MessageLogger;

@MessageLogger
public interface RegistrationLogger {
    @Log @Message("Successfully registered user named %s")
    void userRegistered(String username);
    
    @Log(level = Level.WARN) @Message("Registering a minor user named %s")
    void minorRegistered(String username);
}
