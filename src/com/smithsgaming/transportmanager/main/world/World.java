package com.smithsgaming.transportmanager.main.world;

import com.smithsgaming.transportmanager.main.world.chunk.Chunk;
import com.smithsgaming.transportmanager.main.world.tileentities.TileEntity;
import com.smithsgaming.transportmanager.main.world.tiles.Tile;
import com.smithsgaming.transportmanager.main.world.tiles.TileRegistry;

/**
 * Created by Tim on 26/03/2016.
 */
public abstract class World {

    protected WorldCoreData coreData;
    protected Chunk[][] chunks;

    protected World(WorldCoreData data) {
        this.coreData = data;
        initializeChunkMap();
    }

    public Chunk getChunkAtPos(int chunkPosX, int chunkPosZ) {
        return chunks[chunkPosX][chunkPosZ];
    }

    public void setChunk(Chunk chunkForPos) {
        chunks[chunkForPos.getChunkX()][chunkForPos.getChunkZ()] = chunkForPos;
    }

    public Tile getTileAtPos(int tileWorldPosX, int tileWorldPosY, int tileWorldPosZ) {
        return getChunkAtPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).getTileAtPos(tileWorldPosX % Chunk.chunkSize, tileWorldPosY, tileWorldPosZ % Chunk.chunkSize);
    }

    public TileEntity getTileEntityAtPos(int tileWorldPosX, int tileWorldPosY, int tileWorldPosZ) {
        return getChunkAtPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).getTileEntityAtPos(tileWorldPosX % Chunk.chunkSize, tileWorldPosY, tileWorldPosZ % Chunk.chunkSize);
    }

    public void setTileAtPos(Tile tile, int tileWorldPosX, int tileWorldPosY, int tileWorldPosZ) {
        getChunkAtPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).setTileAtPos(tile, tileWorldPosX % Chunk.chunkSize, tileWorldPosY, tileWorldPosZ % Chunk.chunkSize);
    }

    public void setTileEntityAtPos(TileEntity tileEntity, int tileWorldPosX, int tileWorldPosY, int tileWorldPosZ) {
        getChunkAtPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).setTileEntityAtPos(tileEntity, tileWorldPosX % Chunk.chunkSize, tileWorldPosY, tileWorldPosZ % Chunk.chunkSize);
    }

    public WorldCoreData getCoreData() {
        return coreData;
    }

    protected void initializeChunkMap() {
        chunks = new Chunk[coreData.getWorldWidth() / Chunk.chunkSize + 1][coreData.getWorldLength() / Chunk.chunkSize + 1];
        for (int x = 0; x < chunks.length; x++) {
            for (int z = 0; z < chunks[0].length; z++) {
                chunks[x][z] = new Chunk(this, x, z);
            }
        }
    }

    public void generate() {
        for (int x = 0; x < chunks.length; x++) {
            for (int z = 0; z < chunks[0].length; z++) {
                Chunk chunk = chunks[x][z];
                for (int cx = 0; cx < Chunk.chunkSize; cx++) {
                    for (int cy = 0; cy < coreData.getWorldHeight(); cy++) {
                        for (int cz = 0; cz < Chunk.chunkSize; cz++) {
                            chunk.setTileAtPos(TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.OCEAN), cx, cy, cz);
                        }
                    }
                }
            }
        }
    }

    public void update() {
    }
}
