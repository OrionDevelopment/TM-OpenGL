package com.smithsgaming.transportmanager.main.player;

import java.nio.channels.*;

/**
 * Created by marcf on 3/13/2016.
 */
public class GamePlayer {
    private String displayName;
    private Channel connectionChannel;
    private int currentScore = 0;

    public GamePlayer(String displayName, Channel connectionChannel) {
        this.displayName = displayName;
        this.connectionChannel = connectionChannel;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Channel getConnectionChannel() {
        return connectionChannel;
    }

    public int getCurrentScore() {
        return currentScore;
    }
}
