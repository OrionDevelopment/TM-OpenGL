package com.smithsgaming.transportmanager.client.render.core;

import com.smithsgaming.transportmanager.util.OpenGLUtil;

import java.io.FileNotFoundException;

/**
 * Created by marcf on 11/18/2016.
 */
public class Shader {

    private int shaderId = -1;

    private String vertexShaderSourceCode;
    private String fragmentShaderSourceCode;

    private int projectionMatrixIndex;
    private int viewMatrixIndex;
    private int modelMatrixIndex;
    private int colorIndex;

    private VertexInformation information;

    public Shader(String vertexShaderFileName, String fragmentShaderFileName, VertexInformation information) throws FileNotFoundException {
        vertexShaderSourceCode = OpenGLUtil.loadShaderSourceCode(vertexShaderFileName);
        fragmentShaderSourceCode = OpenGLUtil.loadShaderSourceCode(fragmentShaderFileName);
        this.information = information;
    }

    public int getShaderId() {
        return shaderId;
    }

    public void setShaderId(int shaderId) {
        this.shaderId = shaderId;
    }

    public String getVertexShaderSourceCode() {
        return vertexShaderSourceCode;
    }

    public void setVertexShaderSourceCode(String vertexShaderSourceCode)
    {
        this.vertexShaderSourceCode = vertexShaderSourceCode;
    }

    public String getFragmentShaderSourceCode() {
        return fragmentShaderSourceCode;
    }

    public int getProjectionMatrixIndex() {
        return projectionMatrixIndex;
    }

    public void setProjectionMatrixIndex(int projectionMatrixIndex) {
        this.projectionMatrixIndex = projectionMatrixIndex;
    }

    public int getViewMatrixIndex() {
        return viewMatrixIndex;
    }

    public void setViewMatrixIndex(int viewMatrixIndex) {
        this.viewMatrixIndex = viewMatrixIndex;
    }

    public int getModelMatrixIndex() {
        return modelMatrixIndex;
    }

    public void setModelMatrixIndex(int modelMatrixIndex) {
        this.modelMatrixIndex = modelMatrixIndex;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(int colorIndex) {
        this.colorIndex = colorIndex;
    }

    public VertexInformation getInformation() {
        return information;
    }
}
