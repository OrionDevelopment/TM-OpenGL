package com.smithsgaming.transportmanager.client.graphics;

import org.joml.Vector3f;

/**
 * @Author Marc (Created on: 17.03.2016)
 */
public class AABox {

    Vector3f corner;

    float x, y, z;

    public AABox (Vector3f corner, float x, float y, float z) {

        setBox(corner, x, y, z);
    }

    public AABox () {
        corner.x = 0;
        corner.y = 0;
        corner.z = 0;

        x = 1.0f;
        y = 1.0f;
        z = 1.0f;

    }

    public Vector3f getMinCorner() {
        return corner;
    }

    public Vector3f getMaxCorner() { return corner.add(getX(), getY(), getZ(), new Vector3f()); }

    public float getX () {
        return x;
    }

    public float getY () {
        return y;
    }

    public float getZ () {
        return z;
    }

    void setBox (Vector3f corner, float x, float y, float z) {
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


    }
}
