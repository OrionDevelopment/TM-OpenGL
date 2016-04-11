package com.smithsgaming.transportmanager.client.render.test;

import com.smithsgaming.transportmanager.client.graphics.*;
import com.smithsgaming.transportmanager.client.registries.*;
import com.smithsgaming.transportmanager.client.render.*;
import com.smithsgaming.transportmanager.util.*;
import com.smithsgaming.transportmanager.util.math.*;
import com.smithsgaming.transportmanager.util.math.graphical.*;
import org.lwjgl.*;

import java.nio.*;
import java.util.*;

/**
 * @Author Marc (Created on: 05.04.2016)
 */
public class ResetIndexedRenderer implements IRenderer {

    private static Geometry testGeometry;

    @Override
    public void render () {
        if (testGeometry == null) {
            testGeometry = new Geometry();
            OpenGLUtil.loadGeometryIntoGPU(testGeometry);
        }

        testGeometry.render();
    }

    public static class Geometry extends GeometryRegistry.Geometry implements IRenderer {
        private int[] verticesIndecis;
        private int resetIndex;

        public Geometry () {
            super(GeometryRegistry.GeometryType.QUAD, new TexturedVertex[0]);

            buildGeometry();
        }

        public void buildGeometry () {
            ArrayList<TexturedVertex> texturedVertices = new ArrayList<>();

            for (int x = 0; x < 1000; x++) {
                Vector2i topLeftQuadCorner = new Vector2i(0 + x, 0 + x);
                Vector2i lowerRightQuadCorner = new Vector2i(1 + x, -1 + x);

                GeometryRegistry.Geometry positionGeometry = GeometryRegistry.QuadGeometry.constructFromPlaneForTextureOnZ(new GuiPlaneI(topLeftQuadCorner, lowerRightQuadCorner), TextureRegistry.Textures.Tiles.grass.getArea());

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
        public IntBuffer getIndicesBuffer () {
            IntBuffer indicesBuffer = BufferUtils.createIntBuffer(verticesIndecis.length);
            indicesBuffer.put(verticesIndecis);
            indicesBuffer.flip();

            return indicesBuffer;
        }

        @Override
        public int getResetIndex () {
            return resetIndex;
        }

        @Override
        public boolean requiresResetting () {
            return true;
        }

        @Override
        public int getVertexCount () {
            return verticesIndecis.length;
        }

        @Override
        public void render () {
            OpenGLUtil.drawGeometryWithShaderAndTexture(Camera.Player, this, TextureRegistry.Textures.Tiles.grass, ShaderRegistry.Shaders.textured);
        }

    }
}
