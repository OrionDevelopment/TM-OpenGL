

package com.smithsgaming.transportmanager.main.world;

import com.smithsgaming.transportmanager.client.event.EventClientChunkChangePost;
import com.smithsgaming.transportmanager.client.event.EventClientChunkChangePre;
import com.smithsgaming.transportmanager.client.event.util.ChunkChangeType;
import com.smithsgaming.transportmanager.main.core.EntityRegistry;
import com.smithsgaming.transportmanager.main.entity.Entity;
import com.smithsgaming.transportmanager.main.tileentity.TileEntity;
import com.smithsgaming.transportmanager.main.world.chunk.Chunk;
import com.smithsgaming.transportmanager.main.world.generation.WorldGenerationData;
import com.smithsgaming.transportmanager.main.world.structure.Building;
import com.smithsgaming.transportmanager.main.world.tiles.Tile;
import com.smithsgaming.transportmanager.network.message.ClientEventProcessMessage;
import com.smithsgaming.transportmanager.network.server.TMNetworkingServer;
import com.smithsgaming.transportmanager.util.exception.EntityRegistrationException;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by marcf on 3/13/2016.
 */
public class WorldServer extends World {

    private ConcurrentHashMap<UUID, Building> buildings;
    private ConcurrentHashMap<UUID, Entity> entities;

    public WorldServer(WorldGenerationData data, WorldType type) {
        super(data, type);
        initWorld();
    }

    private void initWorld() {
        this.buildings = new ConcurrentHashMap<>();
        this.entities = new ConcurrentHashMap<>();
    }

    public void addEntity(Entity entity) {
        if (EntityRegistry.containsEntity(entity.getClass())) {
            this.entities.put(entity.uuid, entity);
            entity.onAddedToWorld(this);
        } else {
            throw new EntityRegistrationException("The entity: " + entity.getClass().toString() + "\n you tried to add to the world is not registered. This is a bug. Please register all entity classes during load");
        }
    }

    public Chunk getChunkAtPos(int chunkPosX, int chunkPosZ) {
        return coreData.getChunkMap()[chunkPosX][chunkPosZ];
    }

    public Tile getTileAtPos(int tileWorldPosX, int tileWorldPosZ) {
        return getChunkAtPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).getTileAtPos(tileWorldPosX % Chunk.chunkSize, tileWorldPosZ % Chunk.chunkSize);
    }

    public TileEntity getTileEntityAtPos(int tileWorldPosX, int tileWorldPosZ) {
        return getChunkAtPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).getTileEntityAtPos(tileWorldPosX % Chunk.chunkSize, tileWorldPosZ % Chunk.chunkSize);
    }

    public void setChunk(Chunk chunk) {
        sendChunkChangeMessagePre(ChunkChangeType.CHUNK, chunk.getChunkX(), chunk.getChunkZ());
        coreData.getChunkMap()[chunk.getChunkX()][chunk.getChunkZ()] = chunk;
        sendChunkChangeMessagePost(ChunkChangeType.CHUNK, chunk.getChunkX(), chunk.getChunkZ());
    }

    public void setTileAtPos(Tile tile, int tileWorldPosX, int tileWorldPosZ) {
        Chunk chunk = getChunkAtPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize);
        sendChunkChangeMessagePre(ChunkChangeType.TILE, chunk.getChunkX(), chunk.getChunkZ());
        chunk.setTileAtPos(tile, tileWorldPosX % Chunk.chunkSize, tileWorldPosZ % Chunk.chunkSize);
        sendChunkChangeMessagePost(ChunkChangeType.TILE, chunk.getChunkX(), chunk.getChunkZ());
    }

    public void setTileEntityAtPos(TileEntity tileEntity, int tileWorldPosX, int tileWorldPosZ) {
        Chunk chunk = getChunkAtPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize);
        sendChunkChangeMessagePre(ChunkChangeType.TILE_ENTITY, chunk.getChunkX(), chunk.getChunkZ());
        chunk.setTileEntityAtPos(tileEntity, tileWorldPosX % Chunk.chunkSize, tileWorldPosZ % Chunk.chunkSize);
        sendChunkChangeMessagePost(ChunkChangeType.TILE_ENTITY, chunk.getChunkX(), chunk.getChunkZ());
    }

    private void sendChunkChangeMessagePre(ChunkChangeType type, int x, int y) {
        TMNetworkingServer.sendMessage(new ClientEventProcessMessage(new EventClientChunkChangePre(this.getWorldType(), type, x, y)));
    }

    private void sendChunkChangeMessagePost(ChunkChangeType type, int x, int y) {
        TMNetworkingServer.sendMessage(new ClientEventProcessMessage(new EventClientChunkChangePost(this.getWorldType(), type, x, y)));
    }

    @Override
    public void update() {
    }
}
