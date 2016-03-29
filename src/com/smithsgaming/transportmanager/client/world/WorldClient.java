package com.smithsgaming.transportmanager.client.world;

import com.smithsgaming.transportmanager.client.world.chunk.*;
import com.smithsgaming.transportmanager.main.world.*;
import com.smithsgaming.transportmanager.main.world.chunk.*;
import com.smithsgaming.transportmanager.main.world.generation.WorldGenerationData;
import com.smithsgaming.transportmanager.main.world.tileentities.*;
import com.smithsgaming.transportmanager.main.world.tiles.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class WorldClient extends World {

    private boolean[][] chunkLoadedState;

    public WorldClient(WorldGenerationData coreData, WorldType type) {
        super(coreData, type);
        coreData.setChunks(new ChunkClient[coreData.getWorldWidth() / Chunk.chunkSize + 1][coreData.getWorldHeight() / Chunk.chunkSize + 1]);
        for (int x = 0; x < coreData.getWorldWidth() / Chunk.chunkSize + 1; x++) {
            for (int z = 0; z < coreData.getWorldHeight() / Chunk.chunkSize + 1; z++) {
                coreData.setChunk(new ChunkClient(this, x, z));
            }
        }
        chunkLoadedState = new boolean[coreData.getWorldWidth() / Chunk.chunkSize + 1][coreData.getWorldHeight() / Chunk.chunkSize + 1];
    }

    public boolean getLoadedStateForChunk(int chunkPosX, int chunkPosZ) {
        return chunkLoadedState[chunkPosX][chunkPosZ];
    }

    public void setChunkLoadedState(int chunkPosX, int chunkPosZ, boolean state) {
        chunkLoadedState[chunkPosX][chunkPosZ] = state;
    }

    public ChunkClient getChunkAtPos(int chunkPosX, int chunkPosZ) {
        return (ChunkClient) coreData.getChunkMap()[chunkPosX][chunkPosZ];
    }

    @Override
    public void update() {

    }
}
