package com.smithsgaming.transportmanager.client.graphics;

import org.lwjgl.util.vector.*;

/**
 * @Author Marc (Created on: 17.03.2016)
 */
public class Frustum {
    private Camera activeCamera;
    private FrustumPlane topFrustumPlane = new FrustumPlane();
    private FrustumPlane bottomFrustumPlane = new FrustumPlane();
    private FrustumPlane farFrustumPlane = new FrustumPlane();
    private FrustumPlane nearFrustumPlane = new FrustumPlane();
    private FrustumPlane leftFrustumPlane = new FrustumPlane();
    private FrustumPlane rightFrustumPlane = new FrustumPlane();
    private FrustumPlane[] planes = new FrustumPlane[6];
    private Matrix4f viewProjectionMatrix = new Matrix4f();

    protected Frustum (Camera activeCamera) {
        this.activeCamera = activeCamera;

        planes[0] = topFrustumPlane;
        planes[1] = bottomFrustumPlane;
        planes[2] = leftFrustumPlane;
        planes[3] = rightFrustumPlane;
        planes[4] = nearFrustumPlane;
        planes[5] = farFrustumPlane;
    }

    protected void updateFrustum () {
        Matrix4f.mul(activeCamera.getViewMatrix(), activeCamera.getProjectionMatrix(), viewProjectionMatrix);
        viewProjectionMatrix.invert();

        nearFrustumPlane.setCoefficients(viewProjectionMatrix.m20 + viewProjectionMatrix.m30, viewProjectionMatrix.m21 + viewProjectionMatrix.m31, viewProjectionMatrix.m22 + viewProjectionMatrix.m32, viewProjectionMatrix.m23 + viewProjectionMatrix.m33);
        farFrustumPlane.setCoefficients(-viewProjectionMatrix.m20 + viewProjectionMatrix.m30, -viewProjectionMatrix.m21 + viewProjectionMatrix.m31, -viewProjectionMatrix.m22 + viewProjectionMatrix.m32, -viewProjectionMatrix.m23 + viewProjectionMatrix.m33);
        bottomFrustumPlane.setCoefficients(viewProjectionMatrix.m10 + viewProjectionMatrix.m30, viewProjectionMatrix.m11 + viewProjectionMatrix.m31, viewProjectionMatrix.m12 + viewProjectionMatrix.m32, viewProjectionMatrix.m13 + viewProjectionMatrix.m33);
        topFrustumPlane.setCoefficients(-viewProjectionMatrix.m10 + viewProjectionMatrix.m30, -viewProjectionMatrix.m11 + viewProjectionMatrix.m31, -viewProjectionMatrix.m12 + viewProjectionMatrix.m32, -viewProjectionMatrix.m13 + viewProjectionMatrix.m33);
        leftFrustumPlane.setCoefficients(viewProjectionMatrix.m00 + viewProjectionMatrix.m30, viewProjectionMatrix.m01 + viewProjectionMatrix.m31, viewProjectionMatrix.m02 + viewProjectionMatrix.m32, viewProjectionMatrix.m03 + viewProjectionMatrix.m33);
        rightFrustumPlane.setCoefficients(-viewProjectionMatrix.m00 + viewProjectionMatrix.m30, -viewProjectionMatrix.m01 + viewProjectionMatrix.m31, -viewProjectionMatrix.m02 + viewProjectionMatrix.m32, -viewProjectionMatrix.m03 + viewProjectionMatrix.m33);
    }

    public ViewType pointInFrustum (Vector3f p) {
        ViewType result = ViewType.INSIDE;
        for (int i = 0; i < 6; i++) {

            if (planes[i].distance(p) < 0)
                return ViewType.OUTSIDE;
        }
        return result;
    }

    public ViewType sphereInFrustum (Vector3f p, float radius) {
        ViewType result = ViewType.INSIDE;
        float distance;

        for (int i = 0; i < 6; i++) {
            distance = planes[i].distance(p);
            if (distance < -radius)
                return ViewType.OUTSIDE;
            else if (distance < radius)
                result = ViewType.INTERSECT;
        }
        return result;
    }

    public ViewType boxInFrustum (AABox b) {
        ViewType result = ViewType.INSIDE;
        for (int i = 0; i < 6; i++) {
            if (planes[i].distance(b.getVertexP(planes[i].normal)) < 0)
                return ViewType.OUTSIDE;
            else if (planes[i].distance(b.getVertexN(planes[i].normal)) < 0)
                result = ViewType.INTERSECT;
        }
        return result;
    }


    public enum ViewType {
        OUTSIDE,
        INSIDE,
        INTERSECT
    }
}
