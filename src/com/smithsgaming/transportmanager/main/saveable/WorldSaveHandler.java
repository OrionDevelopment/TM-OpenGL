package com.smithsgaming.transportmanager.main.saveable;

import com.google.common.base.Stopwatch;
import com.smithsgaming.transportmanager.client.world.WorldClient;
import com.smithsgaming.transportmanager.client.world.chunk.ChunkClient;
import com.smithsgaming.transportmanager.main.TransportManager;
import com.smithsgaming.transportmanager.main.world.World;
import com.smithsgaming.transportmanager.main.world.chunk.Chunk;
import com.smithsgaming.transportmanager.main.world.tileentities.ITileEntityProvider;
import com.smithsgaming.transportmanager.main.world.tileentities.TileEntity;
import com.smithsgaming.transportmanager.main.world.tileentities.TileEntityRegistry;
import com.smithsgaming.transportmanager.main.world.tiles.Tile;
import com.smithsgaming.transportmanager.main.world.tiles.TileRegistry;
import com.smithsgaming.transportmanager.util.nbt.NBTTagCompound;

import java.util.concurrent.TimeUnit;

/**
 * Created by marcf on 3/13/2016.
 */
public class WorldSaveHandler {

    public static WorldSaveHandler instance = new WorldSaveHandler();

    public NBTTagCompound writeTagForWorld(World world) {
        NBTTagCompound worldTag = new NBTTagCompound();
        worldTag.writeInt(NBTTags.WORLD_TYPE, world.getWorldTypeOrdinal());
        for (int x = 0; x < world.getCoreData().getWorldWidth() / Chunk.chunkSize + 1; x++) {
            for (int z = 0; z < world.getCoreData().getWorldLength() / Chunk.chunkSize + 1; z++) {
                writeTagForChunk(worldTag, world, x, z);
            }
        }
        return worldTag;
    }

    public void writeTagForChunk(NBTTagCompound worldTag, World world, int chunkPosX, int chunkPosZ) {
        System.out.println("Started writing chunk to Tag: " + chunkPosX + ", " + chunkPosZ);
        Stopwatch stopwatch = Stopwatch.createStarted();
        NBTTagCompound chunkTag = new NBTTagCompound();
        Chunk chunk = world.getChunkAtPos(chunkPosX, chunkPosZ);
        for (int x = 0; x < Chunk.chunkSize; x++) {
            for (int z = 0; z < Chunk.chunkSize; z++) {
                writeTagForTile(chunkTag, chunk, x, z);
            }
        }
        worldTag.writeCompoundTag(NBTTags.CHUNK + "_" + chunkPosX + "_" + chunkPosZ, chunkTag.toCompoundTag(NBTTags.CHUNK));
        TransportManager.serverLogger.debug("   ==> Finished converting in: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms.");
    }

    public void writeTagForTile(NBTTagCompound chunkTag, Chunk chunk, int tileChunkPosX, int tileChunkPosZ) {
        NBTTagCompound tileTag = new NBTTagCompound();
        tileTag.writeInt(NBTTags.TILE_CHUNK_POS_X, tileChunkPosX);
        tileTag.writeInt(NBTTags.TILE_CHUNK_POS_Z, tileChunkPosZ);
        Tile tile = chunk.getTileAtPos(tileChunkPosX, tileChunkPosZ);
        if (tile == null) {
            tileTag.writeString(NBTTags.TILE_IDENTITY, TileRegistry.NULL_TILE_IDENTITY);
        } else {
            tileTag.writeString(NBTTags.TILE_IDENTITY, tile.getIdentity());
            if (tile instanceof ITileEntityProvider) {
                writeTagForTileEntity(tileTag, chunk, tileChunkPosX, tileChunkPosZ);
            }
        }
        chunkTag.writeCompoundTag(NBTTags.TILE + "_" + tileChunkPosX + "_" + tileChunkPosZ, tileTag.toCompoundTag(NBTTags.TILE));
    }

