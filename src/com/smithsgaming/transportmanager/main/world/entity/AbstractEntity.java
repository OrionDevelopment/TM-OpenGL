package com.smithsgaming.transportmanager.main.world.entity;

import com.smithsgaming.transportmanager.main.world.World;

import java.io.Serializable;

/**
 * Created by Tim on 26/03/2016.
 */
public class AbstractEntity implements Serializable {

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

}
