
package com.smithsgaming.transportmanager.main.world;

import java.util.*;

/**
 * Created by marcf on 3/13/2016.
 */
public class WorldManager {

    public static WorldManager instance = new WorldManager();

    private World loadedWorld = null;
    private boolean isWorldBeingLoaded;

    protected WorldManager() {
    }

    public void generateWorld() {
        if (isWorldBeingLoaded) {
            return;
        }
        isWorldBeingLoaded = true;

        loadedWorld = new WorldServer(new WorldCoreData(500, 256, 500, new Random().nextLong()));
        loadedWorld.generate();

        isWorldBeingLoaded = false;
    }

    public World getLoadedWorld() {
        return loadedWorld;
    }


}
