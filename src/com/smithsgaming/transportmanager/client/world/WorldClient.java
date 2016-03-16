package com.smithsgaming.transportmanager.client.world;

import com.smithsgaming.transportmanager.main.world.*;
import com.smithsgaming.transportmanager.main.world.chunk.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class WorldClient extends World
{

    private boolean[][] chunkLoadedState;

    public WorldClient (WorldCoreData coreData) {
        super(coreData);

        chunkLoadedState = new boolean[coreData.getWorldWidth() / Chunk.chunkSize + 1][coreData.getWorldLength() / Chunk.chunkSize + 1];
    }

    public boolean getLoadedStateForChunk (int chunkPosX, int chunkPosZ) {
        return chunkLoadedState[chunkPosX][chunkPosZ];
    }

    public void setChunkLoadedState (int chunkPosX, int chunkPosZ, boolean state) {
        chunkLoadedState[chunkPosX][chunkPosZ] = state;
    }
}
