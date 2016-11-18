package com.smithsgaming.transportmanager.client.render.core;

import com.smithsgaming.transportmanager.client.registries.GeometryRegistry;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by marcf on 11/18/2016.
 */
public class Geometry {
    protected GeometryRegistry.GeometryType type;
    protected TexturedVertex[] vertices;
    protected VertexInformation information;

    int openGLVertaxArrayId;
    int openGLVertexDataId;
    int openGLVertexIndexID;

    public Geometry(GeometryRegistry.GeometryType type, TexturedVertex[] vertices, VertexInformation information) {
        this.type = type;
        this.vertices = vertices;
        this.information = information;
    }

    public TexturedVertex[] getVertices() {
        return vertices;
    }

    public FloatBuffer getBufferData() {
        FloatBuffer data = BufferUtils.createFloatBuffer(getInformation().getStride() * vertices.length);
        for (TexturedVertex vertex : vertices) {
            data.put(vertex.getElements());
        }

        data.flip();

        return data;
    }

    public GeometryRegistry.GeometryType getType() {
        return type;
    }

    public int getOpenGLVertaxArrayId() {
        return openGLVertaxArrayId;
    }

    public void setOpenGLVertaxArrayId(int openGLVertaxArrayId) {
        this.openGLVertaxArrayId = openGLVertaxArrayId;
    }

    public int getOpenGLVertexDataId() {
        return openGLVertexDataId;
    }

    public void setOpenGLVertexDataId(int openGLVertexDataId) {
        this.openGLVertexDataId = openGLVertexDataId;
    }

    public int getOpenGLVertexIndexID() {
        return openGLVertexIndexID;
    }

    public void setOpenGLVertexIndexID(int openGLVertexIndexID) {
        this.openGLVertexIndexID = openGLVertexIndexID;
    }

    public int getVertexCount() {

        return getType().getVertexCount();
    }

    public boolean requiresResetting() {
        return getType().requiresResetting();
    }

    public int getResetIndex() {
        return getType().getResetIndex();
    }

    public IntBuffer getIndicesBuffer() {
        return getType().getIndicesBuffer();
    }

    public VertexInformation getInformation() {
        return information;
    }
}
