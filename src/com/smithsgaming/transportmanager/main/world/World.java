package com.smithsgaming.transportmanager.main.world;

import com.smithsgaming.transportmanager.main.world.chunk.Chunk;
import com.smithsgaming.transportmanager.main.world.tileentities.TileEntity;
import com.smithsgaming.transportmanager.main.world.tiles.Tile;
import com.smithsgaming.transportmanager.main.core.TileRegistry;

import java.io.Serializable;

/**
 * Created by Tim on 26/03/2016.
 */
public abstract class World {

    public enum WorldType implements Serializable {
        OVERGROUND, UNDERGROUND
    }

    protected WorldCoreData coreData;
    protected Chunk[][] chunks;
    protected WorldType type;

    protected World(WorldCoreData data, WorldType type) {
        this.coreData = data;
        this.type = type;
        initializeChunkMap();
    }

    public Chunk getChunkAtPos(int chunkPosX, int chunkPosZ) {
        return chunks[chunkPosX][chunkPosZ];
    }

    public void setChunk(Chunk chunkForPos) {
        chunks[chunkForPos.getChunkX()][chunkForPos.getChunkZ()] = chunkForPos;
    }

    public Tile getTileAtPos(int tileWorldPosX, int tileWorldPosZ) {
        return getChunkAtPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).getTileAtPos(tileWorldPosX % Chunk.chunkSize, tileWorldPosZ % Chunk.chunkSize);
    }

    public TileEntity getTileEntityAtPos(int tileWorldPosX, int tileWorldPosZ) {
        return getChunkAtPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).getTileEntityAtPos(tileWorldPosX % Chunk.chunkSize, tileWorldPosZ % Chunk.chunkSize);
    }

    public void setTileAtPos(Tile tile, int tileWorldPosX, int tileWorldPosZ) {
        getChunkAtPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).setTileAtPos(tile, tileWorldPosX % Chunk.chunkSize, tileWorldPosZ % Chunk.chunkSize);
    }

    public void setTileEntityAtPos(TileEntity tileEntity, int tileWorldPosX, int tileWorldPosZ) {
        getChunkAtPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).setTileEntityAtPos(tileEntity, tileWorldPosX % Chunk.chunkSize, tileWorldPosZ % Chunk.chunkSize);
    }

    public WorldCoreData getCoreData() {
        return coreData;
    }

    public void setType(WorldType type) {
        this.type = type;
    }

    public int getWorldTypeOrdinal() {
        return type.ordinal();
    }

    public WorldType getWorldType() {
        return type;
    }

    protected abstract void initializeChunkMap();

    public void generate() {
        for (int x = 0; x < chunks.length; x++) {
            for (int z = 0; z < chunks[0].length; z++) {
                Chunk chunk = chunks[x][z];
                for (int cx = 0; cx < Chunk.chunkSize; cx++) {
                    for (int cz = 0; cz < Chunk.chunkSize; cz++) {
                        chunk.setTileAtPos(TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.OCEAN), cx, cz);
                    }
                }
            }
        }
    }

    public abstract void update();
}
