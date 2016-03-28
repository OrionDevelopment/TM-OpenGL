package com.smithsgaming.transportmanager.main.world.chunk;

import com.smithsgaming.transportmanager.main.world.*;
import com.smithsgaming.transportmanager.main.world.tileentities.*;
import com.smithsgaming.transportmanager.main.world.tiles.*;

import java.io.*;

/**
 * Created by marcf on 3/13/2016.
 */
public class Chunk implements Serializable {

    public static int chunkSize = 20;

    protected transient World world;

    protected int chunkX;
    protected int chunkZ;

    protected Tile[][][] tiles;
    protected TileEntity[][][] tileEntities;

    public Chunk(World world, int chunkX, int chunkZ) {
        this.world = world;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;

        if (world != null) {
            tiles = new Tile[chunkSize][world.getCoreData().getWorldHeight()][chunkSize];
            tileEntities = new TileEntity[chunkSize][world.getCoreData().getWorldHeight()][chunkSize];
        }
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public void update(){
        synchronized (tileEntities) {
            for (int x = 0; x < tileEntities.length; x++) {
                for (int y = 0; y < tileEntities[1].length; x++) {
                    for (int z = 0; z < tileEntities[2].length; z++) {
                        tileEntities[x][y][z].update();
                    }
                }
            }
        }
    }

    public Tile getTileAtPos(int tileChunkPosX, int tileChunkPosY, int tileChunkPosZ) {
        return tiles[tileChunkPosX][tileChunkPosY][tileChunkPosZ];
    }

    public void setTileAtPos(Tile tile, int tileChunkPosX, int tileChunkPosY, int tileChunkPosZ) {
        tiles[tileChunkPosX][tileChunkPosY][tileChunkPosZ] = tile;
        if (tile instanceof ITileEntityProvider) {
            setTileEntityAtPos(((ITileEntityProvider) tile).createTileEntity(world, tileChunkPosX, tileChunkPosY, tileChunkPosZ), tileChunkPosX, tileChunkPosY, tileChunkPosZ);
        }
    }

    public TileEntity getTileEntityAtPos(int tileChunkPosX, int tileChunkPosY, int tileChunkPosZ) {
        return tileEntities[tileChunkPosX][tileChunkPosY][tileChunkPosZ];
    }

    public void setTileEntityAtPos(TileEntity tileEntity, int tileChunkPosX, int tileChunkPosY, int tileChunkPosZ) {
        tileEntities[tileChunkPosX][tileChunkPosY][tileChunkPosZ] = tileEntity;
    }

    public Tile[][][] getTiles() {
        return tiles;
    }

    public TileEntity[][][] getTileEntities() {
        return tileEntities;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
