package com.smithsgaming.transportmanager.server.world;

import com.smithsgaming.transportmanager.common.entity.*;
import com.smithsgaming.transportmanager.common.world.*;
import com.smithsgaming.transportmanager.util.common.*;

import java.util.*;
import java.util.stream.*;

/**
 * @Author Marc (Created on: 05.05.2016)
 */
public class ServerWorldLayer implements IWorldLayer {

    ServerWorld world;

    HashMap<WorldCoordinate, ITile> tileHashMap;
    HashMap<WorldCoordinate, ITileEntity> tileIdentityHashMap;

    ArrayList<EntityInstance> entityInstances;

    public ServerWorldLayer (ServerWorld world) {
        this.world = world;

        this.tileHashMap = new HashMap<>();
        this.tileIdentityHashMap = new HashMap<>();

        this.entityInstances = new ArrayList<>();
    }

    @Override
    public IWorld getWorld () {
        return world;
    }

    @Override
    public ITile getTileForPosition (WorldCoordinate worldCoordinate) {
        return tileHashMap.get(worldCoordinate);
    }

    @Override
    public ITileEntity getTileIdentity (WorldCoordinate worldCoordinate) {
        return tileIdentityHashMap.get(worldCoordinate);
    }

    @Override
    public void setTileForPosition (ITile tile, WorldCoordinate worldCoordinate) {
        tileHashMap.put(worldCoordinate, tile);
    }

    @Override
    public void spawnEntityInstanceInLayer (EntityInstance instance) {
        entityInstances.add(instance);
    }

    @Override
    public void killEntityInstanceInLayer (EntityInstance instance) {
        entityInstances.remove(instance);
    }

    @Override
    public void killEntityInstanceInLayer (WorldCoordinate pos, float radius) {
        ArrayList<EntityInstance> toDelete = entityInstances.stream().filter(instance -> Math.abs(pos.getDistanceTo(instance.getPos().getCoordinate())) < Math.abs(radius)).collect(Collectors.toCollection(ArrayList::new));

        toDelete.forEach(this::killEntityInstanceInLayer);
    }
}
