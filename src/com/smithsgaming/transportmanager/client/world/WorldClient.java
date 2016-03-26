package com.smithsgaming.transportmanager.client.world;

import com.smithsgaming.transportmanager.client.world.chunk.*;
import com.smithsgaming.transportmanager.main.world.*;
import com.smithsgaming.transportmanager.main.world.chunk.*;
import com.smithsgaming.transportmanager.main.world.tileentities.*;
import com.smithsgaming.transportmanager.main.world.tiles.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class WorldClient extends World {

    private boolean[][] chunkLoadedState;

    public WorldClient(WorldCoreData coreData) {
        super(coreData);
        chunkLoadedState = new boolean[coreData.getWorldWidth() / Chunk.chunkSize + 1][coreData.getWorldLength() / Chunk.chunkSize + 1];
    }

    public boolean getLoadedStateForChunk(int chunkPosX, int chunkPosZ) {
        return chunkLoadedState[chunkPosX][chunkPosZ];
    }

    public void setChunkLoadedState(int chunkPosX, int chunkPosZ, boolean state) {
        chunkLoadedState[chunkPosX][chunkPosZ] = state;
    }

    public ChunkClient getChunkForPos(int chunkPosX, int chunkPosZ) {
        return (ChunkClient) chunks[chunkPosX][chunkPosZ];
    }

    protected void initializeChunkMap() {
        chunks = new ChunkClient[coreData.getWorldWidth() / Chunk.chunkSize + 1][coreData.getWorldLength() / Chunk.chunkSize + 1];
        for (int x = 0; x < chunks.length; x++) {
            for (int z = 0; z < chunks[0].length; z++) {
                chunks[x][z] = new ChunkClient(this, x, z);
            }
        }
    }

}
