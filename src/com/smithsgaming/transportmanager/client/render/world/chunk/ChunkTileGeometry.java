package com.smithsgaming.transportmanager.client.render.world.chunk;

import com.smithsgaming.transportmanager.client.graphics.*;
import com.smithsgaming.transportmanager.client.registries.*;
import com.smithsgaming.transportmanager.client.render.*;
import com.smithsgaming.transportmanager.client.render.textures.*;
import com.smithsgaming.transportmanager.client.world.chunk.*;
import com.smithsgaming.transportmanager.main.world.chunk.*;
import com.smithsgaming.transportmanager.main.world.tiles.*;
import com.smithsgaming.transportmanager.util.*;
import com.smithsgaming.transportmanager.util.math.*;
import com.smithsgaming.transportmanager.util.math.graphical.*;
import org.lwjgl.*;

import java.nio.*;
import java.util.*;

/**
 * @Author Marc (Created on: 04.04.2016)
 */
public class ChunkTileGeometry extends GeometryRegistry.Geometry implements IRenderer {

    public static int VertexCount = 0;
    private ChunkClient chunkClient;
    private Tile tile;
    private Texture texture;
    private int[] verticesIndecis;
    private int resetIndex;

    public ChunkTileGeometry (ChunkClient chunkClient, Tile tile) {
        super(GeometryRegistry.GeometryType.QUAD, new TexturedVertex[0]);

        this.chunkClient = chunkClient;
        this.tile = tile;

        this.texture = TextureRegistry.instance.getTextureForName(tile.getIdentity());

        if (this.texture == null)
            System.out.println("Empty texture for: " + tile.getIdentity());

        buildGeometry();
    }

    public void buildGeometry () {
        ArrayList<TexturedVertex> texturedVertices = new ArrayList<>();

        for (int x = 0; x < Chunk.chunkSize; x++) {
            for (int z = 0; z < Chunk.chunkSize; z++) {
                if (chunkClient.getTileAtPos(x, z) != null && chunkClient.getTileAtPos(x, z).equals(tile)) {
                    //TODO: Update the position of the Quad.
                    Vector2i topLeftQuadCorner = new Vector2i(chunkClient.getChunkX() * Chunk.chunkSize + x - ( chunkClient.getWorld().getCoreData().getWorldWidth() / 2 ), ( chunkClient.getWorld().getCoreData().getWorldHeight() / 2 ) - ( chunkClient.getChunkZ() * Chunk.chunkSize + z ));
                    Vector2i lowerRightQuadCorner = new Vector2i(topLeftQuadCorner.x + 1, topLeftQuadCorner.y - 1);

                    GeometryRegistry.Geometry positionGeometry = GeometryRegistry.QuadGeometry.constructFromPlaneForTextureOnZ(new GuiPlaneI(topLeftQuadCorner, lowerRightQuadCorner), texture.getArea());

                    Collections.addAll(texturedVertices, positionGeometry.getVertices());
                }
            }
        }

        VertexCount += texturedVertices.size();

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
        OpenGLUtil.drawGeometryWithShaderAndTexture(Camera.Player, this, texture, ShaderRegistry.Shaders.textured);
    }
}
