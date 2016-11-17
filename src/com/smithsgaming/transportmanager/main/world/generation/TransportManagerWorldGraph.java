package com.smithsgaming.transportmanager.main.world.generation;

import com.hoten.delaunay.voronoi.*;
import com.hoten.delaunay.voronoi.nodename.as3delaunay.*;
import com.smithsgaming.transportmanager.main.core.*;
import com.smithsgaming.transportmanager.main.world.biome.*;

import java.awt.*;
import java.util.*;

/**
 * Created by Tim on 29/03/2016.
 */
public class TransportManagerWorldGraph extends VoronoiGraph {

    public TransportManagerWorldGraph (Voronoi v, int numLloydRelaxations, Random r) {
        super(v, numLloydRelaxations, r);
        LAKE = Biome.LAKE.getGenerationColor();
        OCEAN = Biome.OCEAN.getGenerationColor();
        RIVER = Biome.RIVER.getGenerationColor();
        BEACH = Biome.BEACH.getGenerationColor();
    }

    @Override
    protected Enum getBiome (Center center) {
        return BiomeManager.instance.getBaseBiomeForCenter(center);
    }

    @Override
    protected Color getColor (Enum anEnum) {
        return ( (Biome) anEnum ).getGenerationColor();
    }
}
