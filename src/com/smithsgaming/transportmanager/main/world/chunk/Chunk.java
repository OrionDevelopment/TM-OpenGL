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

    private transient World world;

    private int chunkX;
    private int chunkZ;

    private Tile[][][] tiles;
    private TileEntity[][][] tileEntities;

    public Chunk(World world, int chunkX, int chunkZ) {
        this.world = world;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;

        tiles = new Tile[chunkSize][world.getCoreData().getWorldHeight()][chunkSize];
        tileEntities = new TileEntity[chunkSize][world.getCoreData().getWorldHeight()][chunkSize];
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public Tile getTileOnPos(int tileChunkPosX, int tileChunkPosY, int tileChunkPosZ) {
        return tiles[tileChunkPosX][tileChunkPosY][tileChunkPosZ];
    }

    public void setTileOnPos(Tile tile, int tileChunkPosX, int tileChunkPosY, int tileChunkPosZ) {
        tiles[tileChunkPosX][tileChunkPosY][tileChunkPosZ] = tile;
    }

    public TileEntity getTileEntityOnPos(int tileChunkPosX, int tileChunkPosY, int tileChunkPosZ) {
        return tileEntities[tileChunkPosX][tileChunkPosY][tileChunkPosZ];
    }

    public void setTileEntityOnPos(TileEntity tileEntity, int tileChunkPosX, int tileChunkPosY, int tileChunkPosZ) {
        tileEntities[tileChunkPosX][tileChunkPosY][tileChunkPosZ] = tileEntity;
    }

    public Tile[][][] getTiles () {
        return tiles;
    }

    public TileEntity[][][] getTileEntities () {
        return tileEntities;
    }

    public World getWorld () {
        return world;
    }

    public void setWorld (World world) {
        this.world = world;
    }
}
