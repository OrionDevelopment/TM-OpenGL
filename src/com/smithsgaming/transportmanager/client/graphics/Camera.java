package com.smithsgaming.transportmanager.client.graphics;

import com.smithsgaming.transportmanager.main.world.chunk.*;
import com.smithsgaming.transportmanager.util.*;
import org.lwjgl.util.vector.*;

/**
 * @Author Marc (Created on: 17.03.2016)
 */
public class Camera {

    public static final Camera Player = new Camera();

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;

    private Vector3f cameraPosition = new Vector3f();
    private float viewDistanceInChunks = 4;

    private Frustum activeFrustum;

    public Camera () {
        this(new Vector3f());
    }

    public Camera (Vector3f cameraPosition) {
        createProjectionMatrix();

        createCameraMatrix();
        moveCamera(cameraPosition);

        this.activeFrustum = new Frustum(this);
    }

    /**
     * Method to create a perspective View, should be used for WorldRendering.
     */
    private void createProjectionMatrix () {
        setProjectionMatrix(com.smithsgaming.transportmanager.util.MathUtil.CreatePerspectiveFieldOfView(com.smithsgaming.transportmanager.util.MathUtil.toRadiant(OpenGLUtil.getFOV()), OpenGLUtil.getAspectRatio(), 0.1f, 100f));
    }

    /**
     * Method to create the default camera matrix.
     */
    private void createCameraMatrix () {
        Matrix4f matrix4f = new Matrix4f();
        setViewMatrix(matrix4f);
    }

    /**
     * Getter for the current projection matrix. ~ @return The current projection matrix.
     */
    public Matrix4f getProjectionMatrix () {
        return projectionMatrix;
    }

    /**
     * Private setter for the projection matrix.
     *
     * @param projectionMatrix The new projection matrix.
     */
    private void setProjectionMatrix (Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;

        activeFrustum.updateFrustum();
    }

    public Matrix4f getViewMatrix () {
        return viewMatrix;
    }

    private void setViewMatrix (Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;

        activeFrustum.updateFrustum();
    }

    /**
     * Method to move the camera over a certain WorldCoordinate delta.
     *
     * @param moveDelta The distance to move the camera over.
     */
    public void moveCamera (Vector3f moveDelta) {
        viewMatrix.translate(moveDelta);
        cameraPosition.translate(moveDelta.getX(), moveDelta.getY(), moveDelta.getZ());

        activeFrustum.updateFrustum();
    }

    /**
     * Method to rotate the camera.
     *
     * @param angle The angle to rotate the camera over.
     * @param axis  A vector indicating the axis that should be rotated over.
     */
    public void rotateCamera (float angle, Vector3f axis) {
        viewMatrix.rotate(angle, axis);

        activeFrustum.updateFrustum();
    }

    public Frustum getActiveFrustum () {
        return activeFrustum;
    }

    public Vector3f getCameraPosition () {
        return cameraPosition;
    }

    public boolean isPointInViewDistance (Vector3f point) {
        return point.sub(point, cameraPosition, new Vector3f()).lengthSquared() <= ( Math.pow(viewDistanceInChunks * Chunk.chunkSize, 2) );
    }
}
