package com.smithsgaming.transportmanager.client.world;

import com.smithsgaming.transportmanager.client.world.chunk.*;
import com.smithsgaming.transportmanager.main.world.*;
import com.smithsgaming.transportmanager.main.world.chunk.*;
import com.smithsgaming.transportmanager.main.world.tileentities.*;
import com.smithsgaming.transportmanager.main.world.tiles.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class WorldClient
{
    WorldCoreData coreData;

    private ChunkClient[][] chunks;
    private boolean[][] chunkLoadedState;

    public WorldClient (WorldCoreData coreData) {
        this.coreData = coreData;
        initializeChunkMap();
        chunkLoadedState = new boolean[coreData.getWorldWidth() / Chunk.chunkSize + 1][coreData.getWorldLength() / Chunk.chunkSize + 1];
    }

    public boolean getLoadedStateForChunk (int chunkPosX, int chunkPosZ) {
        return chunkLoadedState[chunkPosX][chunkPosZ];
    }

    public void setChunkLoadedState (int chunkPosX, int chunkPosZ, boolean state) {
        chunkLoadedState[chunkPosX][chunkPosZ] = state;
    }

    public ChunkClient getChunkForPos (int chunkPosX, int chunkPosZ) {
        return chunks[chunkPosX][chunkPosZ];
    }

    public void setChunkForPos (ChunkClient chunkForPos) {
        chunks[chunkForPos.getChunkX()][chunkForPos.getChunkZ()] = chunkForPos;
    }

    public Tile getTileForPos (int tileWorldPosX, int tileWorldPosY, int tileWorldPosZ) {
        return getChunkForPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).getTileOnPos(tileWorldPosX % Chunk.chunkSize, tileWorldPosY, tileWorldPosZ % Chunk.chunkSize);
    }

    public TileEntity getTileEntityForPos (int tileWorldPosX, int tileWorldPosY, int tileWorldPosZ) {
        return getChunkForPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).getTileEntityOnPos(tileWorldPosX % Chunk.chunkSize, tileWorldPosY, tileWorldPosZ % Chunk.chunkSize);
    }

    public void setTileOnPos (Tile tile, int tileWorldPosX, int tileWorldPosY, int tileWorldPosZ) {
        getChunkForPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).setTileOnPos(tile, tileWorldPosX % Chunk.chunkSize, tileWorldPosY, tileWorldPosZ % Chunk.chunkSize);
    }

    public void setTileEntityOnPos (TileEntity tileEntity, int tileWorldPosX, int tileWorldPosY, int tileWorldPosZ) {
        getChunkForPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).setTileEntityOnPos(tileEntity, tileWorldPosX % Chunk.chunkSize, tileWorldPosY, tileWorldPosZ % Chunk.chunkSize);
    }

    public WorldCoreData getCoreData () {
        return coreData;
    }

    protected void initializeChunkMap () {
        chunks = new ChunkClient[coreData.getWorldWidth() / Chunk.chunkSize + 1][coreData.getWorldLength() / Chunk.chunkSize + 1];

        for (int x = 0; x < chunks.length; x++) {
            for (int z = 0; z < chunks[0].length; z++) {
                chunks[x][z] = new ChunkClient(this, x, z);
            }
        }
    }

}
