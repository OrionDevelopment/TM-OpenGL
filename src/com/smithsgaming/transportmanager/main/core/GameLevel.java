package com.smithsgaming.transportmanager.main.core;

/**
 * Created by Tim on 29/03/2016.
 */
public enum GameLevel {

    SMALL("Small", 500, 500, 256),
    MEDIUM("Medium", 750, 750, 512),
    LARGE("Large", 1000, 1000, 1024);

    private int worldWidth;
    private int worldHeight;
    private int maxTileHeight;
    private String name;

    GameLevel(String name, int worldWidth, int worldHeight, int maxTileHeight) {
        this.name = name;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.maxTileHeight = maxTileHeight;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public int getMaxTileHeight() {
        return maxTileHeight;
    }

    public String getName() {
        return name;
    }
}