    public void writeTagForTileEntity(NBTTagCompound tileTag, Chunk chunk, int tileChunkPosX, int tileChunkPosZ) {
        NBTTagCompound tileEntityTag = new NBTTagCompound();
        TileEntity tileEntity = chunk.getTileEntityAtPos(tileChunkPosX, tileChunkPosZ);
        if (tileEntity == null) {
            throw new IllegalArgumentException("The given chunk pos does not contain a TE!");
        } else {
            try {
                tileEntity.writeToDisk(tileEntityTag);
                tileEntityTag.writeInt(NBTTags.TILE_ENTITY_ERRORED, 0);
            } catch (Exception ex) {
                TransportManager.serverLogger.error("Exception while attempting to write TE on Pos: " + tileChunkPosX + "-" + tileChunkPosZ + " for Chunk: " + chunk.getChunkX() + "-" + chunk.getChunkZ() + " has been caught: +\n");
                TransportManager.serverLogger.error(ex.getStackTrace() + "\n");
                TransportManager.serverLogger.error("On load of this world the TE will be replaced with a new one!");
                tileEntityTag.writeInt(NBTTags.TILE_ENTITY_ERRORED, 1);
            }
            tileTag.writeCompoundTag(NBTTags.TILE_ENTITY_DATA, tileEntityTag.toCompoundTag(NBTTags.TILE_ENTITY_DATA));
        }
    }

    public void loadWorldFromTag(World world, NBTTagCompound worldTag) {
        world.setType(World.WorldType.values()[worldTag.getInt(NBTTags.WORLD_TYPE)]);
        for (int x = 0; x < world.getCoreData().getWorldWidth() / Chunk.chunkSize + 1; x++) {
            for (int z = 0; z < world.getCoreData().getWorldLength() / Chunk.chunkSize + 1; z++) {
                NBTTagCompound chunkTag = new NBTTagCompound(worldTag.getTagCompound(NBTTags.CHUNK + "_" + x + "_" + z));
                loadChunkForTagIntoWorld(chunkTag, world, x, z);
            }
        }
    }

    public void loadChunkForTagIntoWorld(NBTTagCompound chunkTag, World world, int chunkPosX, int chunkPosZ) {
        Chunk chunk = world.getChunkAtPos(chunkPosX, chunkPosZ);
        for (int x = 0; x < Chunk.chunkSize; x++) {
            for (int z = 0; z < Chunk.chunkSize; z++) {
                NBTTagCompound tileTag = new NBTTagCompound(chunkTag.getTagCompound(NBTTags.TILE + "_" + x + "_" + z));
                loadTileForTagIntoChunk(tileTag, chunk, x, z);
            }
        }
    }

    public void loadTileForTagIntoChunk(NBTTagCompound tileTag, Chunk chunk, int x, int z) {
        int tileChunkPosX = tileTag.getInt(NBTTags.TILE_CHUNK_POS_X);
        int tileChunkPosZ = tileTag.getInt(NBTTags.TILE_CHUNK_POS_Z);
        Tile tile = TileRegistry.instance.getTileForIdentity(tileTag.getString(NBTTags.TILE_IDENTITY));
        if (tile == null) {
            chunk.setTileAtPos(null, tileChunkPosX, tileChunkPosZ);
        } else {
            chunk.setTileAtPos(tile, tileChunkPosX, tileChunkPosZ);
            if (tile instanceof ITileEntityProvider) {
                NBTTagCompound tileEntityTag = new NBTTagCompound(tileTag.getTagCompound(NBTTags.TILE_ENTITY_DATA));
                loadTileEntityForTagIntoChunk(chunk, tileEntityTag, tileChunkPosX, tileChunkPosZ);
            }
        }
    }

    public void loadTileEntityForTagIntoChunk(Chunk chunk, NBTTagCompound tileEntityTag, int tileChunkPosX, int tileChunkPosZ) {
        TileEntity tileEntity = TileEntityRegistry.instance.getTileEntityForIdentity(tileEntityTag.getString(NBTTags.TILE_ENTITY_IDENTITY));
        if (tileEntity == null) {
            chunk.setTileEntityAtPos(null, tileChunkPosX, tileChunkPosZ);
        } else {
            if (tileEntityTag.getInt(NBTTags.TILE_ENTITY_ERRORED) == 1) {
                TransportManager.serverLogger.error("Loading errored TE on pos: " + +tileChunkPosX + "-" + tileChunkPosZ + " for Chunk: " + chunk.getChunkX() + "-" + chunk.getChunkZ());
                // System.err.println("Loading errored TE on pos: " + +tileChunkPosX + "-" + tileChunkPosY + "-" + tileChunkPosZ + " for Chunk: " + chunk.getChunkX() + "-" + chunk.getChunkZ());
                chunk.setTileEntityAtPos(null, tileChunkPosX, tileChunkPosZ);
                return;
            }
            tileEntity.loadFromDisk(tileEntityTag);
            chunk.setTileEntityAtPos(tileEntity, tileChunkPosX, tileChunkPosZ);
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
}
