

package com.smithsgaming.transportmanager.util;

import com.smithsgaming.transportmanager.client.*;
import com.smithsgaming.transportmanager.client.registries.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import org.lwjgl.util.vector.*;

import java.io.*;
import java.nio.*;
import java.util.*;

/**
 * CLass that holds wrapper methods for rendering in OpenGL.
 *
 * @Author Marc (Created on: 05.03.2016)
 */
public class OpenGLUtil {

    private static float FOV = 60f;
    private static float aspectRatio = ((float) TransportManagerClient.getDisplay().getResolutionHorizontal() / (float)TransportManagerClient.getDisplay().getResolutionVertical());

    private static Matrix4f projectionMatrix;
    private static Matrix4f viewMatrix;
    private static Matrix4f modelMatrix;

    private static int projectionMatrixAdress;
    private static int viewMatrixAdress;
    private static int modelMatrixAdress;

    private static Vector3f cameraPosition = new Vector3f(0, 0, 0);

    private static Stack<Matrix4f> modelMatrixStack = new Stack<>();
    private static Matrix4f renderingModelMatrix = new Matrix4f();

    private static FloatBuffer projectionMatrixBuffer = BufferUtils.createFloatBuffer(16);
    private static FloatBuffer viewMatrixBuffer = BufferUtils.createFloatBuffer(16);
    private static FloatBuffer modelMatrixBuffer = BufferUtils.createFloatBuffer(16);

    /**
     * Method to load a ShaderProgramm from the Jar. Will return the uncompiled string version of the Sourcecode. Pass this
     * to OpenGL to make it compile the code and push it to the GPU.
     *
     * @param shaderName The name of the Shader to load, should be the File name of the shader in the shaders directory
     *                   without the ending. So if the filename equals: shaders/vertexshader.glsl the string that should
     *                   be passed here is vertexshader.
     *
     * @return A String containing the uncompiled shader code.
     *
     * @throws FileNotFoundException Exception when the shader code file does not exist.
     */
    public static String loadShaderSourceCode (String shaderName) throws FileNotFoundException {
        return ResourceUtil.getFileContents("shaders/" + shaderName + ".glsl");
    }

    /**
     * Method used to compile a Shader from source code.
     *
     * @param shaderType       The type of shader to compile.
     * @param shaderSourceCode The source code of the shader.
     *
     * @return The GL ID of the shader source code once loaded into the GPU.
     */
    public static int compileShader (int shaderType, String shaderSourceCode) {
        int shader = GL20.glCreateShader(shaderType);
        GL20.glShaderSource(shader, shaderSourceCode);
        GL20.glCompileShader(shader);
        int status = GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS);
        if (status == GL11.GL_FALSE) {

            String error = GL20.glGetShaderInfoLog(shader);

            String ShaderTypeString = null;
            switch (shaderType) {
                case GL20.GL_VERTEX_SHADER:
                    ShaderTypeString = "vertex";
                    break;
                case GL32.GL_GEOMETRY_SHADER:
                    ShaderTypeString = "geometry";
                    break;
                case GL20.GL_FRAGMENT_SHADER:
                    ShaderTypeString = "fragment";
                    break;
            }

            System.err.println("Compile failure in " + ShaderTypeString + " shader:\n" + error);
        }

        checkGlState("Compile Shader");

