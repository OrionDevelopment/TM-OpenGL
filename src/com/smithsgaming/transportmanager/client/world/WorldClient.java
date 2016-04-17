package com.smithsgaming.transportmanager.client.world;

import com.smithsgaming.transportmanager.client.world.chunk.*;
import com.smithsgaming.transportmanager.main.entity.Entity;
import com.smithsgaming.transportmanager.main.tileentity.TileEntity;
import com.smithsgaming.transportmanager.main.world.*;
import com.smithsgaming.transportmanager.main.world.chunk.*;
import com.smithsgaming.transportmanager.main.world.generation.*;
import com.smithsgaming.transportmanager.main.world.tiles.Tile;

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
                setChunk(new ChunkClient(this, x, z));
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

    @Override
    public void addEntity(Entity entity) {
        //TODO: This probably needs to do something to the server
    }

    public ChunkClient getChunkAtPos(int chunkPosX, int chunkPosZ) {
        return (ChunkClient) coreData.getChunkMap()[chunkPosX][chunkPosZ];
    }

    @Override
    public Tile getTileAtPos(int tileWorldPosX, int tileWorldPosZ) {
        return getChunkAtPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).getTileAtPos(tileWorldPosX % Chunk.chunkSize, tileWorldPosZ % Chunk.chunkSize);
    }

    @Override
    public TileEntity getTileEntityAtPos(int tileWorldPosX, int tileWorldPosZ) {
        return getChunkAtPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).getTileEntityAtPos(tileWorldPosX % Chunk.chunkSize, tileWorldPosZ % Chunk.chunkSize);
    }

    @Override
    public void setChunk(Chunk chunk) {
        //TODO: This probably needs to do something to the server
        if (chunk instanceof ChunkClient) {
            coreData.getChunkMap()[chunk.getChunkX()][chunk.getChunkZ()] = chunk;
        } else {
            throw new IllegalArgumentException("Setting a Chunk on the client side requires a ChunkClient instance");
        }
    }

    @Override
    public void setTileAtPos(Tile tile, int tileWorldPosX, int tileWorldPosZ) {
        //TODO: This probably needs to do something to the server
        Chunk chunk = getChunkAtPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize);
        chunk.setTileAtPos(tile, tileWorldPosX % Chunk.chunkSize, tileWorldPosZ % Chunk.chunkSize);
    }

    @Override
    public void setTileEntityAtPos(TileEntity tileEntity, int tileWorldPosX, int tileWorldPosZ) {
        //TODO: This probably needs to do something to the server
        Chunk chunk = getChunkAtPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize);
        chunk.setTileEntityAtPos(tileEntity, tileWorldPosX % Chunk.chunkSize, tileWorldPosZ % Chunk.chunkSize);
    }

    @Override
    public void update() {
    }
}
