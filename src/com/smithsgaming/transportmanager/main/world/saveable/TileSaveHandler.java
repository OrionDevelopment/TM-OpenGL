package com.smithsgaming.transportmanager.main.world.saveable;

import com.smithsgaming.transportmanager.main.world.*;
import com.smithsgaming.transportmanager.main.world.chunk.*;
import com.smithsgaming.transportmanager.main.world.tileentities.*;
import com.smithsgaming.transportmanager.main.world.tiles.*;
import org.jnbt.*;

import java.util.*;

/**
 * Created by marcf on 3/13/2016.
 */
public class TileSaveHandler {

    private World world;

    public TileSaveHandler(World world) {
        this.world = world;
    }

    public CompoundTag getTagForTile(Chunk chunk, int tileChunkPosX, int tileChunkPosY, int tileChunkPosZ) {
        Map<String, Tag> dataMap = new HashMap<>();

        dataMap.put(Tags.TILECHUNKPOSX, new IntTag(Tags.TILECHUNKPOSX, tileChunkPosX));
        dataMap.put(Tags.TILECHUNKPOSY, new IntTag(Tags.TILECHUNKPOSY, tileChunkPosY));
        dataMap.put(Tags.TILECHUNKPOSZ, new IntTag(Tags.TILECHUNKPOSZ, tileChunkPosZ));

        Tile tile = chunk.getTileOnPos(tileChunkPosX, tileChunkPosY, tileChunkPosZ);
        if (tile == null) {
            dataMap.put(Tags.TILEIDENTITY, new StringTag(Tags.TILEIDENTITY, TileRegistry.NULLTILEIDENTITY));
        } else {
            dataMap.put(Tags.TILEIDENTITY, new StringTag(Tags.TILEIDENTITY, tile.getIdentity()));

            if (tile instanceof ITileEntityProvider) {
                ITileEntityProvider iTileEntityProvider = (ITileEntityProvider) tile;

                dataMap.put(Tags.TILEENTITYDATA, getTagForTileEntity(chunk, tileChunkPosX, tileChunkPosY, tileChunkPosZ));
            }
        }

        return new CompoundTag(Tags.TILE + "-" + tileChunkPosX + "-" + tileChunkPosY + "-" + tileChunkPosZ, dataMap);
    }

    public CompoundTag getTagForTileEntity(Chunk chunk, int tileChunkPosX, int tileChunkPosY, int tileChunkPosZ) {
        Map<String, Tag> dataMap = new HashMap<>();

        TileEntity tileEntity = chunk.getTileEntityOnPos(tileChunkPosX, tileChunkPosY, tileChunkPosZ);
        if (tileEntity == null) {
            throw new IllegalArgumentException("The given chunk pos does not contain a TE!");
        } else {
            dataMap.put(Tags.TILEENTITYIDENTITY, new StringTag(Tags.TILEENTITYIDENTITY, tileEntity.getIdentity()));

            try {
                dataMap.putAll(tileEntity.writeDataToDisk());
                dataMap.put(Tags.TILEENTITYDATAERRORED, new IntTag(Tags.TILEENTITYDATAERRORED, 0));
            } catch (Exception ex) {
                System.err.println("Exception while attempting to write TE on Pos: " + tileChunkPosX + "-" + tileChunkPosY + "-" + tileChunkPosZ + " for Chunk: " + chunk.getChunkX() + "-" + chunk.getChunkZ() + " has been caught: ");
                System.err.println();

                ex.printStackTrace();

                System.err.println();
                System.err.println("On load of this world the TE will be replaced with a new one!");

                dataMap.put(Tags.TILEENTITYDATAERRORED, new IntTag(Tags.TILEENTITYDATAERRORED, 1));
            }
        }

        return new CompoundTag(Tags.TILEENTITYDATA, dataMap);
    }

    public CompoundTag getTagForChunk(World world, int chunkPosX, int chunkPosZ) {
        Chunk chunk = world.getChunkForPos(chunkPosX, chunkPosZ);

        Map<String, Tag> dataMap = new HashMap<>();

        for (int x = 0; x < Chunk.chunkSize; x++) {
            for (int y = 0; y < World.WORLDHEIGHT; y++) {
                for (int z = 0; z < Chunk.chunkSize; z++) {
                    Tag tag = getTagForTile(chunk, x, y, z);

                    dataMap.put(tag.getName(), tag);
                }
            }
        }

        return new CompoundTag(Tags.CHUNK, dataMap);
    }

    public CompoundTag getTagForWorld(World world) {
        Map<String, Tag> dataMap = new HashMap<>();

        for (int x = 0; x < World.WORLDWIDTH / Chunk.chunkSize + 1; x++) {
            for (int z = 0; z < World.WORLDLENGTH / Chunk.chunkSize + 1; z++) {
                Tag tag = getTagForChunk(world, x, z);

                dataMap.put(tag.getName(), tag);
            }
        }

        return new CompoundTag(Tags.WORLD, dataMap);
    }

    public static class Tags {
        public static final String WORLD = "world";
        public static final String CHUNK = "chunk";
        public static final String TILE = "tile";
        public static final String TILEIDENTITY = "tileIdentity";
        public static final String TILECHUNKPOSX = "tileChunkPosX";
        public static final String TILECHUNKPOSY = "tileChunkPosY";
        public static final String TILECHUNKPOSZ = "tileChunkPosZ";
        public static final String TILEENTITYDATA = "tileEntityData";
        public static final String TILEENTITYIDENTITY = "tileEntityIdentity";
        public static final String TILEENTITYDATAERRORED = "tileEntityErrored";
    }
}
