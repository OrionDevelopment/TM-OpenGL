package com.smithsgaming.transportmanager.client.registries;

import com.smithsgaming.transportmanager.client.render.core.Geometry;
import com.smithsgaming.transportmanager.client.render.core.TexturedVertex;
import com.smithsgaming.transportmanager.client.render.core.VertexInformation;
import com.smithsgaming.transportmanager.util.*;
import com.smithsgaming.transportmanager.util.math.graphical.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;

import java.awt.*;
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
        TRIANGLE(3, GL11.GL_TRIANGLES, -1, 0, 1, 2),
        QUAD(4, GL11.GL_TRIANGLE_STRIP, -1, 0, 1, 2, 3);

        private int vertexCount;
        private int[] vertexOrder;
        private int openGLRenderType;
        private int resetIndex;

        GeometryType (int vertexCount, int openGLRenderType, int resetIndex, int... vertexOrder) {
            this.vertexCount = vertexCount;
            this.openGLRenderType = openGLRenderType;
            this.vertexOrder = vertexOrder;
            this.resetIndex = resetIndex;
        }

        public int getOpenGLRenderType () {
            return openGLRenderType;
        }

        public int[] getVertexOrder () {
            return vertexOrder;
        }

        public int getVertexCount () {

            return vertexCount;
        }

        public boolean requiresResetting() {
            return resetIndex > -1;
        }

        public int getResetIndex() {
            return resetIndex;
        }

        public IntBuffer getIndicesBuffer () {
            IntBuffer indicesBuffer = BufferUtils.createIntBuffer(vertexOrder.length);
            indicesBuffer.put(vertexOrder);
            indicesBuffer.flip();

            return indicesBuffer;
        }
    }

    public static class TriangleGeometry extends Geometry {
        private static final TexturedVertex top = new TexturedVertex().setRGB(1f, 1f, 1f).setST(0, 1).setXYZ(0, 1, 1);
        private static final TexturedVertex left = new TexturedVertex().setRGB(1f, 1f, 1f).setST(0, 0).setXYZ(0, 0, 1);
        private static final TexturedVertex right = new TexturedVertex().setRGB(1f, 1f, 1f).setST(1, 0).setXYZ(1, 0, 1);

        public TriangleGeometry () {
            super(GeometryType.TRIANGLE, new TexturedVertex[]{top, right, left}, VertexInformation.DEFAULT);
        }
    }

    public static class QuadGeometry extends Geometry {
        private static final TexturedVertex topLeft = new TexturedVertex().setRGB(1f, 1f, 1f).setST(0, 1).setXYZ(-0.5f, 0.5f, -1);
        private static final TexturedVertex topRight = new TexturedVertex().setRGB(1f, 1f, 1f).setST(1, 1).setXYZ(0.5f, 0.5f, -1);
        private static final TexturedVertex bottomRight = new TexturedVertex().setRGB(1f, 1f, 1f).setST(1, 0).setXYZ(0.5f, -0.5f, -1);
        private static final TexturedVertex bottomLeft = new TexturedVertex().setRGB(1f, 1f, 1f).setST(0, 0).setXYZ(-0.5f, -0.5f, -1);

        public QuadGeometry () {
            super(GeometryType.QUAD, new TexturedVertex[]{topLeft, bottomLeft, topRight, bottomRight}, VertexInformation.DEFAULT);
        }

        private QuadGeometry (TexturedVertex[] texturedVertices) {
            super(GeometryType.QUAD, texturedVertices, VertexInformation.DEFAULT);
        }

        public static Geometry constructFromPlaneForTexture (GuiPlaneI geometryPlane, GuiPlaneF texturePlane) {
            final TexturedVertex topLeft = new TexturedVertex().setRGB(1f, 1f, 1f).setST(texturePlane.getTopLeftCoordinate().x, texturePlane.getTopLeftCoordinate().y).setXYZ(geometryPlane.getTopLeftCoordinate().x, geometryPlane.getTopLeftCoordinate().y, 0);
            final TexturedVertex topRight = new TexturedVertex().setRGB(1f, 1f, 1f).setST(texturePlane.getTopRightCoordinate().x, texturePlane.getTopRightCoordinate().y).setXYZ(geometryPlane.getTopRightCoordinate().x, geometryPlane.getTopRightCoordinate().y, 0);
            final TexturedVertex bottomRight = new TexturedVertex().setRGB(1f, 1f, 1f).setST(texturePlane.getLowerRightCoordinate().x, texturePlane.getLowerRightCoordinate().y).setXYZ(geometryPlane.getLowerRightCoordinate().x, geometryPlane.getLowerRightCoordinate().y, 0);
            final TexturedVertex bottomLeft = new TexturedVertex().setRGB(1f, 1f, 1f).setST(texturePlane.getLowerLeftCoordinate().x, texturePlane.getLowerLeftCoordinate().y).setXYZ(geometryPlane.getLowerLeftCoordinate().x, geometryPlane.getLowerLeftCoordinate().y, 0);

            return new QuadGeometry(new TexturedVertex[]{topLeft, bottomLeft, topRight, bottomRight});
        }

        public static Geometry constructFromPlaneForTextureOnZ (GuiPlaneI geometryPlane, GuiPlaneF texturePlane) {
            final TexturedVertex topLeft = new TexturedVertex().setRGB(1f, 1f, 1f).setST(Math.abs(texturePlane.getTopLeftCoordinate().x), Math.abs(texturePlane.getTopLeftCoordinate().y)).setXYZ(geometryPlane.getTopLeftCoordinate().x, 0, geometryPlane.getTopLeftCoordinate().y);
            final TexturedVertex topRight = new TexturedVertex().setRGB(1f, 1f, 1f).setST(Math.abs(texturePlane.getTopRightCoordinate().x), Math.abs(texturePlane.getTopRightCoordinate().y)).setXYZ(geometryPlane.getTopRightCoordinate().x, 0, geometryPlane.getTopRightCoordinate().y);
            final TexturedVertex bottomRight = new TexturedVertex().setRGB(1f, 1f, 1f).setST(Math.abs(texturePlane.getLowerRightCoordinate().x), Math.abs(texturePlane.getLowerRightCoordinate().y)).setXYZ(geometryPlane.getLowerRightCoordinate().x, 0, geometryPlane.getLowerRightCoordinate().y);
            final TexturedVertex bottomLeft = new TexturedVertex().setRGB(1f, 1f, 1f).setST(Math.abs(texturePlane.getLowerLeftCoordinate().x), Math.abs(texturePlane.getLowerLeftCoordinate().y)).setXYZ(geometryPlane.getLowerLeftCoordinate().x, 0, geometryPlane.getLowerLeftCoordinate().y);

            return new QuadGeometry(new TexturedVertex[]{topLeft, bottomLeft, topRight, bottomRight});
        }

        public static Geometry constructFromPlaneForColored (GuiPlaneI geometryPlane, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
            TexturedVertex topLeftVertex = new TexturedVertex().setRGBA(topLeftColor.getRed() / 255f, topLeftColor.getGreen() / 255f, topLeftColor.getBlue() / 255f, topLeftColor.getAlpha() / 255f).setST(0, 1).setXYZ(geometryPlane.getTopLeftCoordinate().x, geometryPlane.getTopLeftCoordinate().y, 0);
            TexturedVertex topRightVertex = new TexturedVertex().setRGBA(topRightColor.getRed() / 255f, topRightColor.getGreen() / 255f, topRightColor.getBlue() / 255f, topRightColor.getAlpha() / 255f).setST(1, 1).setXYZ(geometryPlane.getTopRightCoordinate().x, geometryPlane.getTopRightCoordinate().y, 0);
            TexturedVertex bottomRightVertex = new TexturedVertex().setRGBA(bottomRightColor.getRed() / 255f, bottomRightColor.getGreen() / 255f, bottomRightColor.getBlue() / 255f, bottomRightColor.getAlpha() / 255f).setST(1, 0).setXYZ(geometryPlane.getLowerRightCoordinate().x, geometryPlane.getLowerRightCoordinate().y, 0);
            TexturedVertex bottomLeftVertex = new TexturedVertex().setRGBA(bottomLeftColor.getRed() / 255f, bottomLeftColor.getGreen() / 255f, bottomLeftColor.getBlue() / 255f, bottomLeftColor.getAlpha() / 255f).setST(0, 0).setXYZ(geometryPlane.getLowerLeftCoordinate().x, geometryPlane.getLowerLeftCoordinate().y, 0);

            return new QuadGeometry(new TexturedVertex[]{topLeftVertex, bottomLeftVertex, topRightVertex, bottomRightVertex});
        }
    }
}
