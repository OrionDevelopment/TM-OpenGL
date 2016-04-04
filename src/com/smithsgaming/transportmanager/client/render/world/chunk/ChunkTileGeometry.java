package com.smithsgaming.transportmanager.client.render.world.chunk;

import com.smithsgaming.transportmanager.client.registries.GeometryRegistry;
import com.smithsgaming.transportmanager.client.world.chunk.ChunkClient;
import com.smithsgaming.transportmanager.main.world.chunk.Chunk;
import com.smithsgaming.transportmanager.main.world.tiles.Tile;
import com.smithsgaming.transportmanager.util.TexturedVertex;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

/**
 * Created by marcf on 4/4/2016.
 */
public class ChunkTileGeometry extends GeometryRegistry.Geometry {

    private ChunkClient chunk;
    private Tile tile;
    private boolean[][] positions = null;
    private byte[] indexOrder;

    public ChunkTileGeometry(ChunkClient chunk, Tile tile) {
        super(GeometryRegistry.GeometryType.QUAD, new TexturedVertex[0]);

        this.chunk = chunk;
        this.tile = tile;
    }

    private boolean[][] collectTilePosOrdered() {
        if (positions != null)
            return positions;

        for (int x = 0; x < Chunk.chunkSize; x++) {
            for (int z = 0; z < Chunk.chunkSize; z++) {
                positions[x][z] = chunk.getTileAtPos(x, z).equals(tile);
            }
        }

        return positions;
    }

    private TexturedVertex[][] collectVerticesOrdered() {

    }

    @Override
    public int getVertexCount() {
        return super.getVertexCount();
    }

    @Override
    public boolean requiresResetting() {
        return true;
    }

    @Override
    public int getResetIndex() {
        return 0;
    }

    @Override
    public ByteBuffer getIndicesBuffer() {
        ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indexOrder.length);
        indicesBuffer.put(indexOrder);
        indicesBuffer.flip();

        return indicesBuffer;
    }
}
