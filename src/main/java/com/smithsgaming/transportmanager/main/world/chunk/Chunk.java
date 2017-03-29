package com.smithsgaming.transportmanager.main.world.chunk;

import com.smithsgaming.transportmanager.main.tileentity.ITileEntityProvider;
import com.smithsgaming.transportmanager.main.tileentity.TileEntity;
import com.smithsgaming.transportmanager.main.world.World;
import com.smithsgaming.transportmanager.main.world.tiles.Tile;

import java.io.Serializable;

/**
 * Created by marcf on 3/13/2016.
 */
public class Chunk implements Serializable {

    public static final int chunkSize = 20;

    protected transient World world;

    protected int chunkX;
    protected int chunkZ;

    protected Tile[][] tiles;
    protected TileEntity[][] tileEntities;

    public Chunk(World world, int chunkX, int chunkZ) {
        this.world = world;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;

        if (world != null) {
            tiles = new Tile[chunkSize][chunkSize];
            tileEntities = new TileEntity[chunkSize][chunkSize];
        }
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public void update () {
        synchronized (tileEntities) {
            for (int x = 0; x < tileEntities[0].length; x++) {
                for (int z = 0; z < tileEntities[2].length; z++) {
                    tileEntities[x][z].update();
                }
            }
        }
    }

    public Tile getTileAtPos (int tileChunkPosX, int tileChunkPosZ) {
        return tiles[tileChunkPosX][tileChunkPosZ];
    }

    public void setTileAtPos (Tile tile, int tileChunkPosX, int tileChunkPosZ) {
        tiles[tileChunkPosX][tileChunkPosZ] = tile;
        if (tile instanceof ITileEntityProvider) {
            setTileEntityAtPos(( (ITileEntityProvider) tile ).createTileEntity(world, tileChunkPosX, tileChunkPosZ), tileChunkPosX, tileChunkPosZ);
        }
    }

    public void setTileEntityAtPos (TileEntity tileEntity, int tileChunkPosX, int tileChunkPosZ) {
        tileEntities[tileChunkPosX][tileChunkPosZ] = tileEntity;
    }

    public TileEntity getTileEntityAtPos(int tileChunkPosX, int tileChunkPosZ)
    {
        return tileEntities[tileChunkPosX][tileChunkPosZ];
    }

    public Tile[][] getTiles () {
        return tiles;
    }

    public TileEntity[][] getTileEntities () {
        return tileEntities;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
