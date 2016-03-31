package com.smithsgaming.transportmanager.main.world.generation;

import com.smithsgaming.transportmanager.util.concurrent.*;

/**
 * Created by Tim on 29/03/2016.
 */
public interface IWorldGenFeature {

    void generate (WorldGenerationData worldGenerationData, ProgressionNotifierThread runningThread);

}
