package com.smithsgaming.transportmanager.main.world.generation;

import com.hoten.delaunay.voronoi.Center;
import com.hoten.delaunay.voronoi.VoronoiGraph;
import com.hoten.delaunay.voronoi.nodename.as3delaunay.Voronoi;
import com.smithsgaming.transportmanager.main.core.BiomeManager;
import com.smithsgaming.transportmanager.main.world.biome.Biome;

import java.awt.*;
import java.util.Random;

/**
 * Created by Tim on 29/03/2016.
 */
public class TransportManagerWorldGraph extends VoronoiGraph {

    public TransportManagerWorldGraph(Voronoi v, int numLloydRelaxations, Random r) {
        super(v, numLloydRelaxations, r);
        LAKE = Biome.LAKE.getGenerationColor();
        OCEAN = Biome.OCEAN.getGenerationColor();
        RIVER = Biome.RIVER.getGenerationColor();
        BEACH = Biome.BEACH.getGenerationColor();
    }

    @Override
    protected Enum getBiome(Center center) {
        return BiomeManager.instance.getBaseBiomedForCenter(center);
    }

    @Override
    protected Color getColor(Enum anEnum) {
        return ((Biome) anEnum).getGenerationColor();
    }
}
