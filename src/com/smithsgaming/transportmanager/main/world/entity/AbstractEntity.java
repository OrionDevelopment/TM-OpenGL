package com.smithsgaming.transportmanager.main.world.entity;

import com.smithsgaming.transportmanager.main.world.World;
import com.smithsgaming.transportmanager.main.world.saveable.ISavable;
import com.smithsgaming.transportmanager.main.world.saveable.WorldSaveHandler;
import org.jnbt.CompoundTag;
import org.jnbt.IntTag;
import org.jnbt.Tag;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tim on 26/03/2016.
 */
public abstract class AbstractEntity implements Serializable, ISavable {

    protected World world;
    public int xPos, yPos, zPos, height;

    /**
     * @return The world the entity is in
     */
    public World getWorldObj() {
        return world;
    }

    /**
     * @return The x position of the entity
     */
    public int getXPosition() {
        return xPos;
    }

    /**
     * @return The y position of the entity
     */
    public int getYPosition() {
        return yPos;
    }

    /**
     * @return The game layer the entity is in
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the world this entity belongs to
     *
     * @param world The world instance
     */
    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public CompoundTag writeToDisk() {
        Map<String, Tag> dataMap = new HashMap<>();
        dataMap.put(WorldSaveHandler.Tags.TILE_CHUNK_POS_X, new IntTag(WorldSaveHandler.Tags.TILE_CHUNK_POS_X, xPos));
        dataMap.put(WorldSaveHandler.Tags.TILE_CHUNK_POS_Y, new IntTag(WorldSaveHandler.Tags.TILE_CHUNK_POS_Y, xPos));
        dataMap.put(WorldSaveHandler.Tags.TILE_CHUNK_POS_Z, new IntTag(WorldSaveHandler.Tags.TILE_CHUNK_POS_Z, xPos));
        return new CompoundTag(WorldSaveHandler.Tags.TILE_ENTITY_DATA, dataMap);
    }

    @Override
    public void loadFromDisk(CompoundTag tag) {

    }
}
