package com.smithsgaming.transportmanager.client.render.test;

import com.smithsgaming.transportmanager.client.graphics.Camera;
import com.smithsgaming.transportmanager.client.registries.GeometryRegistry;
import com.smithsgaming.transportmanager.client.registries.ShaderRegistry;
import com.smithsgaming.transportmanager.client.registries.TextureRegistry;
import com.smithsgaming.transportmanager.client.render.IRenderer;
import com.smithsgaming.transportmanager.client.render.core.TexturedVertex;
import com.smithsgaming.transportmanager.client.render.core.VertexInformation;
import com.smithsgaming.transportmanager.client.render.core.geometry.Geometry;
import com.smithsgaming.transportmanager.util.OpenGLUtil;
import com.smithsgaming.transportmanager.util.math.graphical.GuiPlaneF;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @Author Marc (Created on: 05.04.2016)
 */
public class ResetIndexedRenderer implements IRenderer {

    private static TestGeometry testGeometry;

    @Override
    public void render () {
        if (testGeometry == null) {
            testGeometry = new TestGeometry();
            OpenGLUtil.loadGeometryIntoGPU(testGeometry);
        }

        testGeometry.render();
    }

    public static class TestGeometry extends Geometry implements IRenderer
    {
        private int[] verticesIndecis;
        private int resetIndex;

        public TestGeometry()
        {
            super(GeometryRegistry.GeometryType.QUAD, new TexturedVertex[0], VertexInformation.DEFAULT);

            buildGeometry();
        }

        public void buildGeometry () {
            ArrayList<TexturedVertex> texturedVertices = new ArrayList<>();

            for (int x = 0; x < 1; x++)
            {
                Vector2f topLeftQuadCorner = new Vector2f(-0.5f + x, -0.5f + x);
                Vector2f lowerRightQuadCorner = new Vector2f(0.5f + x, 0.5f + x);

                com.smithsgaming.transportmanager.client.render.core.geometry.Geometry positionGeometry =
                  GeometryRegistry.QuadGeometry.constructFromPlaneForTextureOnZ(new GuiPlaneF(topLeftQuadCorner, lowerRightQuadCorner),
                    TextureRegistry.Textures.Tiles.voidTexture.getArea());

                Collections.addAll(texturedVertices, positionGeometry.getVertices());
            }

            resetIndex = texturedVertices.size();
            verticesIndecis = new int[texturedVertices.size() + texturedVertices.size() / 4];
            for (int i = 0; i < verticesIndecis.length; i++) {
                if (i % 5 == 4)
                    verticesIndecis[i] = resetIndex;
                else
                    verticesIndecis[i] = ( i - ( i / 5 ) );
            }

            this.vertices = texturedVertices.toArray(new TexturedVertex[0]);
        }

        @Override
        public int getVertexCount()
        {
            return verticesIndecis.length;
        }

        @Override
        public boolean requiresResetting()
        {
            return true;
        }

        @Override
        public int getResetIndex()
        {
            return resetIndex;
        }

        @Override
        public IntBuffer getIndicesBuffer () {
            IntBuffer indicesBuffer = BufferUtils.createIntBuffer(verticesIndecis.length);
            indicesBuffer.put(verticesIndecis);
            indicesBuffer.flip();

            return indicesBuffer;
        }

        @Override
        public void render () {
            OpenGLUtil.drawGeometryWithShaderAndTexture(Camera.Player, this, TextureRegistry.Textures.Tiles.voidTexture, ShaderRegistry.Shaders.textured);
        }

    }
}
