package com.smithsgaming.transportmanager.util.common;

import com.smithsgaming.transportmanager.common.world.*;
import com.smithsgaming.transportmanager.util.constants.*;
import net.smert.frameworkgl.math.*;

/**
 * @Author Marc (Created on: 05.05.2016)
 */
public class WorldPosition {

    WorldCoordinate coordinate;
    IWorldLayer layer;

    public WorldPosition (WorldCoordinate coordinate, IWorldLayer layer) {
        this.coordinate = coordinate;
        this.layer = layer;
    }

    public WorldPosition (Vector3f coordinate, IWorldLayer layer) {
        this(new WorldCoordinate(coordinate), layer);
    }

    public WorldPosition (WorldCoordinate coordinate, WorldLayers layer) {
        this.coordinate = coordinate;
        this.layer = layer.getLayerData();
    }

    public WorldPosition (Vector3f coordinate, WorldLayers layer) {
        this(new WorldCoordinate(coordinate), layer);
    }

    public WorldCoordinate getCoordinate () {
        return coordinate;
    }

    public void setCoordinate (WorldCoordinate coordinate) {
        this.coordinate = coordinate;
    }

    public IWorldLayer getLayer () {
        return layer;
    }

    public void setLayer (IWorldLayer layer) {
        this.layer = layer;
    }
}
