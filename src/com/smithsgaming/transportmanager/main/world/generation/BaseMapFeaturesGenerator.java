package com.smithsgaming.transportmanager.main.world.generation;

import com.smithsgaming.transportmanager.main.*;
import com.smithsgaming.transportmanager.main.core.*;
import com.smithsgaming.transportmanager.main.world.biome.*;
import com.smithsgaming.transportmanager.main.world.chunk.*;
import com.smithsgaming.transportmanager.util.concurrent.*;

import java.awt.*;
import java.awt.image.*;

/**
 * Created by Tim on 29/03/2016.
 */
public class BaseMapFeaturesGenerator implements IWorldGenFeature {

    public static BaseMapFeaturesGenerator instance = new BaseMapFeaturesGenerator();

    @Override
    public void generate (WorldGenerationData worldGenerationData, ProgressionNotifierThread progressionNotifierThread) {
        BufferedImage biomeData = worldGenerationData.getPregenImage();
        int maxCount = worldGenerationData.getWorldWidth() * worldGenerationData.getWorldHeight();
        float progressionStep = 1F / maxCount;
        float totalProgression = 0F;

        for (int x = 0; x < worldGenerationData.getWorldWidth() / Chunk.chunkSize + 1; x++) {
            for (int z = 0; z < worldGenerationData.getWorldHeight() / Chunk.chunkSize + 1; z++) {
                worldGenerationData.world.setChunk(new Chunk(worldGenerationData.world, x, z));
            }
        }
        progressionNotifierThread.onThreadProgressionChanged(totalProgression, 1, "Started generating biomes...");
        for (int x = 0; x < worldGenerationData.getWorldWidth(); x++) {
            for (int y = 0; y < worldGenerationData.getWorldHeight(); y++) {
                int colorData = biomeData.getRGB(x, y);
                BaseBiome blockBiome = BiomeManager.instance.getBaseBiomeForGenerationColor(new Color(colorData));

                if (colorData == 0) {
                    blockBiome = BiomeManager.instance.getBiomeForId(0);
                }

                if (blockBiome == null) {
                    TransportManager.serverLogger.warn("The block at position (X: " + x + " Y: " + y + ") has no biome with associated - Color: " + colorData + "!");
                    continue;
                }
                totalProgression += progressionStep;
                progressionNotifierThread.onThreadProgressionChanged(totalProgression, 1, "Generated Biome: " + blockBiome.getBiomeType().getName() + " for X: " + x + " Y: " + y);
                worldGenerationData.getBiomeMap()[x][y] = blockBiome;
                worldGenerationData.world.setTileAtPos(blockBiome.getTile(), x, y);
            }
        }
        progressionNotifierThread.onThreadProgressionChanged(1F, 1, "Finished generating biomes...");
    }
}
