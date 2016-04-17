package com.smithsgaming.transportmanager.main.world.generation;

import com.smithsgaming.transportmanager.util.concurrent.*;

import javax.imageio.*;
import java.io.*;
import java.text.*;
import java.util.*;

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

        //TODO: Add a copy of the Settings system used on the client to the Server side as well.
        if (true) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
            Date date = new Date();

            try {
                //ImageIO.write(worldGenerationData.getPregenImage(), "PNG", );
                File imageFile = new File("resource-test/worldgen/images/WorldGenPreImage (" + dateFormat.format(date) + ").png");
                imageFile.mkdirs();
                if (!imageFile.exists())
                    imageFile.createNewFile();

                ImageIO.write(worldGenerationData.getPregenImage(), "PNG", imageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
