

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
        instance.registerTile(new BuildableTile(TileNames.GRASS));
        instance.registerTile(new BuildableTile(TileNames.DRY_GRASS));
        instance.registerTile(new BuildableTile(TileNames.BEACH));
        instance.registerTile(new BuildableTile(TileNames.DESERT));
        instance.registerTile(new BridgableTile(TileNames.RIVER));
        instance.registerTile(new BuildableTile(TileNames.SNOW));
        instance.registerTile(new BuildableTile(TileNames.STONE_OVERGROUND));
        instance.registerTile(new BuildableTile(TileNames.STONE_UNDERGROUND));
        instance.registerTile(new BuildableTile(TileNames.ICE));
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
        public static final String GRASS = "tile.buildable.Grass";
        public static final String DRY_GRASS = "tile.buildable.DryGrass";
        public static final String BEACH = "tile.buildable.Beach";
        public static final String DESERT = "tile.buildable.Desert";
        public static final String RIVER = "tile.bridgable.River";
        public static final String SNOW = "tile.buildable.Snow";
        public static final String STONE_OVERGROUND = "tile.buildable.StoneOverground";
        public static final String STONE_UNDERGROUND = "tile.buildable.StoneUnderground";
        public static final String ICE = "tile.buildable.Ice";
    }


}


