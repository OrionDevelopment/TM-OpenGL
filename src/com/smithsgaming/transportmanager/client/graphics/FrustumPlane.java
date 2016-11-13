package com.smithsgaming.transportmanager.client.graphics;

import org.joml.Vector3f;

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

        v1.sub(v2, aux1);
        v3.sub(v2, aux2);

        aux2.cross(aux1, normal);

        normal.normalize();
        point = new Vector3f(v2);

        d = -normal.dot(point);
    }

    void setNormalAndPoint (Vector3f normal, Vector3f point) {
        this.normal = new Vector3f(normal);
        this.normal.normalize();

        d = -normal.dot(point);
    }

    void setCoefficients (float a, float b, float c, float d) {
        normal.set(a, b, c);
        float l = (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(c, 2) + Math.pow(d, 2));
        normal.set(a / l, b / l, c / l);
        this.d = d / l;
    }

    float distance (Vector3f p) {
        return ( d + normal.dot(p) );
    }

}