        return shader;
    }

    /**
     * Method used to link several Shaders into a Graphical Pipeline program.
     *
     * @param shaders The GL ID's of the shaders that should be part of the OpenGL Pipeline.
     *
     * @return The GL ID of the OpenGL Graphical Pipeline programm.
     */
    public static int linkShaders (int[] shaders) {
        int program = GL20.glCreateProgram();
        for (int i = 0; i < shaders.length; i++) {
            GL20.glAttachShader(program, shaders[i]);
        }

        // Position information will be attribute 0
        GL20.glBindAttribLocation(program, 0, "in_Position");
        // Color information will be attribute 1
        GL20.glBindAttribLocation(program, 1, "in_Color");
        // Textute information will be attribute 2
        GL20.glBindAttribLocation(program, 2, "in_TextureCoord");

        GL20.glLinkProgram(program);
        GL20.glValidateProgram(program);

        String log = GL20.glGetProgramInfoLog(program);

        if (!log.equals("")) {
            System.err.println("Warning OpenGL log was not empty:");
            System.err.println(log);
        }

        // Get matrices uniform locations
        projectionMatrixAdress = GL20.glGetUniformLocation(program, "projectionMatrix");
        viewMatrixAdress = GL20.glGetUniformLocation(program, "viewMatrix");
        modelMatrixAdress = GL20.glGetUniformLocation(program, "modelMatrix");

        for (int i = 0; i < shaders.length; i++) {
            GL20.glDetachShader(program, shaders[i]);
        }

        checkGlState("Link Shader Program");

        return program;
    }

    /**
     * Method to get the default shader for TM.
     *
     * @return The OpenGL ID of the default TM Shader.
     *
     * @throws FileNotFoundException Exception is thrown when the ShaderLoader could not find the Shader source code
     *                               file.
     */
    public static int loadDefaultShaderProgramm () throws FileNotFoundException {
        if (Shaders.defaultShader != null)
            return Shaders.defaultShader;

        int[] shaders = new int[2];
        shaders[0] = compileShader(GL20.GL_VERTEX_SHADER, loadShaderSourceCode("vertexShaderZoom"));
        shaders[1] = compileShader(GL20.GL_FRAGMENT_SHADER, loadShaderSourceCode("fragmentShaderColor"));

        Shaders.defaultShader = linkShaders(shaders);

        checkGlState("Load Default Shader");

        return Shaders.defaultShader;
    }

    /**
     * Method to load vertex data into the GPU.
     *
     * @param geometry The geometry to load into the GPU.
     */
    public static void loadGeometryIntoGPU (GeometryRegistry.Geometry geometry) {
        int arrayBuffer = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(arrayBuffer);

        int dataBuffer = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, dataBuffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, geometry.getBufferData(), GL15.GL_STATIC_DRAW);

        // Put the position coordinates in attribute list 0
        GL20.glVertexAttribPointer(0, TexturedVertex.positionElementCount, GL11.GL_FLOAT,
                false, TexturedVertex.stride, TexturedVertex.positionByteOffset);
        // Put the color components in attribute list 1
        GL20.glVertexAttribPointer(1, TexturedVertex.colorElementCount, GL11.GL_FLOAT,
                false, TexturedVertex.stride, TexturedVertex.colorByteOffset);
        // Put the texture coordinates in attribute list 2
        GL20.glVertexAttribPointer(2, TexturedVertex.textureElementCount, GL11.GL_FLOAT,
                false, TexturedVertex.stride, TexturedVertex.textureByteOffset);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);

        int indicesBuffer = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, geometry.getType().getIndicesBuffer(), GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        geometry.setOpenGLVertaxArrayId(arrayBuffer);
        geometry.setOpenGLVertexDataId(dataBuffer);
        geometry.setOpenGLVertexIndexID(indicesBuffer);

        checkGlState("Load Geometry");
    }

    public static void loadTextureIntoGPU (TextureRegistry.Texture texture) {
        int texId = GL11.glGenTextures();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);

        // All RGB bytes are aligned to each other and each component is 1 byte
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

        // Upload the texture data and generate mip maps (for scaling)
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, texture.getWidth(), texture.getHeight(), 0,
                GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, texture.getData());
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        // Setup the ST coordinate system
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        // Setup what to do when the texture has to be scaled
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
                GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
                GL11.GL_LINEAR_MIPMAP_LINEAR);

        texture.setOpenGLTextureId(texId);
        texture.setBoundTextureUnit(GL13.GL_TEXTURE0);

        checkGlState("Load Texture");
    }

    /**
     * Method to render an already in the GPU stored piece of Geometry on screen with a specific Shader.
     *
     * @param geometry The Geometry to render.
     * @param texture  The Texture to render the geometry with.
     * @param shaderId The OpenGL Shader Programm ID to use.
     */
    public static void drawGeometryWithShader(GeometryRegistry.Geometry geometry, TextureRegistry.Texture texture, Matrix4f renderMatrix, int shaderId) {
        if (geometry == null)
            return;

        if (projectionMatrix == null)
            createProjectionMatrix();

        if (viewMatrix == null)
            createCameraMatrix();

        GL20.glUseProgram(shaderId);

        setModelMatrix(renderMatrix);

        GL20.glUniformMatrix4fv(projectionMatrixAdress, false, projectionMatrixBuffer);
        GL20.glUniformMatrix4fv(viewMatrixAdress, false, viewMatrixBuffer);
        GL20.glUniformMatrix4fv(modelMatrixAdress, false, modelMatrixBuffer);

        GL13.glActiveTexture(texture.getBoundTextureUnit());
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getOpenGLTextureId());

        GL30.glBindVertexArray(geometry.getOpenGLVertaxArrayId());
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, geometry.getOpenGLVertexIndexID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        GL11.glDrawElements(geometry.getType().getOpenGLRenderType(), geometry.getType().getVertexCount(), GL11.GL_UNSIGNED_BYTE, 0);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);

        GL20.glUseProgram(0);

        checkGlState("Render Geometry");
    }


    /**
     * Method to disable the currently used Shader.
     */
    public static void disableShader () {
        GL20.glUseProgram(0);
    }

    /**
     * Getter for the Field of View of the camera in degrees.
     *
     * @return The field of view of the Camera.
     */
    public static float getFOV () {
        return FOV;
    }

    /**
     * Setter for the Field of View of the camera in degrees.
     *
     * @param FOV The new Field of View for the Camera.
     */
    public static void setFOV (float FOV) {
        OpenGLUtil.FOV = FOV;

        createProjectionMatrix();
    }

    /**
     * Getter for the Aspect ratio.
     *
     * @return The currently in use Aspect ratio.
     */
    public static float getAspectRatio () {
        return aspectRatio;
    }

    /**
     * Setter for the aspect ratio. Should be used to compensate for none square windows.
     *
     * @param aspectRatio The new aspect ration. (screenwidth / screenheight)
     */
    public static void setAspectRatio (float aspectRatio) {
        OpenGLUtil.aspectRatio = aspectRatio;

        createProjectionMatrix();
    }

    public static Vector3f getCameraPosition() {
        return cameraPosition;
    }

    public static void setCameraPosition(Vector3f cameraPosition) {
        OpenGLUtil.cameraPosition = cameraPosition;

        createCameraMatrix();
    }

    @JavadocExclude
    private static void createProjectionMatrix () {
        setProjectionMatrix(MathUtil.CreatePerspectiveFieldOfView(MathUtil.toRadiant(FOV), aspectRatio, 0.1f, 100f));
    }

    @JavadocExclude
    private static void createCameraMatrix () {
        Matrix4f matrix4f = new Matrix4f();

        matrix4f.translate(cameraPosition);

        setViewMatrix(matrix4f);
    }

    private static void createModelMatrix () {
        setModelMatrix(new Matrix4f());
    }

    public static Matrix4f getViewMatrix () {
        return viewMatrix;
    }

    public static void setViewMatrix (Matrix4f viewMatrix) {
        OpenGLUtil.viewMatrix = viewMatrix;

        viewMatrixBuffer.clear();

        viewMatrix.store(viewMatrixBuffer);
        viewMatrixBuffer.flip();
    }

    public static Matrix4f getProjectionMatrix () {
        return projectionMatrix;
    }

    public static void setProjectionMatrix (Matrix4f projectionMatrix) {
        OpenGLUtil.projectionMatrix = projectionMatrix;

        projectionMatrix.store(projectionMatrixBuffer);
        projectionMatrixBuffer.flip();
    }

    public static Matrix4f getModelMatrix () {
        return modelMatrix;
    }

    public static void setModelMatrix (Matrix4f modelMatrix) {
        OpenGLUtil.modelMatrix = modelMatrix;

        modelMatrix.store(modelMatrixBuffer);
        modelMatrixBuffer.flip();
    }

    public static void pushMatrix() {
        modelMatrixStack.push(modelMatrix);
    }

    public static void destroyTexture (TextureRegistry.Texture texture) {
        GL11.glDeleteTextures(texture.getOpenGLTextureId());
    }

    public static void deleteGeometry (GeometryRegistry.Geometry geometry) {
        // Select the VAO
        GL30.glBindVertexArray(geometry.getOpenGLVertaxArrayId());

        // Disable the VBO index from the VAO attributes list
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);

        // Delete the vertex VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(geometry.getOpenGLVertexDataId());

        // Delete the index VBO
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(geometry.getOpenGLVertexIndexID());

        // Delete the VAO
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(geometry.getOpenGLVertaxArrayId());

    }

    public static void deleteShader (int programmId) {
        GL20.glUseProgram(0);
        GL20.glDeleteProgram(programmId);

    }

    private static void checkGlState (String snapshotMoment) {
        int errorValue = GL11.glGetError();

        if (errorValue != GL11.GL_NO_ERROR) {
            System.err.println("ERROR - " + snapshotMoment + ": " + errorValue);
        }
    }

    public static class Shaders {
        public static Integer defaultShader = null;
    }

}
