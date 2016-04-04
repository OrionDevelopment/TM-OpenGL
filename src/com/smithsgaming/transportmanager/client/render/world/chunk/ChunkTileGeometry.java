package com.smithsgaming.transportmanager.client.render.world.chunk;

import com.smithsgaming.transportmanager.client.registries.*;
import com.smithsgaming.transportmanager.client.render.textures.*;
import com.smithsgaming.transportmanager.client.world.chunk.*;
import com.smithsgaming.transportmanager.main.world.tiles.*;
import com.smithsgaming.transportmanager.util.*;
import org.lwjgl.*;

import java.nio.*;

/**
 * @Author Marc (Created on: 04.04.2016)
 */
public class ChunkTileGeometry extends GeometryRegistry.Geometry {

    private ChunkClient chunkClient;
    private Tile tile;
    private Texture texture;

    private boolean[][] tilePositions;
    private int[] verticesIndecis;
    private int resetIndex;

    public ChunkTileGeometry (GeometryRegistry.GeometryType type, TexturedVertex[] vertices) {
        super(GeometryRegistry.GeometryType.QUAD, new TexturedVertex[0]);


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
}
