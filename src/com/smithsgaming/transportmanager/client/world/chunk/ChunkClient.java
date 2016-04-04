package com.smithsgaming.transportmanager.client.world.chunk;

import com.smithsgaming.transportmanager.client.graphics.*;
import com.smithsgaming.transportmanager.client.world.*;
import com.smithsgaming.transportmanager.main.world.chunk.*;
import com.smithsgaming.transportmanager.main.world.tiles.*;
import org.lwjgl.util.vector.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class ChunkClient extends Chunk {

    public ChunkClient(WorldClient world, int chunkX, int chunkZ) {
        super(world, chunkX, chunkZ);
    }

    public ChunkClient(Chunk serverChunk) {
        super(null, serverChunk.getChunkX(), serverChunk.getChunkZ());
        this.tiles = serverChunk.getTiles();
        this.tileEntities = serverChunk.getTileEntities();
    }

    public AABox getBoundingBox() {
        return new AABox(new Vector3f(chunkX * Chunk.chunkSize, 0, chunkZ * Chunk.chunkSize), Chunk.chunkSize, world.getCoreData().getWorldHeight(), Chunk.chunkSize);
    }

    public Vector3f getChunkCenterForCamera(Camera camera) {
        return new Vector3f(chunkX * Chunk.chunkSize + Chunk.chunkSize / 2, camera.getCameraPosition().getY(), chunkZ * Chunk.chunkSize + Chunk.chunkSize / 2);
    }

    private void onTileUpdated(Tile oldTile, Tile newTile){

    }
}
