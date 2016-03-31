package com.smithsgaming.transportmanager.main.entity;

import com.smithsgaming.transportmanager.main.saveable.*;
import com.smithsgaming.transportmanager.main.world.*;
import com.smithsgaming.transportmanager.util.nbt.*;

import java.io.*;

/**
 * Created by Tim on 26/03/2016.
 */
public abstract class AbstractEntity implements Serializable, ISavable {

    public int xPos, yPos, zPos, height;
    protected String identity;
    protected World world;

    /**
     * @return The world the entity is in
     */
    public World getWorldObj () {
        return world;
    }

    /**
     * @return The x position of the entity
     */
    public int getXPosition () {
        return xPos;
    }

    /**
     * @return The y position of the entity
     */
    public int getYPosition () {
        return yPos;
    }

    /**
     * @return The game layer the entity is in
     */
    public int getHeight () {
        return height;
    }

    /**
     * Sets the world this entity belongs to
     *
     * @param world The world instance
     */
    public void setWorld (World world) {
        this.world = world;
    }

    public String getIdentity () {
        return new String(identity);
    }

    public abstract void update ();

    @Override
    public void writeToDisk (NBTTagCompound tag) {
        tag.writeString(NBTTags.TILE_ENTITY_IDENTITY, getIdentity());
    }

    @Override
    public void loadFromDisk (NBTTagCompound tag) {
        identity = tag.getString(NBTTags.TILE_ENTITY_IDENTITY);
    }

}
