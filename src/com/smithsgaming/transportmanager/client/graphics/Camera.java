package com.smithsgaming.transportmanager.client.graphics;

import com.smithsgaming.transportmanager.client.*;
import com.smithsgaming.transportmanager.main.TransportManager;
import com.smithsgaming.transportmanager.network.client.TMNetworkingClient;
import com.smithsgaming.transportmanager.util.GraphicUtil;
import com.smithsgaming.transportmanager.main.world.chunk.*;
import com.smithsgaming.transportmanager.util.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.*;

import java.awt.*;
import java.nio.*;
import java.util.*;

/**
 * @Author Marc (Created on: 17.03.2016)
 */
public class Camera {

    public static final Camera Gui = new Camera();
    public static final Camera Player = new Camera(MathUtil.toRadiant(-90), new Vector3f(1, 0, 0)).moveCamera(new Vector3f(0, 10f, 0));
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
    private float viewDistanceInChunks = 6;
    private Color activeColor = (Color) Color.WHITE;
    private FloatBuffer activeColorBuffer = BufferUtils.createFloatBuffer(4);
    private Frustum activeFrustum;

    public Camera () {
        this.projectionMatrix = com.smithsgaming.transportmanager.util.MathUtil.CreateOrthogonalFieldOfView(0f, 100f);
        this.projectionMatrix.get(projectionMatrixBuffer);
        //this.projectionMatrixBuffer.flip();

        this.viewMatrix = new Matrix4f();
        this.viewMatrix.get(this.viewMatrixBuffer);
        //this.viewMatrixBuffer.flip();

        this.activeFrustum = new Frustum(this);

        setActiveColor(activeColor);
        renderingModelMatrix.scale(new Vector3f(2f / GuiScale.FWVGA.getHorizontalResolution(), -2f / GuiScale.FWVGA.getVerticalResolution(), 1f));
    }

    public Camera (float angle, Vector3f rotationAxis) {
        this.projectionMatrix = com.smithsgaming.transportmanager.util.MathUtil.CreatePerspectiveFieldOfView(com.smithsgaming.transportmanager.util.MathUtil.toRadiant(OpenGLUtil.getFOV()), OpenGLUtil.getAspectRatio(), 0.1f, 550f);
        this.projectionMatrix.get(projectionMatrixBuffer);
        //this.projectionMatrixBuffer.flip();


        this.viewMatrix = new Matrix4f();
        this.viewMatrix.get(this.viewMatrixBuffer);
        //this.viewMatrixBuffer.flip();

        this.activeFrustum = new Frustum(this);

        rotateCamera(angle, rotationAxis);
    }

    /**
     * Method to create a perspective View, should be used for WorldRendering.
     */
    private void createProjectionMatrix () {
        setProjectionMatrix(com.smithsgaming.transportmanager.util.MathUtil.CreatePerspectiveFieldOfView(com.smithsgaming.transportmanager.util.MathUtil.toRadiant(OpenGLUtil.getFOV()), OpenGLUtil.getAspectRatio(), 0.1f, 550f));
    }

    /**
     * Method to create the default camera matrix.
     */
    private void createCameraMatrix () {
        Matrix4f matrix4f = new Matrix4f();
        setViewMatrix(matrix4f);
    }

    public void updateProjectionMatrix () {
        com.smithsgaming.transportmanager.util.MathUtil.CreatePerspectiveFieldOfView(com.smithsgaming.transportmanager.util.MathUtil.toRadiant(OpenGLUtil.getFOV()), OpenGLUtil.getAspectRatio(), 0.1f, 550f);
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
        projectionMatrix.get(projectionMatrixBuffer);
        //projectionMatrixBuffer.flip();

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
        viewMatrix.get(viewMatrixBuffer);
        //viewMatrixBuffer.flip();

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
            renderingModelMatrix.get(renderingModelMatrixBuffer);
            //renderingModelMatrixBuffer.flip();
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
        cameraPosition.add(moveDelta);

        viewMatrixBuffer.clear();
        viewMatrix.get(viewMatrixBuffer);
        //viewMatrixBuffer.flip();

        activeFrustum.updateFrustum();

        TransportManagerClient.clientLogger.trace("New Cameraposition:" + cameraPosition.toString());

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
        viewMatrix.get(viewMatrixBuffer);
        //viewMatrixBuffer.flip();

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
     * Getter for the active rendering color.
     *
     * @return The active rendering color.
     */
    public Color getActiveColor () {
        return activeColor;
    }

    /**
     * Setter for the active color.
     *
     * @param activeColor The new active color.
     */
    public void setActiveColor (Color activeColor) {
        this.activeColor = activeColor;

        GraphicUtil.storeColor(activeColorBuffer, activeColor);
    }

    /**
     * Getter for the active color buffer.
     *
     * @return The active color buffer.
     */
    public FloatBuffer getActiveColorBuffer () {
        return activeColorBuffer;
    }

    /**
     * Method to check if a given point is in the ViewDistance of this Camera.
     *
     * @param point The point to check.
     *
     * @return True if the point is in the ViewDistance, false when not.
     */
    public boolean isPointInViewDistance (Vector3f point) {
        return point.sub(cameraPosition, new Vector3f()).lengthSquared() <= ( Math.pow(viewDistanceInChunks * Chunk.chunkSize, 2) );
    }

    /**
     * Method to update the GuiScale before a render run is made.
     *
     * @param horizontalScale The horizontal GUI Scale.
     * @param verticalScale   The vertical GUI scale.
     */
    public void updateGuiScale (float horizontalScale, float verticalScale) {
        if (getMatrixStackCount() == 1)
            popMatrix();

        scaleModel(new Vector3f(horizontalScale, verticalScale, 1f));
        pushMatrix();
    }

    /**
     * Method to push the current rendering matrix on the stack and absorb the acting matrix. Is automatically called
     * when rendering. So if you make changes to the ModelMatrix before rendering you will need to call popmatrix at
     * least once.
     */
    public void pushMatrix () {
        modelMatrixStack.push(new Matrix4f(renderingModelMatrix));

        currentActingMatrix.mul(renderingModelMatrix, renderingModelMatrix);
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
     * Method to get the current amount of matrix's on the stack. Does not take the current in use matrix into account.
     *
     * @return The amount of matrix's on the stack.
     */
    public int getMatrixStackCount () {
        return modelMatrixStack.size();
    }

    /**
     * Method to translate the current in use ModelMatrix.
     *
     * @param modelTranslation The translation to perform.
     */
    public void translateModel (Vector3f modelTranslation) {
        currentActingMatrix.translate(new Vector3f(modelTranslation.x() / (TransportManagerClient.instance.getSettings().getCurrentScale().getHorizontalResolution() / 2), modelTranslation.y() / (TransportManagerClient.instance.getSettings().getCurrentScale().getVerticalResolution() / 2), modelTranslation.z()));
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
