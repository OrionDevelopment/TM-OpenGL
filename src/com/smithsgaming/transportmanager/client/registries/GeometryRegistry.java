package com.smithsgaming.transportmanager.client.registries;

import com.smithsgaming.transportmanager.util.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;

import java.nio.*;
import java.util.*;

/**
 * @Author Marc (Created on: 06.03.2016)
 */
public class GeometryRegistry {
    public static final GeometryRegistry instance = new GeometryRegistry();

    private static Integer triangleOpenGLID = null;
    private static Integer quadOpenGLID = null;

    private HashMap<Integer, Geometry> openGLGeometryMap = new HashMap<>();

    private GeometryRegistry () {
    }

    public static int getDefaultTriangleGeometryOpenGLID () {
        if (triangleOpenGLID != null)
            return triangleOpenGLID;

        triangleOpenGLID = instance.registerNewGeometry(new TriangleGeometry());

        return triangleOpenGLID;
    }

    public static TriangleGeometry getDefaultTriangleGeometry () {
        return (TriangleGeometry) instance.getGeometryForOpenGLID(getDefaultTriangleGeometryOpenGLID());
    }

    public static int getDefaultQuadGeometryOpenGLID () {
        if (quadOpenGLID != null)
            return quadOpenGLID;

        quadOpenGLID = instance.registerNewGeometry(new QuadGeometry());

        return quadOpenGLID;
    }

    public static QuadGeometry getDefaultQuadGeometry () {
        return (QuadGeometry) instance.getGeometryForOpenGLID(getDefaultQuadGeometryOpenGLID());
    }

    public int registerNewGeometry (Geometry geometry) {
        OpenGLUtil.loadGeometryIntoGPU(geometry);
        openGLGeometryMap.put(geometry.getOpenGLVertaxArrayId(), geometry);

        return geometry.getOpenGLVertaxArrayId();
    }

    public Geometry getGeometryForOpenGLID (int openGLID) {
        return openGLGeometryMap.get(openGLID);
    }

    public void unLoad () {
        openGLGeometryMap.values().forEach(OpenGLUtil::deleteGeometry);

        openGLGeometryMap.clear();
    }

    public enum GeometryType {
        TRIANGLE(3, GL11.GL_TRIANGLES, (byte) 0, (byte) 1, (byte) 2),
        QUAD(4, GL11.GL_TRIANGLE_STRIP, (byte) 0, (byte) 1, (byte) 2, (byte) 3);

        private int vertexCount;
        private byte[] vertexOrder;
        private int openGLRenderType;

        GeometryType (int vertexCount, int openGLRenderType, byte... vertexOrder) {
            this.vertexCount = vertexCount;
            this.openGLRenderType = openGLRenderType;
            this.vertexOrder = vertexOrder;
        }

        public int getOpenGLRenderType () {
            return openGLRenderType;
        }

        public byte[] getVertexOrder () {
            return vertexOrder;
        }

        public int getVertexCount () {

            return vertexCount;
        }

        public ByteBuffer getIndicesBuffer () {
            ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(vertexOrder.length);
            indicesBuffer.put(vertexOrder);
            indicesBuffer.flip();

            return indicesBuffer;
        }
    }

    public static class Geometry {
        GeometryType type;
        TexturedVertex[] vertices;

        int openGLVertaxArrayId;
        int openGLVertexDataId;
        int openGLVertexIndexID;

        public Geometry (GeometryType type, TexturedVertex[] vertices) {
            this.type = type;
            this.vertices = vertices;
        }

        public FloatBuffer getBufferData () {
            FloatBuffer data = BufferUtils.createFloatBuffer(TexturedVertex.stride * vertices.length);
            for (TexturedVertex vertex : vertices) {
                data.put(vertex.getElements());
            }

            data.flip();

            return data;
        }

        public GeometryType getType () {
            return type;
        }

        public int getOpenGLVertaxArrayId () {
            return openGLVertaxArrayId;
        }

        public void setOpenGLVertaxArrayId (int openGLVertaxArrayId) {
            this.openGLVertaxArrayId = openGLVertaxArrayId;
        }

        public int getOpenGLVertexDataId () {
            return openGLVertexDataId;
        }

        public void setOpenGLVertexDataId (int openGLVertexDataId) {
            this.openGLVertexDataId = openGLVertexDataId;
        }

        public int getOpenGLVertexIndexID () {
            return openGLVertexIndexID;
        }

        public void setOpenGLVertexIndexID (int openGLVertexIndexID) {
            this.openGLVertexIndexID = openGLVertexIndexID;
        }
    }

    public static class TriangleGeometry extends Geometry {
        private static final TexturedVertex top = new TexturedVertex().setRGB(1f, 1f, 1f).setST(0, 1).setXYZ(0, 1, 1);
        private static final TexturedVertex left = new TexturedVertex().setRGB(1f, 1f, 1f).setST(0, 0).setXYZ(0, 0, 1);
        private static final TexturedVertex right = new TexturedVertex().setRGB(1f, 1f, 1f).setST(1, 0).setXYZ(1, 0, 1);

        public TriangleGeometry () {
            super(GeometryType.TRIANGLE, new TexturedVertex[]{top, right, left});
        }
    }

    public static class QuadGeometry extends Geometry {
        private static final TexturedVertex topLeft = new TexturedVertex().setRGB(1f, 0f, 0f).setST(0, 1).setXYZ(-0.5f, 0.5f, -1);
        private static final TexturedVertex topRight = new TexturedVertex().setRGB(0f, 1f, 0f).setST(1, 1).setXYZ(0.5f, 0.5f, -1);
        private static final TexturedVertex bottomRight = new TexturedVertex().setRGB(0f, 0f, 0f).setST(1, 0).setXYZ(0.5f, -0.5f, -1);
        private static final TexturedVertex bottomLeft = new TexturedVertex().setRGB(0f, 0f, 1f).setST(0, 0).setXYZ(-0.5f, -0.5f, -1);

        public QuadGeometry () {
            super(GeometryType.QUAD, new TexturedVertex[]{topLeft, bottomLeft, topRight, bottomRight});
        }
    }
}
