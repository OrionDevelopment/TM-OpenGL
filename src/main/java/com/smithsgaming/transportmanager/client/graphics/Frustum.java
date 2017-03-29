package com.smithsgaming.transportmanager.client.graphics;


import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * @Author Marc (Created on: 17.03.2016)
 */
public class Frustum {
    private Camera activeCamera;

    private FrustumPlanes planes;

    private Matrix4f viewProjectionMatrix = new Matrix4f();

    protected Frustum (Camera activeCamera) {
        this.activeCamera = activeCamera;

        this.planes = new FrustumPlanes();
    }

    protected void updateFrustum () {
        viewProjectionMatrix = activeCamera.getProjectionMatrix().mul(activeCamera.getViewMatrix(), new Matrix4f());
        extractPlanes(planes);
    }

    // Conversion Operations
    public void extractPlanes(FrustumPlanes clipPlanes) {

        // Near Plane (row 2 + row 3)
        clipPlanes.npX = viewProjectionMatrix.m02() + viewProjectionMatrix.m03();
        clipPlanes.npY = viewProjectionMatrix.m12() + viewProjectionMatrix.m13();
        clipPlanes.npZ = viewProjectionMatrix.m22() + viewProjectionMatrix.m23();
        clipPlanes.npW = viewProjectionMatrix.m32() + viewProjectionMatrix.m33();

        // Far Plane (row 3 - row 2)
        clipPlanes.fpX = viewProjectionMatrix.m03() - viewProjectionMatrix.m02();
        clipPlanes.fpY = viewProjectionMatrix.m13() - viewProjectionMatrix.m12();
        clipPlanes.fpZ = viewProjectionMatrix.m23() - viewProjectionMatrix.m22();
        clipPlanes.fpW = viewProjectionMatrix.m33() - viewProjectionMatrix.m32();

        // Left Plane (row 0 + row 3)
        clipPlanes.lpX = viewProjectionMatrix.m00() + viewProjectionMatrix.m03();
        clipPlanes.lpY = viewProjectionMatrix.m10() + viewProjectionMatrix.m13();
        clipPlanes.lpZ = viewProjectionMatrix.m20() + viewProjectionMatrix.m23();
        clipPlanes.lpW = viewProjectionMatrix.m30() + viewProjectionMatrix.m33();

        // Right Plane (row 3 - row 0)
        clipPlanes.rpX = viewProjectionMatrix.m03() - viewProjectionMatrix.m00();
        clipPlanes.rpY = viewProjectionMatrix.m13() - viewProjectionMatrix.m10();
        clipPlanes.rpZ = viewProjectionMatrix.m23() - viewProjectionMatrix.m20();
        clipPlanes.rpW = viewProjectionMatrix.m33() - viewProjectionMatrix.m30();

        // Bottom Plane (row 1 + row 3)
        clipPlanes.bpX = viewProjectionMatrix.m01() + viewProjectionMatrix.m03();
        clipPlanes.bpY = viewProjectionMatrix.m11() + viewProjectionMatrix.m13();
        clipPlanes.bpZ = viewProjectionMatrix.m21() + viewProjectionMatrix.m23();
        clipPlanes.bpW = viewProjectionMatrix.m31() + viewProjectionMatrix.m33();

        // Top Plane (row 3 - row 1)
        clipPlanes.tpX = viewProjectionMatrix.m03() - viewProjectionMatrix.m01();
        clipPlanes.tpY = viewProjectionMatrix.m13() - viewProjectionMatrix.m11();
        clipPlanes.tpZ = viewProjectionMatrix.m23() - viewProjectionMatrix.m21();
        clipPlanes.tpW = viewProjectionMatrix.m33() - viewProjectionMatrix.m31();

        clipPlanes.normalize();
    }

    public ViewType sphereInFrustum (Vector3f p, float radius) {
        if (planes.planePointEquation(p, radius)) return ViewType.INSIDE;

        return ViewType.OUTSIDE;
    }

    public ViewType boxInFrustum (AABox b) {
        for (Vector3f point : b.getPoints())
        {
            if (pointInFrustum(point) == ViewType.INSIDE)
            {
                return ViewType.INSIDE;
            }
        }

        if (planes.planeAABBEquation(b.getMinCorner(), b.getMaxCorner())) return ViewType.INSIDE;

        return ViewType.OUTSIDE;
    }

    public ViewType pointInFrustum(Vector3f p)
    {
        if (planes.planePointEquation(p, 0f))
        {
            return ViewType.INSIDE;
        }

        return ViewType.OUTSIDE;
    }

    public enum ViewType {
        OUTSIDE,
        INSIDE
    }
}
