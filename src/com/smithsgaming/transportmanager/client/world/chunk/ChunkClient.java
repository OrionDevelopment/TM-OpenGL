package com.smithsgaming.transportmanager.client.world.chunk;

import com.smithsgaming.transportmanager.client.graphics.*;
import com.smithsgaming.transportmanager.client.world.*;
import com.smithsgaming.transportmanager.main.world.chunk.*;
import com.smithsgaming.transportmanager.main.world.tileentities.*;
import com.smithsgaming.transportmanager.main.world.tiles.*;
import org.lwjgl.util.vector.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class ChunkClient {
    private WorldClient world;

    private int chunkX;
    private int chunkZ;

    private Tile[][][] tiles;
    private TileEntity[][][] tileEntities;

    public ChunkClient (WorldClient world, int chunkX, int chunkZ) {
        this.world = world;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;

        tiles = new Tile[Chunk.chunkSize][world.getCoreData().getWorldHeight()][Chunk.chunkSize];
        tileEntities = new TileEntity[Chunk.chunkSize][world.getCoreData().getWorldHeight()][Chunk.chunkSize];
    }

    public ChunkClient (Chunk serverChunk) {
        this.world = null;
        this.chunkX = serverChunk.getChunkX();
        this.chunkZ = serverChunk.getChunkZ();

        tiles = serverChunk.getTiles();
        tileEntities = serverChunk.getTileEntities();

    }

    public int getChunkX () {
        return chunkX;
    }

    public int getChunkZ () {
        return chunkZ;
    }

    public Tile getTileOnPos (int tileChunkPosX, int tileChunkPosY, int tileChunkPosZ) {
        return tiles[tileChunkPosX][tileChunkPosY][tileChunkPosZ];
    }

    public void setTileOnPos (Tile tile, int tileChunkPosX, int tileChunkPosY, int tileChunkPosZ) {
        tiles[tileChunkPosX][tileChunkPosY][tileChunkPosZ] = tile;
    }

    public TileEntity getTileEntityOnPos (int tileChunkPosX, int tileChunkPosY, int tileChunkPosZ) {
        return tileEntities[tileChunkPosX][tileChunkPosY][tileChunkPosZ];
    }

    public void setTileEntityOnPos (TileEntity tileEntity, int tileChunkPosX, int tileChunkPosY, int tileChunkPosZ) {
        tileEntities[tileChunkPosX][tileChunkPosY][tileChunkPosZ] = tileEntity;
    }

    public WorldClient getWorld () {
        return world;
    }

    public void setWorld (WorldClient world) {
        this.world = world;
    }

    public AABox getBoundingBox () {
        return new AABox(new Vector3f(chunkX * Chunk.chunkSize, 0, chunkZ * Chunk.chunkSize), Chunk.chunkSize, world.getCoreData().getWorldHeight(), Chunk.chunkSize);
    }

    public Vector3f getChunkCenterForCamera (Camera camera) {
        return new Vector3f(chunkX * Chunk.chunkSize + Chunk.chunkSize / 2, camera.getCameraPosition().getY(), chunkZ * Chunk.chunkSize + Chunk.chunkSize / 2);
    }
}
