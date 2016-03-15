
package com.smithsgaming.transportmanager.main.world;

import com.smithsgaming.transportmanager.main.world.saveable.*;

/**
 * Created by marcf on 3/13/2016.
 */
public class WorldManager {

    public static WorldManager instance = new WorldManager();

    private World loadedWorld = null;
    private boolean isWorldBeingLoaded;
    private TileSaveHandler saveHandler;

    protected WorldManager() {
    }

    public TileSaveHandler getSaveHandler() {
        return saveHandler;
    }

    public void generateWorld() {
        if (isWorldBeingLoaded)
            return;

        isWorldBeingLoaded = true;

        loadedWorld = new World();
        loadedWorld.initializeChunkMap();
        loadedWorld.generate();

        isWorldBeingLoaded = false;
    }

    public World getLoadedWorld () {
        return loadedWorld;
    }
}
