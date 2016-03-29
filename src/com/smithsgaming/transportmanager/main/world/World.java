package com.smithsgaming.transportmanager.main.world;

import com.smithsgaming.transportmanager.main.world.chunk.Chunk;
import com.smithsgaming.transportmanager.main.world.generation.WorldGenerationData;
import com.smithsgaming.transportmanager.main.world.generation.WorldGraphFeaturesGenerator;
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

    public void generate() {
    }

    public abstract void update();
}
