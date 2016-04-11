

package com.smithsgaming.transportmanager.main.world;

import com.smithsgaming.transportmanager.main.core.EntityRegistry;
import com.smithsgaming.transportmanager.main.core.TileRegistry;
import com.smithsgaming.transportmanager.main.entity.Entity;
import com.smithsgaming.transportmanager.main.world.generation.*;
import com.smithsgaming.transportmanager.main.world.structure.Building;
import com.smithsgaming.transportmanager.main.world.tiles.TileNames;
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

    @Override
    public void update() {
        this.coreData.setTileAtPos(TileRegistry.instance.getTileForIdentity(TileNames.BEACH), 3, 3);
    }
}
