package com.acme.test.config.princessrescue.effective;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import com.acme.test.config.princessrescue.Room;

@SessionScoped
@Room("start")
public class GameRoom {
    @Inject @Room("emptyRoom")
    private GameRoom north;
}
