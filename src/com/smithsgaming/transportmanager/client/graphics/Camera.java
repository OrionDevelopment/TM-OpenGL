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
    private Stack<Matrix4f> modelMatrixStack = new Stack<>();
    private Matrix4f renderingModelMatrix = new Matrix4f();
    private Matrix4f currentActingMatrix = new Matrix4f();
    private FloatBuffer renderingModelMatrixBuffer = BufferUtils.createFloatBuffer(16);
    private boolean isActingMatrixLive = true;
    private boolean updateModelMatrixBuffer = true;
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
     * Getter for a copy of the ProjectionMatrix
     *
     * Changing the return has no effect on the actual ProjectionMatrixMatrix and is not recommended.
     *
     * @return A copy of the current ProjectionMatrix.
     */
    public Matrix4f getProjectionMatrix () {
        return new Matrix4f(projectionMatrix);
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

    /**
     * Getter for a copy of the ViewMatrix
     * <p>
     * Changing the return has no effect on the actual ViewMatrix and is not recommended. Use the methods in this class
     * if you want to rotate or translate the camera. Never scale the View!
     *
     * @return A copy of the current ViewMatrix.
     */
    public Matrix4f getViewMatrix () {
        return new Matrix4f(viewMatrix);
    }

    /**
     * Private setter for the ViewMatrix. Automatically updates the Buffer.
     *
     * @param viewMatrix The new ViewMatrix.
     */
    private void setViewMatrix (Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;

        viewMatrixBuffer.clear();
        viewMatrix.store(viewMatrixBuffer);
        viewMatrixBuffer.flip();

        activeFrustum.updateFrustum();
    }

    /**
     * Getter for the ProjectionMatrix FloatBuffer.
     *
     * @return The float buffer of the Projection Matrix.
     */
    public FloatBuffer getProjectionMatrixBuffer () {
        return projectionMatrixBuffer;
    }

    /**
     * Getter for the ViewMatrix FloatBuffer.
     *
     * @return The float buffer of the View Matrix.
     */
    public FloatBuffer getViewMatrixBuffer () {
        return viewMatrixBuffer;
    }

    /**
     * Getter for the RenderingModelMatrix FloatBuffer. First performs some checks if the buffer should be updated or
     * not, if so it will perform a push matrix action and update the buffer.
     *
     * @return The current float buffer of the rendering model matrix.
     */
    public FloatBuffer getRenderingModelMatrixBuffer () {
        if (!isActingMatrixLive) {
            updateModelMatrixBuffer = true;
            pushMatrix();
        }

        if (updateModelMatrixBuffer) {
            renderingModelMatrixBuffer.clear();
            renderingModelMatrix.store(renderingModelMatrixBuffer);
            renderingModelMatrixBuffer.flip();
        }

        return renderingModelMatrixBuffer;
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

    /**
     * Method to get the active Frustum.
     *
     * @return The frustum that is in use by the camera.
     */
    public Frustum getActiveFrustum () {
        return activeFrustum;
    }

    /**
     * Getter for the Camera position. In OpenGL Coordinates (So the Axis directions are inverted).
     *
     * @return The camera position.
     */
    public Vector3f getCameraPosition () {
        return cameraPosition;
    }

    /**
     * Method to check if a given point is in the ViewDistance of this Camera.
     *
     * @param point The point to check.
     *
     * @return True if the point is in the ViewDistance, false when not.
     */
    public boolean isPointInViewDistance (Vector3f point) {
        return Vector3f.sub(point, cameraPosition, new Vector3f()).lengthSquared() <= ( Math.pow(viewDistanceInChunks * Chunk.chunkSize, 2) );
    }

    /**
     * Method to push the current rendering matrix on the stack and absorb the acting matrix. Is automatically called
     * when rendering. So if you make changes to the ModelMatrix before rendering you will need to call popmatrix at
     * least once.
     */
    public void pushMatrix () {
        modelMatrixStack.push(new Matrix4f(renderingModelMatrix));

        Matrix4f.mul(currentActingMatrix, renderingModelMatrix, renderingModelMatrix);
        currentActingMatrix = new Matrix4f();

        isActingMatrixLive = true;
    }

    /**
     * Method to revert the changes made to the renderingmatrix to the last state by popping it from the stack.
     */
    public void popMatrix () {
        renderingModelMatrix = modelMatrixStack.pop();

        currentActingMatrix = new Matrix4f();
        isActingMatrixLive = true;
        updateModelMatrixBuffer = true;
    }

    /**
     * Method to translate the current in use ModelMatrix.
     *
     * @param modelTranslation The translation to perform.
     */
    public void translateModel (Vector3f modelTranslation) {
        currentActingMatrix.translate(modelTranslation);
        isActingMatrixLive = false;
    }

    /**
     * Method to rotate the current in use ModelMatrix.
     *
     * @param angle The angle to rotate over.
     * @param axis  A vector indicating which axis to rotate.
     */
    public void rotateModel (float angle, Vector3f axis) {
        currentActingMatrix.rotate(angle, axis);
        isActingMatrixLive = false;
    }

    /**
     * Method to scale the current in use ModelMatrix.
     *
     * @param axisScaling A vector indicating the scaling.
     */
    public void scaleModel (Vector3f axisScaling) {
        currentActingMatrix.scale(axisScaling);
        isActingMatrixLive = false;
    }
}
