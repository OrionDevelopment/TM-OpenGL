package com.smithsgaming.transportmanager.network.message;

import com.smithsgaming.transportmanager.client.world.*;
import com.smithsgaming.transportmanager.main.world.*;
import com.smithsgaming.transportmanager.main.world.generation.WorldGenerationData;
import com.smithsgaming.transportmanager.util.*;
import io.netty.channel.*;
import javafx.util.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class WorldCoreDataMessage extends TMNetworkingMessage {

    WorldGenerationData coreData;

    public WorldCoreDataMessage(WorldGenerationData coreData) {
        this.coreData = coreData;
    }

    public WorldCoreDataMessage() {
    }

    @Override
    public TMNetworkingMessage onReceived(Channel channel, Side side) {
        if (side == Side.CLIENT) {
            WorldClientManager.instance.initializeWorld(coreData);
            Pair<Integer, Integer> nextChunkPair;
            World.WorldType type;
            if (WorldClientManager.instance.getNextChunkToSyncForWorld(World.WorldType.OVERGROUND) == null) {
                nextChunkPair = WorldClientManager.instance.getNextChunkToSyncForWorld(World.WorldType.OVERGROUND);
                type = World.WorldType.OVERGROUND;
            } else {
                nextChunkPair = WorldClientManager.instance.getNextChunkToSyncForWorld(World.WorldType.UNDERGROUND);
                type = World.WorldType.UNDERGROUND;
            }
            if (nextChunkPair == null) {
                return null;
            }
            return new RequestChunkDataMessage(nextChunkPair.getKey(), nextChunkPair.getValue(), type);
        }
        return null;
    }
}
