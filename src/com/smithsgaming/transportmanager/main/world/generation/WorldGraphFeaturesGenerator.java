package com.smithsgaming.transportmanager.main.world.generation;

import com.smithsgaming.transportmanager.util.concurrent.*;

/**
 * Created by Tim on 29/03/2016.
 */
public class WorldGraphFeaturesGenerator implements IWorldGenFeature {

    public static WorldGraphFeaturesGenerator instance = new WorldGraphFeaturesGenerator();

    private WorldGraphFeaturesGenerator () {
    }

    @Override
    public void generate (WorldGenerationData worldGenerationData, ProgressionNotifierThread progressionNotifierThread) {
        progressionNotifierThread.onThreadProgressionChanged(0F, 1, "Generating world graph...");
        TransportManagerWorldGraph worldGraph = new TransportManagerWorldGraph(worldGenerationData.getVoronoiGenerator(), 2, worldGenerationData.getGenerationRandom());
        progressionNotifierThread.onThreadProgressionChanged(0.5F, 1, "Generating biome separations...");
        worldGenerationData.setTransportManagerWorldGraph(worldGraph);
        worldGenerationData.setPregenImage(worldGraph.createMap());
        progressionNotifierThread.onThreadProgressionChanged(1F, 1, "Finished generating world graph...");
    }
}
