

package com.smithsgaming.transportmanager.main.world.tiles;

import java.util.*;

/**
 * Created by marcf on 3/13/2016.
 */
public class TileRegistry {

    public static final String NULL_TILE_IDENTITY = "tile.null";
    public static TileRegistry instance = new TileRegistry();

    private HashMap<String, Tile> tileHashMap = new HashMap<>();

    protected TileRegistry() {
        tileHashMap.put(NULL_TILE_IDENTITY, null);
    }

    /**
     * Method to initialize the TileRegistry.
     */
    public static void init() {
        instance.registerTile(new NonBuildableTile(TileNames.OCEAN));
    }

    /**
     * Method to get a Tile for a given Identity name;
     *
     * @param identity The identity you are trying to get a lookup for.
     * @return The tile registered with the TileRegistry or null if nothing is registered with that name.
     */
    public Tile getTileForIdentity(String identity) {
        return tileHashMap.get(identity);
    }

    /**
     * Method to register a Tile.
     * If two tiles with the same identity are being registered the last one is being kept.
     *
     * @param tile The tile to register.
     */
    public void registerTile(Tile tile) {
        tileHashMap.put(tile.getIdentity(), tile);
    }

    /**
     * Class holds the identities of all tiles used in the Game.
     */
    public static class TileNames {
        public static final String OCEAN = "tile.nonbuildable.Ocean";
    }


}


