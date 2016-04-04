

package com.smithsgaming.transportmanager.main.core;

import com.smithsgaming.transportmanager.main.world.tiles.*;

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
        instance.registerTile(new BuildableTile(TileNames.GRASS));
        instance.registerTile(new BuildableTile(TileNames.DRY_GRASS));
        instance.registerTile(new BuildableTile(TileNames.BEACH));
        instance.registerTile(new BuildableTile(TileNames.DESERT));
        instance.registerTile(new BridgableTile(TileNames.RIVER));
        instance.registerTile(new BuildableTile(TileNames.SNOW));
        instance.registerTile(new BuildableTile(TileNames.STONE_OVERGROUND));
        instance.registerTile(new BuildableTile(TileNames.STONE_UNDERGROUND));
        instance.registerTile(new BuildableTile(TileNames.ICE));
        instance.registerTile(new BuildableTile(TileNames.ICE_BUSH_BROWN));
        instance.registerTile(new NonBuildableTile(TileNames.LAKE));
        instance.registerTile(new BuildableTile(TileNames.SCORCHED));
        instance.registerTile(new BuildingTile(TileNames.BUILDING));
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
     * Method to register a Tile. If two tiles with the same identity are being registered the last one is being kept.
     *
     * @param tile The tile to register.
     */
    public void registerTile(Tile tile) {
        tileHashMap.put(tile.getIdentity(), tile);
    }

    public String getIdentityForTile(Tile tile) {
        if (tileHashMap.containsValue(tile)) {
            for (Map.Entry<String, Tile> e : tileHashMap.entrySet()) {
                if (e.getValue() == tile) {
                    return e.getKey();
                }
            }
        }
        return NULL_TILE_IDENTITY;
    }
}


