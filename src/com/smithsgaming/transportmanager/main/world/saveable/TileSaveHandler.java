package com.smithsgaming.transportmanager.main.world.saveable;

import com.google.common.base.*;
import com.smithsgaming.transportmanager.client.world.*;
import com.smithsgaming.transportmanager.client.world.chunk.*;
import com.smithsgaming.transportmanager.main.world.*;
import com.smithsgaming.transportmanager.main.world.chunk.*;
import com.smithsgaming.transportmanager.main.world.tileentities.*;
import com.smithsgaming.transportmanager.main.world.tiles.*;
import org.jnbt.*;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by marcf on 3/13/2016.
 */
public class TileSaveHandler {

    public static TileSaveHandler instance = new TileSaveHandler();

    public CompoundTag getTagForTile(Chunk chunk, int tileChunkPosX, int tileChunkPosY, int tileChunkPosZ) {
        Map<String, Tag> dataMap = new HashMap<>();

        dataMap.put(Tags.TILECHUNKPOSX, new IntTag(Tags.TILECHUNKPOSX, tileChunkPosX));
        dataMap.put(Tags.TILECHUNKPOSY, new IntTag(Tags.TILECHUNKPOSY, tileChunkPosY));
        dataMap.put(Tags.TILECHUNKPOSZ, new IntTag(Tags.TILECHUNKPOSZ, tileChunkPosZ));

        Tile tile = chunk.getTileAtPos(tileChunkPosX, tileChunkPosY, tileChunkPosZ);
        if (tile == null) {
            dataMap.put(Tags.TILEIDENTITY, new StringTag(Tags.TILEIDENTITY, TileRegistry.NULLTILEIDENTITY));
        } else {
            dataMap.put(Tags.TILEIDENTITY, new StringTag(Tags.TILEIDENTITY, tile.getIdentity()));

            if (tile instanceof ITileEntityProvider) {
                ITileEntityProvider iTileEntityProvider = (ITileEntityProvider) tile;

                //dataMap.put(Tags.TILEENTITYDATA, getTagForTileEntity(chunk, tileChunkPosX, tileChunkPosY, tileChunkPosZ));
            }
        }

        return new CompoundTag(Tags.TILE + "-" + tileChunkPosX + "-" + tileChunkPosY + "-" + tileChunkPosZ, dataMap);
    }

//    public CompoundTag getTagForTileEntity(Chunk chunk, int tileChunkPosX, int tileChunkPosY, int tileChunkPosZ) {
//        Map<String, Tag> dataMap = new HashMap<>();
//
//        TileEntity tileEntity = chunk.getTileEntityAtPos(tileChunkPosX, tileChunkPosY, tileChunkPosZ);
//        if (tileEntity == null) {
//            throw new IllegalArgumentException("The given chunk pos does not contain a TE!");
//        } else {
//            dataMap.put(Tags.TILEENTITYIDENTITY, new StringTag(Tags.TILEENTITYIDENTITY, tileEntity.getIdentity()));
//
//            try {
//                dataMap.putAll(tileEntity.writeDataToDisk());
//                dataMap.put(Tags.TILEENTITYDATAERRORED, new IntTag(Tags.TILEENTITYDATAERRORED, 0));
//            } catch (Exception ex) {
//                System.err.println("Exception while attempting to write TE on Pos: " + tileChunkPosX + "-" + tileChunkPosY + "-" + tileChunkPosZ + " for Chunk: " + chunk.getChunkX() + "-" + chunk.getChunkZ() + " has been caught: ");
//                System.err.println();
//
//                ex.printStackTrace();
//
//                System.err.println();
//                System.err.println("On load of this world the TE will be replaced with a new one!");
//
//                dataMap.put(Tags.TILEENTITYDATAERRORED, new IntTag(Tags.TILEENTITYDATAERRORED, 1));
//            }
//        }
//
//        return new CompoundTag(Tags.TILEENTITYDATA, dataMap);
//    }

    public CompoundTag getTagForChunk(World world, int chunkPosX, int chunkPosZ) {
        System.out.println("Started writing chunk to Tag");
        Stopwatch stopwatch = Stopwatch.createStarted();


        Chunk chunk = world.getChunkAtPos(chunkPosX, chunkPosZ);

        Map<String, Tag> dataMap = new HashMap<>();

        for (int x = 0; x < Chunk.chunkSize; x++) {
            for (int y = 0; y < world.getCoreData().getWorldHeight(); y++) {
                for (int z = 0; z < Chunk.chunkSize; z++) {
                    Tag tag = getTagForTile(chunk, x, y, z);

                    dataMap.put(tag.getName(), tag);
                }
            }
        }


        System.out.println("   ==> Finished converting in: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms.");
        return new CompoundTag(Tags.CHUNK + "-" + chunkPosX + "-" + chunkPosZ, dataMap);
    }

