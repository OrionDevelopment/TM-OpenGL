

/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.util;

import com.smithsgaming.transportmanager.client.TransportManagerClient;
import com.smithsgaming.transportmanager.client.graphics.Camera;
import com.smithsgaming.transportmanager.client.graphics.Display;
import com.smithsgaming.transportmanager.client.render.core.Shader;
import com.smithsgaming.transportmanager.client.render.core.geometry.Geometry;
import com.smithsgaming.transportmanager.client.render.core.textures.Texture;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.io.FileNotFoundException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glTexSubImage2D;

/**
 * CLass that holds wrapper methods for rendering in OpenGL.
 *
 *  ------ Class not Documented ------
 */
public class OpenGLUtil {

    private static int occlusionQuerry = 0;

    private static float FOV = 60f;
    private static float aspectRatio = ((float) TransportManagerClient.getDisplay().getResolutionHorizontal() / (float)TransportManagerClient.getDisplay().getResolutionVertical());

    private static Matrix4f modelMatrix;

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

    public static void loadShaderProgramm(Shader shader)
    {
        if (shader.getShaderId() > -1)
        {
            throw new IllegalArgumentException("Shader already loaded.");
        }

        int[] shaders = new int[2];
        shaders[0] = compileShader(GL20.GL_VERTEX_SHADER, shader.getVertexShaderSourceCode());
        shaders[1] = compileShader(GL20.GL_FRAGMENT_SHADER, shader.getFragmentShaderSourceCode());

        shader.setShaderId(linkShaders(shader, shaders));

        checkGlState("Load Shader");
    }

    /**
     * Method used to compile a Shader from source code.
     *
     * @param shaderType       The type of shader to compile.
     * @param shaderSourceCode The source code of the shader.
     *
     * @return The GL ID of the shader source code once loaded into the GPU.
     */
    private static int compileShader (int shaderType, String shaderSourceCode) {
        int shader = GL20.glCreateShader(shaderType);
        GL20.glShaderSource(shader, shaderSourceCode);
        GL20.glCompileShader(shader);
        int status = GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS);
        if (status == GL11.GL_FALSE) {

            String error = GL20.glGetShaderInfoLog(shader, 150);

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
    private static int linkShaders (Shader shader, int[] shaders) {
        int program = GL20.glCreateProgram();
        for (int i = 0; i < shaders.length; i++) {
            GL20.glAttachShader(program, shaders[i]);
        }

        shader.getInformation().upLoadVertexAttributesForShader(program);

        GL20.glLinkProgram(program);
        GL20.glValidateProgram(program);

        String log = GL20.glGetProgramInfoLog(program, 150);

        if (!log.equals("")) {
            Display.displayLogger.error("Warning OpenGL log was not empty:");
            Display.displayLogger.error(log);
        }

        // Get matrices uniform locations
        shader.setProjectionMatrixIndex(GL20.glGetUniformLocation(program, "projectionMatrix"));
        shader.setViewMatrixIndex(GL20.glGetUniformLocation(program, "viewMatrix"));
        shader.setModelMatrixIndex(GL20.glGetUniformLocation(program, "modelMatrix"));
        shader.setColorIndex(GL20.glGetUniformLocation(program, "color"));

        for (int i = 0; i < shaders.length; i++) {
            GL20.glDetachShader(program, shaders[i]);
        }

        checkGlState("Link Shader Program");

        return program;
    }

    public static void checkGlState(String snapshotMoment)
    {
        int errorValue = GL11.glGetError();

        if (errorValue != GL11.GL_NO_ERROR)
        {
            Display.displayLogger.error("ERROR - " + snapshotMoment + ": " + errorValue);
        }
    }

    /**
     * Method to load vertex data into the GPU.
     *
     * @param geometry The geometry to load into the GPU.
     */
    public static void loadGeometryIntoGPU (Geometry geometry) {
        int arrayBuffer = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(arrayBuffer);

        int dataBuffer = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, dataBuffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, geometry.getBufferData(), GL15.GL_STATIC_DRAW);

        geometry.getInformation().upLoadVertexAttributesForGeometry();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);

        int indicesBuffer = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, geometry.getIndicesBuffer(), GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        geometry.setOpenGLVertaxArrayId(arrayBuffer);
        geometry.setOpenGLVertexDataId(dataBuffer);
        geometry.setOpenGLVertexIndexID(indicesBuffer);

        checkGlState("Load TestGeometry");

