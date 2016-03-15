
package com.smithsgaming.transportmanager.main.world;

/**
 * Created by marcf on 3/13/2016.
 */
public class WorldManager {

    public static WorldManager instance = new WorldManager();

    private World loadedWorld = null;
    private boolean isWorldBeingLoaded;

    protected WorldManager() {
    }


}
