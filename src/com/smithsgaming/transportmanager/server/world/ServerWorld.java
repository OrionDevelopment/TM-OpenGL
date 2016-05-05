package com.smithsgaming.transportmanager.server.world;

import com.smithsgaming.transportmanager.common.world.*;
import com.smithsgaming.transportmanager.util.constants.*;

/**
 * @Author Marc (Created on: 05.05.2016)
 */
public class ServerWorld implements IWorld {
    @Override
    public IWorldLayer getOverworld () {
        return WorldLayers.WORLD.getLayerData();
    }

    @Override
    public IWorldLayer getMetroWorld () {
        return WorldLayers.METRO.getLayerData();
    }
}
