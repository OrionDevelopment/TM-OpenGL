package com.smithsgaming.transportmanager.main.world.generation;

import com.smithsgaming.transportmanager.main.core.GameLevel;
import com.smithsgaming.transportmanager.main.world.World;
import com.smithsgaming.transportmanager.util.concurrent.ProgressionNotifierThread;

/**
 * Created by Tim on 29/03/2016.
 */
public class WorldGenManager {

    public static WorldGenManager instance = new WorldGenManager();

    private long seed = System.currentTimeMillis();
    private GameLevel level = GameLevel.SMALL;

    private WorldGenManager() {
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public void setGameLevel(GameLevel level) {
        this.level = level;
    }

    public PreGenThread getNewPreGenThread() {
        return new PreGenThread();
    }

    public WorldGenThread getNewWorldGenThread() {
        return new WorldGenThread();
    }

    public class PreGenThread extends ProgressionNotifierThread {

        private WorldGenerationData worldGenerationData;

        @Override
        public synchronized void doRun() {
            WorldGenerationData data = new WorldGenerationData(seed, level.getWorldWidth(), level.getWorldHeight(), 0, level.getMaxTileHeight());

            onThreadProgressionChanged(0F, 0, "");
            WorldGraphFeaturesGenerator.instance.generate(data, this);
            onThreadProgressionChanged(1F, 0, "");

            worldGenerationData = data;
        }

        public WorldGenerationData getWorldGenerationData() {
            return worldGenerationData;
        }
    }

    public class WorldGenThread extends ProgressionNotifierThread {

        private WorldGenerationData worldGenerationData;
        private World result;

        @Override
        public synchronized void doRun() {
            WorldGenerationData data = new WorldGenerationData(seed, level.getWorldWidth(), level.getWorldHeight(), 0, level.getMaxTileHeight());

            onThreadProgressionChanged(0F, 0, "");

            WorldGraphFeaturesGenerator.instance.generate(data, this);

            onThreadProgressionChanged(0.1F, 0, "");

            HeightMapFeaturesGenerator.instance.generate(data, this);

            onThreadProgressionChanged(0.5F, 0, "");

            BaseMapFeaturesGenerator.instance.generate(data, this);

            onThreadProgressionChanged(1F, 0, "");

            //TODO: Merge WorldCoreData and WorldGenerationData into one and the same thing
            //result = new World(data);

            worldGenerationData = data;
        }

        public WorldGenerationData getWorldGenerationData() {
            return worldGenerationData;
        }

        public World getWorld() {
            return result;
        }
    }

}
