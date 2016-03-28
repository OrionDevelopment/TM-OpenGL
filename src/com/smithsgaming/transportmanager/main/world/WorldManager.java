
package com.smithsgaming.transportmanager.main.world;

import java.util.Random;

/**
 * Created by marcf on 3/13/2016.
 */
public class WorldManager {

    public static WorldManager instance = new WorldManager();

    private World overgroundWorld = null;
    private World undergroundWorld = null;
    private boolean isWorldBeingLoaded;

    protected WorldManager() {
    }

    public void generateWorld() {
        if (isWorldBeingLoaded) {
            return;
        }
        isWorldBeingLoaded = true;

        WorldCoreData data = new WorldCoreData(500, 256, 500, new Random().nextLong());
        overgroundWorld = new WorldServer(data, World.WorldType.OVERGROUND);
        overgroundWorld.generate();
        undergroundWorld = new WorldServer(data, World.WorldType.UNDERGROUND);
        undergroundWorld.generate();

        isWorldBeingLoaded = false;
    }

    public void updateWorld() {
    }

    public World getOvergroundWorld() {
        return overgroundWorld;
    }

    public World getUndergroundWorld() {
        return undergroundWorld;
    }


}
