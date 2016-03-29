package com.smithsgaming.transportmanager.main.core;

import com.hoten.delaunay.voronoi.Center;
import com.smithsgaming.transportmanager.main.world.biome.BaseBiome;
import com.smithsgaming.transportmanager.main.world.biome.Biome;
import com.smithsgaming.transportmanager.main.world.tiles.Tile;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Tim on 29/03/2016.
 */
public class BiomeManager {

    public static final BiomeManager instance = new BiomeManager();

    static {
        instance.registerBiome(0, new BaseBiome(Biome.OCEAN) {
            @Override
            public boolean isGenerationCenterValidForBiome(Center center) {
                return center.ocean;
            }

            @Override
            public Tile getTile() {
                return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.OCEAN);
            }
        });

        instance.registerBiome(1, new BaseBiome(Biome.LAKE) {
            @Override
            public boolean isGenerationCenterValidForBiome(Center center) {
                return center.water && center.elevation >= 0.1 && center.elevation <= 0.8;
            }

            @Override
            public Tile getTile() {
                return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.LAKE);
            }
        });

        instance.registerBiome(2, new BaseBiome(Biome.BEACH) {
            @Override
            public boolean isGenerationCenterValidForBiome(Center center) {
                return center.coast;
            }

            @Override
            public Tile getTile() {
                return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.BEACH);
            }
        });

        instance.registerBiome(3, new BaseBiome(Biome.SNOW) {
            @Override
            public boolean isGenerationCenterValidForBiome(Center center) {
                return center.elevation > 0.8 && center.moisture > 0.5;
            }

            @Override
            public Tile getTile() {
                return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.SNOW);
            }
        });

        instance.registerBiome(4, new BaseBiome(Biome.TUNDRA) {
            @Override
            public boolean isGenerationCenterValidForBiome(Center center) {
                return center.elevation > 0.8 && center.moisture > 0.33 && center.moisture <= 0.5;
            }

            @Override
            public Tile getTile() {
                Random random = new Random();
                if (random.nextInt(7) == 0) {
                    return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.ICE_BUSH_BROWN);
                }
                return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.ICE);
            }
        });

        instance.registerBiome(5, new BaseBiome(Biome.BARE) {
            @Override
            public boolean isGenerationCenterValidForBiome(Center center) {
                return center.elevation > 0.8 && center.moisture > 0.16 && center.moisture <= 0.33;
            }

            @Override
            public Tile getTile() {
                return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.STONE_OVERGROUND);
            }
        });

        instance.registerBiome(6, new BaseBiome(Biome.SCORCHED) {
            @Override
            public boolean isGenerationCenterValidForBiome(Center center) {
                return center.elevation > 0.8 && center.moisture <= 0.16;
            }

            @Override
            public Tile getTile() {
                return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.SCORCHED);
            }
        });

        instance.registerBiome(7, new BaseBiome(Biome.TAIGA) {
            @Override
            public boolean isGenerationCenterValidForBiome(Center center) {
                return center.elevation > 0.6 && center.moisture > 0.66;
            }

            @Override
            public Tile getTile() {
                Random random = new Random();
                if (random.nextInt(5) == 0)
                    if (random.nextBoolean())
                        return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.ICE_BUSH_BROWN);
                    else
                        return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.ICE_BUSH_YELLOW);

                return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.ICE);
            }
        });

        instance.registerBiome(8, new BaseBiome(Biome.SHRUBLAND) {
            @Override
            public boolean isGenerationCenterValidForBiome(Center center) {
                return center.elevation > 0.6 && center.moisture > 0.33 && center.moisture <= 0.66;
            }

            @Override
            public Tile getTile() {
                Random random = new Random();
                if (random.nextBoolean())
                    return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.STONE_OVERGROUND);

                return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.DRY_GRASS);
            }
        });

        instance.registerBiome(9, new BaseBiome(Biome.TEMPERATE_DESERT) {
            @Override
            public boolean isGenerationCenterValidForBiome(Center center) {
                return ((center.elevation > 0.6 && center.moisture <= 0.33) || (center.elevation > 0.3 && center.moisture <= 0.16));
            }

            @Override
            public Tile getTile() {
                return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.DESERT);
            }
        });

        instance.registerBiome(10, new BaseBiome(Biome.TEMPERATE_RAIN_FOREST) {
            @Override
            public boolean isGenerationCenterValidForBiome(Center center) {
                return center.elevation > 0.3 & center.moisture > 0.83;
            }

            @Override
            public Tile getTile() {
                return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.GRASS);
            }
        });

        instance.registerBiome(11, new BaseBiome(Biome.TEMPERATE_DECIDUOUS_FOREST) {
            @Override
            public boolean isGenerationCenterValidForBiome(Center center) {
                return center.elevation > 0.3 & center.moisture <= 0.83 && center.moisture > 0.5;
            }

            @Override
            public Tile getTile() {
                return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.GRASS);
            }
        });

        instance.registerBiome(12, new BaseBiome(Biome.GRASSLAND) {
            @Override
            public boolean isGenerationCenterValidForBiome(Center center) {
                return center.elevation <= 0.6 & center.moisture <= 0.5 && center.moisture > 0.16;
            }

            @Override
            public Tile getTile() {
                return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.GRASS);
            }
        });

        instance.registerBiome(13, new BaseBiome(Biome.SUBTROPICAL_DESERT) {
            @Override
            public boolean isGenerationCenterValidForBiome(Center center) {
                return center.elevation <= 0.3 && center.moisture <= 0.16;
            }

            @Override
            public Tile getTile() {
                return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.DRY_GRASS);
            }
        });

        instance.registerBiome(14, new BaseBiome(Biome.ICE) {
            @Override
            public boolean isGenerationCenterValidForBiome(Center center) {
                return center.water && center.elevation > 0.8;
            }

            @Override
            public Tile getTile() {
                return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.ICE);
            }
        });

        instance.registerBiome(15, new BaseBiome(Biome.MARSH) {
            @Override
            public boolean isGenerationCenterValidForBiome(Center center) {
                return center.water && center.elevation <= 0.1;
            }

            @Override
            public Tile getTile() {
                Random random = new Random();
                if (random.nextInt(10) == 0) {
                    return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.LAKE);
                }
                return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.GRASS);
            }
        });

        instance.registerBiome(16, new BaseBiome(Biome.TROPICAL_RAIN_FOREST) {
            @Override
            public boolean isGenerationCenterValidForBiome(Center center) {
                return center.elevation <= 0.3 && center.moisture > 0.66;
            }

            @Override
            public Tile getTile() {
                return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.GRASS);
            }
        });

        instance.registerBiome(17, new BaseBiome(Biome.TROPICAL_SEASONAL_FOREST) {
            @Override
            public boolean isGenerationCenterValidForBiome(Center center) {
                return center.elevation <= 0.3 && center.moisture > 0.33 && center.moisture <= 0.66;
            }

            @Override
            public Tile getTile() {
                return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.GRASS);
            }
        });

        instance.registerBiome(18, new BaseBiome(Biome.RIVER) {
            @Override
            public boolean isGenerationCenterValidForBiome(Center center) {
                return false;
            }

            @Override
            public Tile getTile() {
                return TileRegistry.instance.getTileForIdentity(TileRegistry.TileNames.RIVER);
            }
        });


    }

    private HashMap<Integer, BaseBiome> biomeMappings = new HashMap<>();

    private BiomeManager() {
    }

    public void registerBiome(int id, BaseBiome biome) {
        if (biomeMappings.containsKey(id))
            throw new IllegalArgumentException("The given ID for the Biome: " + biome.getBiomeType().getName() + " is already in use!");

        biomeMappings.put(id, biome);
    }

    public BaseBiome getBiomeForId(int id) {
        return biomeMappings.get(id);
    }

    public HashMap<Integer, BaseBiome> getBiomeMappings() {
        return biomeMappings;
    }

    public int getBiomeID(BaseBiome biome) {
        if (instance.biomeMappings.containsValue(biome)) {
            for (Map.Entry<Integer, BaseBiome> e : instance.biomeMappings.entrySet()) {
                if (e.getValue() == biome) {
                    return e.getKey();
                }
            }
        }
        return -1;
    }

    public Biome getBaseBiomedForCenter(Center center) {
        for (Map.Entry<Integer, BaseBiome> entry : biomeMappings.entrySet()) {
            if (entry.getValue().isGenerationCenterValidForBiome(center))
                return entry.getValue().getBiomeType();
        }
        throw new IllegalStateException("The Generator produced a Area that has no valid Biome!");
    }

}
