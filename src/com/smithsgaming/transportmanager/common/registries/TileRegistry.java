package com.smithsgaming.transportmanager.common.registries;

import com.smithsgaming.transportmanager.common.world.*;

import java.util.concurrent.*;

/**
 * @Author Marc (Created on: 07.05.2016)
 */
public class TileRegistry {
    ConcurrentHashMap<String, ITile> mappedTiles;

    public TileRegistry () {
        this.mappedTiles = new ConcurrentHashMap<>();
    }

    public void registerTile (ITile tile) {
        mappedTiles.put(tile.getIdentity(), tile);
    }

    public ITile getTile (String identity) {
        return mappedTiles.get(identity);
    }
}
