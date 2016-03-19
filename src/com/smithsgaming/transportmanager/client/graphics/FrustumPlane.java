package com.smithsgaming.transportmanager.client.graphics;

import org.lwjgl.util.vector.*;

/**
 * @Author Marc (Created on: 17.03.2016)
 */
class FrustumPlane {
    Vector3f normal;
    Vector3f point;

    Float d;

    public FrustumPlane (Vector3f v1, Vector3f v2, Vector3f v3) {
        normal = new Vector3f();
        point = new Vector3f();
        d = 0f;

        set3Points(v1, v2, v3);
    }

    public FrustumPlane () {
        normal = new Vector3f();
        point = new Vector3f();
        d = 0f;
    }

    void set3Points (Vector3f v1, Vector3f v2, Vector3f v3) {

        Vector3f aux1 = new Vector3f();
        Vector3f aux2 = new Vector3f();

        Vector3f.sub(v1, v2, aux1);
        Vector3f.sub(v3, v2, aux2);

        Vector3f.cross(aux2, aux1, normal);

        normal.normalise();
        point = new Vector3f(v2);

        d = -Vector3f.dot(normal, point);
    }

    void setNormalAndPoint (Vector3f normal, Vector3f point) {
        this.normal = new Vector3f(normal);
        this.normal.normalise();

        d = -Vector3f.dot(normal, point);
    }

    void setCoefficients (float a, float b, float c, float d) {
        normal.set(a, b, c);
        float l = normal.length();
        normal.set(a / l, b / l, c / l);
        this.d = d / l;
    }

    float distance (Vector3f p) {
        return ( d + Vector3f.dot(normal, p) );
    }

}