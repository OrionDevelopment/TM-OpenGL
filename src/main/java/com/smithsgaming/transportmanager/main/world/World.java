package com.smithsgaming.transportmanager.main.world;

import com.smithsgaming.transportmanager.main.entity.Entity;
import com.smithsgaming.transportmanager.main.tileentity.TileEntity;
import com.smithsgaming.transportmanager.main.world.chunk.Chunk;
import com.smithsgaming.transportmanager.main.world.generation.WorldGenerationData;
import com.smithsgaming.transportmanager.main.world.tiles.Tile;

import java.io.Serializable;

/**
 * Created by Tim on 26/03/2016.
 */
public abstract class World {

    protected WorldGenerationData coreData;
    protected WorldType type;

    protected World(WorldGenerationData data, WorldType type) {
        this.coreData = data;
        this.type = type;
    }

    public WorldGenerationData getCoreData() {
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

    public abstract void update();

    public abstract void addEntity(Entity entity);

    public abstract Chunk getChunkAtPos(int chunkPosX, int chunkPosZ);

    public abstract Tile getTileAtPos(int tileWorldPosX, int tileWorldPosZ);

    public abstract TileEntity getTileEntityAtPos(int tileWorldPosX, int tileWorldPosZ);

    public abstract void setChunk(Chunk chunk);

    public abstract void setTileAtPos(Tile tile, int tileWorldPosX, int tileWorldPosZ);

    public abstract void setTileEntityAtPos(TileEntity tileEntity, int tileWorldPosX, int tileWorldPosZ);

    public enum WorldType implements Serializable
    {
        OVERGROUND,
        UNDERGROUND
    }
}
