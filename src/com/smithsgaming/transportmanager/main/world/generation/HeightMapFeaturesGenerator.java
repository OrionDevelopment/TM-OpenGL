package com.smithsgaming.transportmanager.main.world.generation;

import com.hoten.delaunay.voronoi.*;
import com.smithsgaming.transportmanager.util.concurrent.*;
import com.smithsgaming.transportmanager.util.math.*;

/**
 * Created by Tim on 29/03/2016.
 */
public class HeightMapFeaturesGenerator implements IWorldGenFeature {

    public static HeightMapFeaturesGenerator instance = new HeightMapFeaturesGenerator();

    @Override
    public void generate (WorldGenerationData worldGenerationData, ProgressionNotifierThread progressionNotifierThread) {
        int maxCount = worldGenerationData.getWorldWidth() * worldGenerationData.getWorldHeight();
        float progressionStep = 1F / maxCount;
        float totalProgression = 0F;

        progressionNotifierThread.onThreadProgressionChanged(totalProgression, 1, "Started generating heightmap...");
        for (int x = 0; x < worldGenerationData.getWorldWidth(); x++) {
            for (int y = 0; y < worldGenerationData.getWorldHeight(); y++) {
                worldGenerationData.getHeightMap()[x][y] = (int) ( worldGenerationData.getMaxTileHeight() * getHeightForPoint(worldGenerationData, new Vector2i(x, y)) - worldGenerationData.getWaterHeight() );

                totalProgression += progressionStep;
                progressionNotifierThread.onThreadProgressionChanged(totalProgression, 1, "Generating height for X: " + x + " Y: " + y);
            }
        }
        progressionNotifierThread.onThreadProgressionChanged(1F, 0, "Finished generating heightmap...");
    }

    private float getHeightForPoint (WorldGenerationData worldGenerationData, Vector2i point) {
        for (Center polygon : worldGenerationData.getWorldGraph().centers) {
            if (!WorldGenUtil.isPointInPolygon(polygon, point)) {
                continue;
            }
            float height = 0f;
            float totalDistance = 0f;
            for (Corner corner : polygon.corners) {
                Vector2d pos = new Vector2d(corner.loc.x, corner.loc.y);
                pos.sub(point.toDouble());
                pos.makePositive();
                totalDistance += pos.mag();
            }
            if (totalDistance <= 0) {
                throw new IllegalStateException("The given point seems to be having problems when getting assigned a height!");
            }
            for (Corner corner : polygon.corners) {
                Vector2d pos = new Vector2d(corner.loc.x, corner.loc.y);
                pos.sub(point.toDouble());
                pos.multiply(1 / totalDistance);
                pos.makePositive();
                float distance = (float) pos.mag();
                height += ( corner.elevation * ( 1 - distance ) );
            }
            return height;
        }
        return 0;
    }
}
