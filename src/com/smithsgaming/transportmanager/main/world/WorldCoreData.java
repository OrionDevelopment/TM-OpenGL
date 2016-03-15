package com.smithsgaming.transportmanager.main.world;

import java.io.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class WorldCoreData implements Serializable {

    public int worldWidth;
    public int worldHeight;
    public int worldLength;

    public long worldSeed;

    public WorldCoreData (int worldWidth, int worldHeight, int worldLength, long worldSeed) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.worldLength = worldLength;
        this.worldSeed = worldSeed;
    }

    public WorldCoreData () {
    }

    public int getWorldWidth () {
        return worldWidth;
    }

    public int getWorldHeight () {
        return worldHeight;
    }

    public int getWorldLength () {
        return worldLength;
    }

    public long getWorldSeed () {
        return worldSeed;
    }
}
