package com.acme.test.typedmsgs;

import java.math.BigDecimal;

import org.jboss.seam.solder.messages.Message;
import org.jboss.seam.solder.messages.MessageBundle;

@MessageBundle
public interface AccountMessages {
    @Message("Insufficient funds. Overdrafted by %.02f!")
    String insufficientFunds(BigDecimal overdraftAmount);
}
