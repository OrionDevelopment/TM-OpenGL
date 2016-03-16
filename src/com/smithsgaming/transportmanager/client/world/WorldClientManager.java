package com.smithsgaming.transportmanager.client.world;

import com.smithsgaming.transportmanager.main.world.*;
import com.smithsgaming.transportmanager.main.world.chunk.*;
import com.smithsgaming.transportmanager.main.world.saveable.*;
import javafx.util.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class WorldClientManager {
    public static WorldClientManager instance = new WorldClientManager();

    private WorldClient world;
    private TileSaveHandler saveHandler;

    protected WorldClientManager () {
    }

    public void initializeWorld (WorldCoreData data) {
        this.world = new WorldClient(data);
        this.saveHandler = new TileSaveHandler(world);
    }

    public Pair<Integer, Integer> getNextChunkToSyncForWorld () {
        for (int x = 0; x < world.getCoreData().getWorldWidth() / Chunk.chunkSize + 1; x++) {
            for (int z = 0; z < world.getCoreData().getWorldWidth() / Chunk.chunkSize + 1; z++) {
                if (!world.getLoadedStateForChunk(x, z))
                    return new Pair<>(x, z);
            }
        }

        return null;
    }

    public TileSaveHandler getSaveHandler () {
        return saveHandler;
    }

    public WorldClient getWorld () {
        return world;
    }
}