        geometry.onLoaded();
    }

    public static void loadTextureIntoGPU (Texture texture) {
        int texId = GL11.glGenTextures();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);

        // All RGB bytes are aligned to each other and each component is 1 byte
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

        // Upload the texture data and generate mip maps (for scaling)
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, texture.getInternalFormat(), texture.getPixelWidth(), texture.getPixelHeight(), 0,
                texture.getFormat(), GL11.GL_UNSIGNED_BYTE, texture.getData());
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        // Setup the ST coordinate system
        texture.setGlTextureOptions();

        // Setup what to do when the texture has to be scaled
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
                GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
                GL11.GL_NEAREST_MIPMAP_LINEAR);

        texture.setOpenGLTextureId(texId);
        texture.setBoundTextureUnit(GL13.GL_TEXTURE0);

        checkGlState("Load Texture");
    }

    /**
     * Method to load a texture into a other texture.
     *
     * @param masterTexture The texture to load into.
     * @param subTexture    The texture to copy from.
     */
    public static void loadSubTextureRegionIntoGPU (Texture masterTexture, Texture subTexture) {
        if (masterTexture.getFormat() != subTexture.getFormat())
            throw new IllegalStateException("The given sub and mastertextures are not compatible.");

        GL13.glActiveTexture(masterTexture.getBoundTextureUnit());
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, masterTexture.getOpenGLTextureId());
        glTexSubImage2D(GL_TEXTURE_2D, 0, subTexture.getOriginX(), subTexture.getOriginY(), subTexture.getPixelWidth(), subTexture.getPixelHeight(), masterTexture.getFormat(), GL11.GL_UNSIGNED_BYTE, subTexture.getData());

        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        checkGlState("Subtexture loading. Master: " + masterTexture.getTextureName() + " - Sub: " + subTexture.getTextureName());
    }

    /**
     * Method to activate an already in the GPU stored texture for rendering on screen.
     *
     * @param texture  The Texture to render the geometry with.
     */
    public static void activateTextureInGPU (Texture texture) {
        if (texture == null)
            return;

        GL13.glActiveTexture(texture.getBoundTextureUnit());
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getOpenGLTextureId());
    }

    /**
     * Method to render an already in the GPU stored piece of TestGeometry on screen with a specific Shader and Texture.
     *
     * @param geometry The TestGeometry to render.
     * @param texture  The Texture to render the geometry with.
     * @param shader The OpenGL Shader ID to use.
     */
    public static void drawGeometryWithShaderAndTexture (Camera camera, Geometry geometry, Texture texture, Shader shader) {
        if (geometry == null)
            return;

        GL13.glActiveTexture(texture.getBoundTextureUnit());
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getOpenGLTextureId());

        drawGeometryWithShader(camera, geometry, shader);
    }

    /**
     * Method to render an already in the GPU stored piece of TestGeometry on screen with a specific Shader.
     *
     * @param geometry The TestGeometry to render.
     * @param shader   The OpenGL Shader ID to use.
     */
    public static void drawGeometryWithShader (Camera camera, Geometry geometry, Shader shader) {
        if (geometry == null)
            return;

        GL20.glUseProgram(shader.getShaderId());

        GL20.glUniformMatrix4fv(shader.getProjectionMatrixIndex(), false, camera.getProjectionMatrixBuffer());
        GL20.glUniformMatrix4fv(shader.getViewMatrixIndex(), false, camera.getViewMatrixBuffer());
        GL20.glUniformMatrix4fv(shader.getModelMatrixIndex(), false, camera.getRenderingModelMatrixBuffer());
        GL20.glUniform4fv(shader.getColorIndex(), camera.getActiveColorBuffer());

        GL30.glBindVertexArray(geometry.getOpenGLVertaxArrayId());
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, geometry.getOpenGLVertexIndexID());
        geometry.getInformation().enableVertexAttributesForRender();

        if (geometry.requiresResetting()) {
            GL11.glEnable(GL31.GL_PRIMITIVE_RESTART);
            GL31.glPrimitiveRestartIndex(geometry.getResetIndex());
        }

        GL11.glDrawElements(geometry.getType().getOpenGLRenderType(), geometry.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

        if (geometry.requiresResetting()) {
            GL11.glDisable(GL31.GL_PRIMITIVE_RESTART);
        }

        geometry.getInformation().disableVertexAttributesForRender();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);

        GL20.glUseProgram(0);

        checkGlState("Render TestGeometry");
    }

    /**
     * Method to setup the queries used in this Util class.
     * Is usually called automatically.
     */
    public static void setupQuerries()
    {
        if (occlusionQuerry == 0)
        {
            occlusionQuerry = GL15.glGenQueries();
        }
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

        Camera.Player.updateProjectionMatrix();
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

        Camera.Player.updateProjectionMatrix();
    }

    public static Matrix4f getModelMatrix () {
        return modelMatrix;
    }

    public static void setModelMatrix (Matrix4f modelMatrix) {
        OpenGLUtil.modelMatrix = modelMatrix;

        modelMatrix.get(modelMatrixBuffer);
        modelMatrixBuffer.flip();
    }

    public static void destroyTexture (Texture texture) {
        GL11.glDeleteTextures(texture.getOpenGLTextureId());
    }

    public static void deleteGeometry (Geometry geometry) {
        // Select the VAO
        GL30.glBindVertexArray(geometry.getOpenGLVertaxArrayId());

        // Disable the VBO index from the VAO attributes list
        geometry.getInformation().unLoadVertexAttributesForGeometry();

        // Delete the vertex VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(geometry.getOpenGLVertexDataId());

        // Delete the index VBO
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(geometry.getOpenGLVertexIndexID());

        // Delete the VAO
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(geometry.getOpenGLVertaxArrayId());

        geometry.onDestroyed();
    }

    public static void deleteShader (Shader shader) {
        GL20.glUseProgram(0);
        GL20.glDeleteProgram(shader.getShaderId());
    }
}
