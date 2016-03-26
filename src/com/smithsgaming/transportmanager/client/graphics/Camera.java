package com.smithsgaming.transportmanager.client.graphics;

import com.smithsgaming.transportmanager.main.world.chunk.*;
import com.smithsgaming.transportmanager.util.*;
import org.lwjgl.*;
import org.lwjgl.util.vector.*;

import java.nio.*;
import java.util.*;

/**
 * @Author Marc (Created on: 17.03.2016)
 */
public class Camera {

    public static final Camera Player = new Camera(MathUtil.toRadiant(90), new Vector3f(1, 0, 0)).moveCamera(new Vector3f(0, -25f, 0f));
    public static final Camera Gui = new Camera();
    private static Stack<Matrix4f> modelMatrixStack = new Stack<>();
    private static Matrix4f renderingModelMatrix = new Matrix4f();
    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private FloatBuffer projectionMatrixBuffer = BufferUtils.createFloatBuffer(16);
    private FloatBuffer viewMatrixBuffer = BufferUtils.createFloatBuffer(16);
    private Vector3f cameraPosition = new Vector3f();
    private float viewDistanceInChunks = 4;

    private Frustum activeFrustum;

    public Camera () {
        this(new Vector3f());
    }

    public Camera (Vector3f cameraPosition) {
        this.activeFrustum = new Frustum(this);

        this.projectionMatrix = com.smithsgaming.transportmanager.util.MathUtil.CreatePerspectiveFieldOfView(com.smithsgaming.transportmanager.util.MathUtil.toRadiant(OpenGLUtil.getFOV()), OpenGLUtil.getAspectRatio(), 0.1f, 100f);
        this.projectionMatrix.store(projectionMatrixBuffer);
        this.projectionMatrixBuffer.flip();


        this.viewMatrix = new Matrix4f();
        this.viewMatrix.store(this.viewMatrixBuffer);
        this.viewMatrixBuffer.flip();

        moveCamera(cameraPosition);
    }

    public Camera (float angle, Vector3f rotationAxis) {
        this.activeFrustum = new Frustum(this);

        this.projectionMatrix = com.smithsgaming.transportmanager.util.MathUtil.CreatePerspectiveFieldOfView(com.smithsgaming.transportmanager.util.MathUtil.toRadiant(OpenGLUtil.getFOV()), OpenGLUtil.getAspectRatio(), 0.1f, 100f);
        this.projectionMatrix.store(projectionMatrixBuffer);
        this.projectionMatrixBuffer.flip();


        this.viewMatrix = new Matrix4f();
        this.viewMatrix.store(this.viewMatrixBuffer);
        this.viewMatrixBuffer.flip();

        rotateCamera(angle, rotationAxis);
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

        projectionMatrixBuffer.clear();
        projectionMatrix.store(projectionMatrixBuffer);
        projectionMatrixBuffer.flip();

        activeFrustum.updateFrustum();
    }

    public Matrix4f getViewMatrix () {
        return viewMatrix;
    }

    private void setViewMatrix (Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;

        viewMatrixBuffer.clear();
        viewMatrix.store(viewMatrixBuffer);
        viewMatrixBuffer.flip();

        activeFrustum.updateFrustum();
    }

    public FloatBuffer getProjectionMatrixBuffer () {
        return projectionMatrixBuffer;
    }

    public FloatBuffer getViewMatrixBuffer () {
        return viewMatrixBuffer;
    }

    /**
     * Method to move the camera over a certain WorldCoordinate delta.
     *
     * @param moveDelta The distance to move the camera over.
     */
    public Camera moveCamera (Vector3f moveDelta) {
        viewMatrix.translate(moveDelta);
        cameraPosition.translate(moveDelta.getX(), moveDelta.getY(), moveDelta.getZ());

        viewMatrixBuffer.clear();
        viewMatrix.store(viewMatrixBuffer);
        viewMatrixBuffer.flip();

        activeFrustum.updateFrustum();

        return this;
    }

    /**
     * Method to rotate the camera.
     *
     * @param angle The angle to rotate the camera over.
     * @param axis  A vector indicating the axis that should be rotated over.
     */
    public Camera rotateCamera (float angle, Vector3f axis) {
        viewMatrix.rotate(angle, axis);

        viewMatrixBuffer.clear();
        viewMatrix.store(viewMatrixBuffer);
        viewMatrixBuffer.flip();

        activeFrustum.updateFrustum();

        return this;
    }

    public Frustum getActiveFrustum () {
        return activeFrustum;
    }

    public Vector3f getCameraPosition () {
        return cameraPosition;
    }

    public boolean isPointInViewDistance (Vector3f point) {
        return Vector3f.sub(point, cameraPosition, new Vector3f()).lengthSquared() <= ( Math.pow(viewDistanceInChunks * Chunk.chunkSize, 2) );
    }

    public void pushMatrix (Matrix4f modelMatrix) {
        modelMatrixStack.push(new Matrix4f(renderingModelMatrix));
        Matrix4f.mul(modelMatrix, renderingModelMatrix, renderingModelMatrix);
    }

    public void popMatrix () {
        renderingModelMatrix = modelMatrixStack.pop();
    }
}
