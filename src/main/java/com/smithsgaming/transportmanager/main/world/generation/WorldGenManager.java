package com.smithsgaming.transportmanager.main.world.generation;

import com.smithsgaming.transportmanager.main.core.GameLevel;
import com.smithsgaming.transportmanager.main.world.World;
import com.smithsgaming.transportmanager.main.world.WorldServer;
import com.smithsgaming.transportmanager.util.Definitions;
import com.smithsgaming.transportmanager.util.concurrent.ProgressionNotifierThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Tim on 29/03/2016.
 */
public class WorldGenManager {

    public static WorldGenManager instance = new WorldGenManager();
    public static Logger worldGenLogger = LogManager.getLogger(Definitions.Loggers.WORLDGEN);

    private long seed =  0; //System.currentTimeMillis();
    private GameLevel level = GameLevel.SMALL;

    private WorldGenManager () {
    }

    public long getSeed () {
        return seed;
    }

    public void setSeed (long seed) {
        this.seed = seed;
    }

    public void setGameLevel (GameLevel level) {
        this.level = level;
    }

    public PreGenThread getNewPreGenThread () {
        return new PreGenThread();
    }

    public WorldGenThread getNewWorldGenThread () {
        return new WorldGenThread();
    }

    public class PreGenThread extends ProgressionNotifierThread {

        private WorldGenerationData worldGenerationData;

        @Override
        public synchronized void doRun () {
            WorldGenerationData data = new WorldGenerationData(seed, level.getWorldWidth(), level.getWorldHeight(), 0, level.getMaxTileHeight());

            onThreadProgressionChanged(0F, 0, "");
            WorldGraphFeaturesGenerator.instance.generate(data, this);
            onThreadProgressionChanged(1F, 0, "");

            worldGenerationData = data;
        }

        public WorldGenerationData getWorldGenerationData () {
            return worldGenerationData;
        }
    }

    public class WorldGenThread extends ProgressionNotifierThread {

        private WorldGenerationData worldGenerationDataOverground;
        private WorldGenerationData worldGenerationDataUnderground;
        private World overgroundWorld;
        private World undergroundWorld;

        public WorldGenerationData getWorldGenerationData(World.WorldType type)
        {
            if (type == World.WorldType.OVERGROUND)
            {
                return worldGenerationDataOverground;
            }
            else if (type == World.WorldType.UNDERGROUND)
            {
                return worldGenerationDataUnderground;
            }
            return null;
        }

        public World getWorld(World.WorldType type)
        {
            if (type == World.WorldType.OVERGROUND)
            {
                return overgroundWorld;
            }
            else if (type == World.WorldType.UNDERGROUND)
            {
                return undergroundWorld;
            }
            return null;
        }

        @Override
        public synchronized void doRun () {
            WorldGenerationData overgroundData = new WorldGenerationData(seed, level.getWorldWidth(), level.getWorldHeight(), 0, level.getMaxTileHeight());
            overgroundWorld = new WorldServer(overgroundData, World.WorldType.OVERGROUND);
            overgroundData.setWorld(overgroundWorld);
            onThreadProgressionChanged(0F, 0, "");
            WorldGraphFeaturesGenerator.instance.generate(overgroundData, this);
            onThreadProgressionChanged(0.125F, 0, "");
            HeightMapFeaturesGenerator.instance.generate(overgroundData, this);
            onThreadProgressionChanged(0.25F, 0, "");
            NoisyBiomeEdgeGenerator.instance.generate(overgroundData, this);
            BaseMapFeaturesGenerator.instance.generate(overgroundData, this);
            CrossBiomeMapFeaturesGenerator.instance.generate(overgroundData, this);
            onThreadProgressionChanged(0.375F, 0, "");
            WorldGenerationData undergroundData = new WorldGenerationData(seed, level.getWorldWidth(), level.getWorldHeight(), 0, level.getMaxTileHeight());
            undergroundWorld = new WorldServer(undergroundData, World.WorldType.UNDERGROUND);
            undergroundData.setWorld(undergroundWorld);
            onThreadProgressionChanged(0.5F, 0, "");
            WorldGraphFeaturesGenerator.instance.generate(undergroundData, this);
            onThreadProgressionChanged(0.675F, 0, "");
            HeightMapFeaturesGenerator.instance.generate(undergroundData, this);
            onThreadProgressionChanged(0.8F, 0, "");
            NoisyBiomeEdgeGenerator.instance.generate(undergroundData, this);
            BaseMapFeaturesGenerator.instance.generate(undergroundData, this);
            onThreadProgressionChanged(1F, 0, "");
            worldGenerationDataOverground = overgroundData;
            worldGenerationDataUnderground = undergroundData;
        }


    }

}
