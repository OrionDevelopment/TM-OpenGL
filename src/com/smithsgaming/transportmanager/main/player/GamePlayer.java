package com.smithsgaming.transportmanager.main.player;

import io.netty.channel.*;

import java.io.*;

/**
 * Created by marcf on 3/13/2016.
 */
public class GamePlayer implements Serializable {

    public static final GamePlayer current = new GamePlayer("Marc", null);

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
