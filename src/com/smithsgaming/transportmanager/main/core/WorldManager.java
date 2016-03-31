
package com.smithsgaming.transportmanager.main.core;

import com.smithsgaming.transportmanager.main.world.*;
import com.smithsgaming.transportmanager.main.world.generation.*;

/**
 * Created by marcf on 3/13/2016.
 */
public class WorldManager {

    public static WorldManager instance = new WorldManager();

    private World overgroundWorld = null;
    private World undergroundWorld = null;
    private boolean isWorldBeingLoaded;

    protected WorldManager () {
    }

    public void generateWorld () {
        if (isWorldBeingLoaded) {
            return;
        }
        isWorldBeingLoaded = true;
        WorldGenManager.WorldGenThread thread = WorldGenManager.instance.getNewWorldGenThread();
        thread.run();
        overgroundWorld = thread.getWorld(World.WorldType.OVERGROUND);
        undergroundWorld = thread.getWorld(World.WorldType.UNDERGROUND);
        isWorldBeingLoaded = false;
    }

    public void updateWorld () {
        if (overgroundWorld != null) {
            overgroundWorld.update();
        }
        if (undergroundWorld != null) {
            undergroundWorld.update();
        }
    }

    public World getOvergroundWorld () {
        return overgroundWorld;
    }

    public World getUndergroundWorld () {
        return undergroundWorld;
    }


}