    public CompoundTag getTagForWorld(World world) {
        Map<String, Tag> dataMap = new HashMap<>();

        for (int x = 0; x < world.getCoreData().getWorldWidth() / Chunk.chunkSize + 1; x++) {
            for (int z = 0; z < world.getCoreData().getWorldLength() / Chunk.chunkSize + 1; z++) {
                Tag tag = getTagForChunk(world, x, z);

                dataMap.put(tag.getName(), tag);
            }
        }

        return new CompoundTag(Tags.WORLD, dataMap);
    }

    public void loadTileForTagIntoChunk (Chunk chunk, Tag tag) {
        if (!( tag instanceof CompoundTag ))
            throw new IllegalArgumentException("tag");

        Map<String, Tag> dataMap = ( (CompoundTag) tag ).getValue();

        int tileChunkPosX = (int) dataMap.get(Tags.TILECHUNKPOSX).getValue();
        int tileChunkPosY = (int) dataMap.get(Tags.TILECHUNKPOSY).getValue();
        int tileChunkPosZ = (int) dataMap.get(Tags.TILECHUNKPOSZ).getValue();

        Tile tile = TileRegistry.instance.getTileForIdentity((String) dataMap.get(Tags.TILEIDENTITY).getValue());
        if (tile == null) {
            chunk.setTileAtPos(null, tileChunkPosX, tileChunkPosY, tileChunkPosZ);
        } else {
            if (tile instanceof ITileEntityProvider) {
                //loadTileEntityForTagIntoChunk(chunk, dataMap.get(Tags.TILEENTITYDATA), tileChunkPosX, tileChunkPosY, tileChunkPosZ);
            }
        }
    }

//    public void loadTileEntityForTagIntoChunk (Chunk chunk, Tag teTag, int tileChunkPosX, int tileChunkPosY, int tileChunkPosZ) {
//        if (!( teTag instanceof CompoundTag ))
//            throw new IllegalArgumentException("teTag");
//
//        Map<String, Tag> dataMap = ( (CompoundTag) teTag ).getValue();
//
//        TileEntity tileEntity = TileEntityRegistry.instance.getTileEntityForIdentity((String) dataMap.get(Tags.TILEENTITYIDENTITY).getValue());
//        if (tileEntity == null) {
//            chunk.setTileEntityAtPos(null, tileChunkPosX, tileChunkPosY, tileChunkPosZ);
//        } else {
//            if (( (int) dataMap.get(Tags.TILEENTITYDATAERRORED).getValue() ) == 1) {
//                System.err.println("Loading errored TE on pos: " + +tileChunkPosX + "-" + tileChunkPosY + "-" + tileChunkPosZ + " for Chunk: " + chunk.getChunkX() + "-" + chunk.getChunkZ());
//                chunk.setTileEntityAtPos(null, tileChunkPosX, tileChunkPosY, tileChunkPosZ);
//
//                return;
//            }
//
//            tileEntity.readDataFromDisk((Map<String, Tag>) dataMap.get(Tags.TILEENTITYDATA).getValue());
//
//            chunk.setTileEntityAtPos(tileEntity, tileChunkPosX, tileChunkPosY, tileChunkPosZ);
//        }
//    }

    public void loadChunkForTagIntoWorld (World world, Tag chunkTag, int chunkPosX, int chunkPosZ) {
        if (!( chunkTag instanceof CompoundTag ))
            throw new IllegalArgumentException("teTag");

        Chunk chunk = world.getChunkAtPos(chunkPosX, chunkPosZ);

        Map<String, Tag> dataMap = ( (CompoundTag) chunkTag ).getValue();

        for (int x = 0; x < Chunk.chunkSize; x++) {
            for (int y = 0; y < world.getCoreData().getWorldHeight(); y++) {
                for (int z = 0; z < Chunk.chunkSize; z++) {
                    Tag tag = dataMap.get(Tags.TILE + "-" + x + "-" + y + "-" + z);

                    loadTileForTagIntoChunk(chunk, tag);
                }
            }
        }
    }

    public void loadWorldFromTag (World world, Tag worldTag) {
        if (!( worldTag instanceof CompoundTag ))
            throw new IllegalArgumentException("teTag");

        Map<String, Tag> dataMap = ( (CompoundTag) worldTag ).getValue();

        for (int x = 0; x < world.getCoreData().getWorldWidth() / Chunk.chunkSize + 1; x++) {
            for (int z = 0; z < world.getCoreData().getWorldLength() / Chunk.chunkSize + 1; z++) {
                Tag chunkTag = dataMap.get(Tags.CHUNK + "-" + x + "-" + z);

                loadChunkForTagIntoWorld(world, chunkTag, x, z);
            }
        }
    }

    public void setChunkInWorld (World world, Chunk chunk) {
        chunk.setWorld(world);
        world.setChunk(chunk);
    }

    public void setChunkInWorldClient (WorldClient world, ChunkClient chunk) {
        chunk.setWorld(world);
        world.setChunk(chunk);
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
