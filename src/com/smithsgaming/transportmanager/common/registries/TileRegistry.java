package com.smithsgaming.transportmanager.common.registries;

import com.smithsgaming.transportmanager.common.GameController;
import com.smithsgaming.transportmanager.common.world.ITile;
import com.smithsgaming.transportmanager.util.logging.Markers;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Marc (Created on: 07.05.2016)
 */
public class TileRegistry {
    ConcurrentHashMap<String, ITile> mappedTiles;

    public TileRegistry () {
        this.mappedTiles = new ConcurrentHashMap<>();
    }

    public void registerTile (ITile tile) {
        if (mappedTiles.containsKey(tile.getIdentity()))
            GameController.runningInstance.getLogger().warn(Markers.TILEREGISTRY, "Their is already a Tile registered with the identity: " + tile.getIdentity() + " The original is being overridden");

        mappedTiles.put(tile.getIdentity(), tile);
    }

    public ITile getTile (String identity) {
        if (!mappedTiles.containsKey(identity))
            GameController.runningInstance.getLogger().warn(Markers.TILEREGISTRY, "Their is no Tile registered to this Registry with the requested identity: " + identity);

        return mappedTiles.get(identity);
    }
}
