/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.client.world.chunk;

import com.smithsgaming.transportmanager.client.graphics.AABox;
import com.smithsgaming.transportmanager.client.graphics.Camera;
import com.smithsgaming.transportmanager.client.world.WorldClient;
import com.smithsgaming.transportmanager.main.world.chunk.Chunk;
import com.smithsgaming.transportmanager.main.world.tiles.Tile;
import org.joml.Vector3f;

/**
 *  ------ Class not Documented ------
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
        return new AABox(new Vector3f(getWorld().getCoreData().getWorldWidth() / -2f + chunkX * Chunk.chunkSize,
                                       0,
                                       getWorld().getCoreData().getWorldHeight() / -2f + chunkZ * Chunk.chunkSize), Chunk.chunkSize, 1, Chunk.chunkSize);
    }

    public Vector3f getChunkCenterForCamera(Camera camera) {
        return new Vector3f(getWorld().getCoreData().getWorldWidth() / -2f + chunkX * Chunk.chunkSize + Chunk.chunkSize / 2, camera.getCameraPosition().y(), getWorld().getCoreData().getWorldHeight() / 2f - chunkZ * Chunk.chunkSize - Chunk.chunkSize / 2);
    }

    private void onTileUpdated(Tile oldTile, Tile newTile){

    }
}
