package com.smithsgaming.transportmanager.client.registries;

import com.smithsgaming.transportmanager.util.*;

import java.io.*;
import java.util.*;

/**
 * @Author Marc (Created on: 06.03.2016)
 */
public class ShaderRegistry {

    public static final ShaderRegistry instance = new ShaderRegistry();

    private HashMap<Integer, Shader> openGLShaderMap = new HashMap<>();

    private ShaderRegistry () {
    }

    public int registerNewShader (Shader shader) {
        OpenGLUtil.loadShaderProgramm(shader);
        openGLShaderMap.put(shader.getShaderId(), shader);

        return shader.getShaderId();
    }

    public Shader getGeometryForOpenGLID (int openGLID) {
        return openGLShaderMap.get(openGLID);
    }

    public void unLoad () {
        openGLShaderMap.values().forEach(OpenGLUtil::deleteShader);

        openGLShaderMap.clear();
    }

    public static class Shader {

        private int shaderId = -1;

        private String vertexShaderSourceCode;
        private String fragmentShaderSourceCode;

        private int projectionMatrixIndex;
        private int viewMatrixIndex;
        private int modelMatrixIndex;

        public Shader (String vertexShaderFileName, String fragmentShaderFileName) throws FileNotFoundException {
            vertexShaderSourceCode = OpenGLUtil.loadShaderSourceCode(vertexShaderFileName);
            fragmentShaderSourceCode = OpenGLUtil.loadShaderSourceCode(fragmentShaderFileName);
        }

        public int getShaderId () {
            return shaderId;
        }

        public void setShaderId (int shaderId) {
            this.shaderId = shaderId;
        }

        public String getVertexShaderSourceCode () {
            return vertexShaderSourceCode;
        }

        public String getFragmentShaderSourceCode () {
            return fragmentShaderSourceCode;
        }

        public int getProjectionMatrixIndex () {
            return projectionMatrixIndex;
        }

        public void setProjectionMatrixIndex (int projectionMatrixIndex) {
            this.projectionMatrixIndex = projectionMatrixIndex;
        }

        public int getViewMatrixIndex () {
            return viewMatrixIndex;
        }

        public void setViewMatrixIndex (int viewMatrixIndex) {
            this.viewMatrixIndex = viewMatrixIndex;
        }

        public int getModelMatrixIndex () {
            return modelMatrixIndex;
        }

        public void setModelMatrixIndex (int modelMatrixIndex) {
            this.modelMatrixIndex = modelMatrixIndex;
        }
    }

    public static class Shaders {

        public static Shader colored;
        public static Shader textured;

        static {
            try {
                colored = new Shader("vertexShaderZoom", "fragmentShaderColor");
                textured = new Shader("vertexShaderZoom", "fragmentShaderTextured");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public static void init () {
            ShaderRegistry.instance.registerNewShader(colored);
            ShaderRegistry.instance.registerNewShader(textured);
        }
    }

}
