package com.smithsgaming.transportmanager.client.world;

import com.smithsgaming.transportmanager.main.world.*;
import com.smithsgaming.transportmanager.main.world.chunk.*;
import javafx.util.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class WorldClientManager {
    public static WorldClientManager instance = new WorldClientManager();

    private WorldClient overgroundWorld;
    private WorldClient undergroundWorld;

    protected WorldClientManager() {
    }

    public void initializeWorld(WorldCoreData data) {
        this.overgroundWorld = new WorldClient(data, World.WorldType.OVERGROUND);
        this.undergroundWorld = new WorldClient(data, World.WorldType.UNDERGROUND);
    }

    public Pair<Integer, Integer> getNextChunkToSyncForWorld(World.WorldType type) {
        if (type == World.WorldType.OVERGROUND) {
            for (int x = 0; x < overgroundWorld.getCoreData().getWorldWidth() / Chunk.chunkSize + 1; x++) {
                for (int z = 0; z < overgroundWorld.getCoreData().getWorldWidth() / Chunk.chunkSize + 1; z++) {
                    if (!overgroundWorld.getLoadedStateForChunk(x, z)) {
                        return new Pair<>(x, z);
                    }
                }
            }
        } else if (type == World.WorldType.UNDERGROUND) {
            for (int x = 0; x < undergroundWorld.getCoreData().getWorldWidth() / Chunk.chunkSize + 1; x++) {
                for (int z = 0; z < undergroundWorld.getCoreData().getWorldWidth() / Chunk.chunkSize + 1; z++) {
                    if (!undergroundWorld.getLoadedStateForChunk(x, z)) {
                        return new Pair<>(x, z);
                    }
                }
            }
        }
        return null;
    }

    public WorldClient getOvergroundWorld() {
        return overgroundWorld;
    }

    public WorldClient getUndergroundWorld() {
        return undergroundWorld;
    }

    public WorldClient getWorld(World.WorldType type) {
        if (type == World.WorldType.OVERGROUND) {
            return overgroundWorld;
        } else if (type == World.WorldType.UNDERGROUND) {
            return undergroundWorld;
        }
        return null;
    }

}
