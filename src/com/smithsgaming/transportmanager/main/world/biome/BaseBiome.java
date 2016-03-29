package com.smithsgaming.transportmanager.main.world.biome;

import com.hoten.delaunay.voronoi.Center;
import com.smithsgaming.transportmanager.main.world.tiles.Tile;

/**
 * Created by Tim on 29/03/2016.
 */
public abstract class BaseBiome {

    private Biome biomeType;

    public BaseBiome(Biome biome) {
        this.biomeType = biome;
    }

    public Biome getBiomeType() {
        return biomeType;
    }

    public abstract boolean isGenerationCenterValidForBiome(Center center);

    public abstract Tile getTile();
}
