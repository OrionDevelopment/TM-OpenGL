package com.smithsgaming.transportmanager.common.registries;

import com.smithsgaming.transportmanager.common.world.*;

import java.util.concurrent.*;

/**
 * @Author Marc (Created on: 07.05.2016)
 */
public class TileEntityRegistry {
    ConcurrentHashMap<String, ITileEntity> mappedTiles;

    public TileEntityRegistry () {
        this.mappedTiles = new ConcurrentHashMap<>();
    }

    public void registerTile (ITileEntity tileIdentity) {
        mappedTiles.put(tileIdentity.getIdentity(), tileIdentity);
    }

    public ITileEntity getTileIdentity (String identity) {
        return mappedTiles.get(identity);
    }
}
