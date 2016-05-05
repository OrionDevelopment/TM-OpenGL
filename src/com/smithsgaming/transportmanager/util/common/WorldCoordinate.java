package com.smithsgaming.transportmanager.util.common;

import net.smert.frameworkgl.math.*;

/**
 * @Author Marc (Created on: 24.04.2016)
 */
public class WorldCoordinate {
    private Vector3f position;

    public WorldCoordinate (Vector3f position) {
        this.position = position;
    }

    public float getX () {
        return position.getX();
    }

    public void setX (float x) {
        position.setX(x);
    }

    public float getY () {
        return position.getY();
    }

    public void setY (float y) {
        position.setY(y);
    }

    public float getZ () {
        return position.getZ();
    }

    public void setZ (float z) {
        position.setZ(z);
    }

    public void move (Vector3f delta) {
        position.add(delta);
    }

    public float getDistanceTo (WorldCoordinate coordinate) {
        return position.distance(coordinate.position);
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (!( o instanceof WorldCoordinate )) return false;

        WorldCoordinate that = (WorldCoordinate) o;

        return position.equals(that.position);

    }

    @Override
    public int hashCode () {
        return position.hashCode();
    }
}
