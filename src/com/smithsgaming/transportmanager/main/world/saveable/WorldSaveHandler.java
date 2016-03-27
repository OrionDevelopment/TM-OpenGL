package com.smithsgaming.transportmanager.main.world.saveable;

import com.google.common.base.*;
import com.smithsgaming.transportmanager.client.world.*;
import com.smithsgaming.transportmanager.client.world.chunk.*;
import com.smithsgaming.transportmanager.main.TransportManager;
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
public class WorldSaveHandler {

    public static WorldSaveHandler instance = new WorldSaveHandler();

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
        TransportManager.serverLogger.debug("   ==> Finished converting in: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms.");
        //System.out.println("   ==> Finished converting in: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms.");
        return new CompoundTag(Tags.CHUNK + "-" + chunkPosX + "-" + chunkPosZ, dataMap);
    }

    public CompoundTag getTagForTile(Chunk chunk, int tileChunkPosX, int tileChunkPosY, int tileChunkPosZ) {
        Map<String, Tag> dataMap = new HashMap<>();
        dataMap.put(Tags.TILE_CHUNK_POS_X, new IntTag(Tags.TILE_CHUNK_POS_X, tileChunkPosX));
        dataMap.put(Tags.TILE_CHUNK_POS_Y, new IntTag(Tags.TILE_CHUNK_POS_Y, tileChunkPosY));
        dataMap.put(Tags.TILE_CHUNK_POS_Z, new IntTag(Tags.TILE_CHUNK_POS_Z, tileChunkPosZ));
        Tile tile = chunk.getTileAtPos(tileChunkPosX, tileChunkPosY, tileChunkPosZ);
        if (tile == null) {
            dataMap.put(Tags.TILE_IDENTITY, new StringTag(Tags.TILE_IDENTITY, TileRegistry.NULLTILEIDENTITY));
        } else {
            dataMap.put(Tags.TILE_IDENTITY, new StringTag(Tags.TILE_IDENTITY, tile.getIdentity()));
            if (tile instanceof ITileEntityProvider) {
                dataMap.put(Tags.TILE_ENTITY_DATA, getTagForTileEntity(chunk, tileChunkPosX, tileChunkPosY, tileChunkPosZ));
            }
        }
        return new CompoundTag(Tags.TILE + "-" + tileChunkPosX + "-" + tileChunkPosY + "-" + tileChunkPosZ, dataMap);
    }

    public CompoundTag getTagForTileEntity(Chunk chunk, int tileChunkPosX, int tileChunkPosY, int tileChunkPosZ) {
        Map<String, Tag> dataMap = new HashMap<>();
        TileEntity tileEntity = chunk.getTileEntityAtPos(tileChunkPosX, tileChunkPosY, tileChunkPosZ);
        if (tileEntity == null) {
            throw new IllegalArgumentException("The given chunk pos does not contain a TE!");
        } else {
            try {
                dataMap.putAll(tileEntity.writeToDisk().getValue());
                dataMap.put(Tags.TILE_ENTITY_ERRORED, new IntTag(Tags.TILE_ENTITY_ERRORED, 0));
            } catch (Exception ex) {
                TransportManager.serverLogger.error("Exception while attempting to write TE on Pos: " + tileChunkPosX + "-" + tileChunkPosY + "-" + tileChunkPosZ + " for Chunk: " + chunk.getChunkX() + "-" + chunk.getChunkZ() + " has been caught: +\n");
                TransportManager.serverLogger.error(ex.getStackTrace() + "\n");
                TransportManager.serverLogger.error("On load of this world the TE will be replaced with a new one!");
//                System.err.println("Exception while attempting to write TE on Pos: " + tileChunkPosX + "-" + tileChunkPosY + "-" + tileChunkPosZ + " for Chunk: " + chunk.getChunkX() + "-" + chunk.getChunkZ() + " has been caught: ");
//                System.err.println();
//                ex.printStackTrace();
//                System.err.println();
//                System.err.println("On load of this world the TE will be replaced with a new one!");
                dataMap.put(Tags.TILE_ENTITY_ERRORED, new IntTag(Tags.TILE_ENTITY_ERRORED, 1));
            }
        }
        return new CompoundTag(Tags.TILE_ENTITY_DATA, dataMap);
    }

    public void loadWorldFromTag(World world, Tag worldTag) {
        if (!(worldTag instanceof CompoundTag)) {
            throw new IllegalArgumentException("Invalid Tag type");
        }
        Map<String, Tag> dataMap = ((CompoundTag) worldTag).getValue();
        for (int x = 0; x < world.getCoreData().getWorldWidth() / Chunk.chunkSize + 1; x++) {
            for (int z = 0; z < world.getCoreData().getWorldLength() / Chunk.chunkSize + 1; z++) {
                Tag chunkTag = dataMap.get(Tags.CHUNK + "-" + x + "-" + z);
                loadChunkForTagIntoWorld(world, chunkTag, x, z);
            }
        }
    }

    public void loadChunkForTagIntoWorld(World world, Tag chunkTag, int chunkPosX, int chunkPosZ) {
        if (!(chunkTag instanceof CompoundTag)) {
            throw new IllegalArgumentException("Invalid Tag type");
        }
        Chunk chunk = world.getChunkAtPos(chunkPosX, chunkPosZ);
        Map<String, Tag> dataMap = ((CompoundTag) chunkTag).getValue();
        for (int x = 0; x < Chunk.chunkSize; x++) {
            for (int y = 0; y < world.getCoreData().getWorldHeight(); y++) {
                for (int z = 0; z < Chunk.chunkSize; z++) {
                    Tag tag = dataMap.get(Tags.TILE + "-" + x + "-" + y + "-" + z);
                    loadTileForTagIntoChunk(chunk, tag);
                }
            }
        }
    }

    public void loadTileForTagIntoChunk(Chunk chunk, Tag tag) {
        if (!(tag instanceof CompoundTag)) {
            throw new IllegalArgumentException("Invalid Tag type");
        }
        Map<String, Tag> dataMap = ((CompoundTag) tag).getValue();
        int tileChunkPosX = (int) dataMap.get(Tags.TILE_CHUNK_POS_X).getValue();
        int tileChunkPosY = (int) dataMap.get(Tags.TILE_CHUNK_POS_Y).getValue();
        int tileChunkPosZ = (int) dataMap.get(Tags.TILE_CHUNK_POS_Z).getValue();
        Tile tile = TileRegistry.instance.getTileForIdentity((String) dataMap.get(Tags.TILE_IDENTITY).getValue());
        if (tile == null) {
            chunk.setTileAtPos(null, tileChunkPosX, tileChunkPosY, tileChunkPosZ);
        } else {
            if (tile instanceof ITileEntityProvider) {
                loadTileEntityForTagIntoChunk(chunk, dataMap.get(Tags.TILE_ENTITY_DATA), tileChunkPosX, tileChunkPosY, tileChunkPosZ);
            }
        }
    }

    public void loadTileEntityForTagIntoChunk(Chunk chunk, Tag teTag, int tileChunkPosX, int tileChunkPosY, int tileChunkPosZ) {
        if (!(teTag instanceof CompoundTag)) {
            throw new IllegalArgumentException("Invalid Tag type");
        }
        Map<String, Tag> dataMap = ((CompoundTag) teTag).getValue();
        TileEntity tileEntity = TileEntityRegistry.instance.getTileEntityForIdentity((String) dataMap.get(Tags.TILE_ENTITY_IDENTITY).getValue());
        if (tileEntity == null) {
            chunk.setTileEntityAtPos(null, tileChunkPosX, tileChunkPosY, tileChunkPosZ);
        } else {
            if (((int) dataMap.get(Tags.TILE_ENTITY_ERRORED).getValue()) == 1) {
                TransportManager.serverLogger.error("Loading errored TE on pos: " + +tileChunkPosX + "-" + tileChunkPosY + "-" + tileChunkPosZ + " for Chunk: " + chunk.getChunkX() + "-" + chunk.getChunkZ());
                // System.err.println("Loading errored TE on pos: " + +tileChunkPosX + "-" + tileChunkPosY + "-" + tileChunkPosZ + " for Chunk: " + chunk.getChunkX() + "-" + chunk.getChunkZ());
                chunk.setTileEntityAtPos(null, tileChunkPosX, tileChunkPosY, tileChunkPosZ);
                return;
            }
            tileEntity.loadFromDisk((CompoundTag) dataMap.get(Tags.TILE_ENTITY_DATA));
            chunk.setTileEntityAtPos(tileEntity, tileChunkPosX, tileChunkPosY, tileChunkPosZ);
        }
    }

    public void setChunkInWorld(World world, Chunk chunk) {
        chunk.setWorld(world);
        world.setChunk(chunk);
    }

    public void setChunkInWorldClient(WorldClient world, ChunkClient chunk) {
        chunk.setWorld(world);
        world.setChunk(chunk);
    }

    public static class Tags {
        public static final String WORLD = "world";
        public static final String CHUNK = "chunk";
        public static final String TILE = "tile";
        public static final String TILE_IDENTITY = "tileIdentity";
        public static final String TILE_CHUNK_POS_X = "tileChunkPosX";
        public static final String TILE_CHUNK_POS_Y = "tileChunkPosY";
        public static final String TILE_CHUNK_POS_Z = "tileChunkPosZ";
        public static final String TILE_ENTITY_DATA = "tileEntityData";
        public static final String TILE_ENTITY_IDENTITY = "tileEntityIdentity";
        public static final String TILE_ENTITY_ERRORED = "tileEntityErrored";
    }
}
