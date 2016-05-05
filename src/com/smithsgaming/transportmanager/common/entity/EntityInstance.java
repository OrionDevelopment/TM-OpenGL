package com.smithsgaming.transportmanager.common.entity;

import com.smithsgaming.transportmanager.common.data.*;
import com.smithsgaming.transportmanager.common.world.*;
import com.smithsgaming.transportmanager.util.common.*;
import com.smithsgaming.transportmanager.util.constants.*;
import com.smithsgaming.transportmanager.util.data.*;
import net.smert.frameworkgl.math.*;

/**
 * @Author Marc (Created on: 25.04.2016)
 */
public final class EntityInstance implements ISavable, IUpdatable {

    private final IEntity entity;
    private WorldPosition pos;
    private IDataStruct instanceData;

    public EntityInstance (IEntity entity, WorldCoordinate coordinate, IWorldLayer layer) {
        this.entity = entity;
        this.pos = new WorldPosition(coordinate, layer);

        this.instanceData = entity.getDefaultData();
    }

    public EntityInstance (IEntity entity, Vector3f coordinate, IWorldLayer layer) {
        this(entity, new WorldCoordinate(coordinate), layer);
    }

    public EntityInstance (IEntity entity, WorldCoordinate coordinate, WorldLayers layer) {
        this(entity, coordinate, layer.getLayerData());
    }

    public EntityInstance (IEntity entity, Vector3f coordinate, WorldLayers layer) {
        this(entity, new Vector3f(), layer.getLayerData());
    }

    public IEntity getEntity () {
        return entity;
    }

    public WorldPosition getPos () {
        return pos;
    }

    public void setPos (WorldPosition pos) {
        if (this.pos.getLayer() != pos.getLayer()) {
            this.pos.getLayer().killEntityInstanceInLayer(this);
            pos.getLayer().spawnEntityInstanceInLayer(this);
        }

        this.pos = pos;
    }

    @Override
    public IDataStruct onWrite () {
        return entity.onWriteInstance(this);
    }

    @Override
    public void onRead (IDataStruct dataStruct) {
        entity.onReadInstance(this, dataStruct);
    }

    @Override
    public void update () {
        entity.onInstanceUpdate(this);
    }
}
