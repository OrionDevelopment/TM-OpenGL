package com.smithsgaming.transportmanager.main.world;

import com.smithsgaming.transportmanager.main.world.generation.*;

import java.io.*;

/**
 * Created by Tim on 26/03/2016.
 */
public abstract class World {

    protected WorldGenerationData coreData;
    protected WorldType type;

    protected World (WorldGenerationData data, WorldType type) {
        this.coreData = data;
        this.type = type;
    }

    public WorldGenerationData getCoreData () {
        return coreData;
    }

    public void setType (WorldType type) {
        this.type = type;
    }

    public int getWorldTypeOrdinal () {
        return type.ordinal();
    }

    public WorldType getWorldType () {
        return type;
    }

    public void generate () {
    }

    public abstract void update ();

    public enum WorldType implements Serializable {
        OVERGROUND, UNDERGROUND
    }
}
