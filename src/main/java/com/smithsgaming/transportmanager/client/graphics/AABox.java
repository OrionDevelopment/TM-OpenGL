/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

/*
 * Copyright (c) 2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.client.graphics;

import org.joml.Vector3f;

/**
 * A box representing an Axis aligned object.
 */
public class AABox {

    private Vector3f corner;

    private float x;
    private float y;
    private float z;

    private Vector3f[] points = new Vector3f[8];

    public AABox (Vector3f corner, float x, float y, float z) {
        setBox(corner, x, y, z);
    }

    private void setBox(Vector3f corner, float x, float y, float z) {
        this.corner = new Vector3f(corner);

        if (x < 0.0) {
            x = -x;
            this.corner.x -= x;
        }

        if (y < 0.0) {
            y = -y;
            this.corner.y -= y;
        }

        if (z < 0.0) {
            z = -z;
            this.corner.z -= z;
        }

        this.x = x;
        this.y = y;
        this.z = z;

        updatePoints();
    }

    private void updatePoints() {
        int i = 0;
        points[i++] = getMinCorner();
        points[i++] = getMinCorner().add(getX(), 0, 0);
        points[i++] = getMinCorner().add(getX(), getY(), 0);
        points[i++] = getMinCorner().add(0, getY(), 0);
        points[i++] = getMinCorner().add(0, 0, getZ());
        points[i++] = getMinCorner().add(getX(), 0, getZ());
        points[i++] = getMinCorner().add(getX(), getY(), getZ());
        points[i] = getMinCorner().add(0, getY(), getZ());
    }

    Vector3f getMinCorner() {
        return new Vector3f(corner);
    }

    float getX() {
        return x;
    }

    float getY() {
        return y;
    }

    float getZ() {
        return z;
    }

    Vector3f getMaxCorner() {
        return corner.add(getX(), getY(), getZ(), new Vector3f());
    }

    public Vector3f[] getPoints() {
        return points;
    }
}
