package com.smithsgaming.transportmanager.network.message;

import com.google.common.base.*;
import com.smithsgaming.transportmanager.client.*;
import com.smithsgaming.transportmanager.client.event.*;
import com.smithsgaming.transportmanager.client.world.*;
import com.smithsgaming.transportmanager.client.world.chunk.*;
import com.smithsgaming.transportmanager.main.saveable.*;
import com.smithsgaming.transportmanager.main.world.*;
import com.smithsgaming.transportmanager.main.world.chunk.*;
import com.smithsgaming.transportmanager.network.client.TMNetworkingClient;
import com.smithsgaming.transportmanager.util.*;
import io.netty.channel.*;
import javafx.util.*;

import java.io.*;
import java.util.concurrent.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class ChunkDataMessage extends TMNetworkingMessage implements Serializable {

    private Chunk chunk;
    private int x;
    private int z;
    private World.WorldType type;

    public ChunkDataMessage () {
    }

    public ChunkDataMessage (Chunk chunk) {
        this.chunk = chunk;
        this.x = chunk.getChunkX();
        this.z = chunk.getChunkZ();
        this.type = chunk.getWorld().getWorldType();
    }

    @Override
    public TMNetworkingMessage onReceived (Channel channel, Side side, MessageContext context) {
        if (side == Side.CLIENT) {
            context.getLogger().trace("Received data package on side: " + side + " for chunk: " + x + "-" + z + " for world type:" + type);

            Stopwatch stopwatch = Stopwatch.createStarted();

            WorldSaveHandler.instance.setChunkInWorldClient(WorldClientManager.instance.getWorld(type), new ChunkClient(chunk));

            context.getLogger().trace("   ==> Finished loading in: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms.");
            stopwatch.reset();
            stopwatch.start();

            WorldClientManager.instance.getWorld(type).setChunkLoadedState(x, z, true);

            Pair<Integer, Integer> nextChunkPair = WorldClientManager.instance.getNextChunkToSyncForWorld(type);

            if (nextChunkPair == null) {
                if (type == World.WorldType.UNDERGROUND) {
                    nextChunkPair = WorldClientManager.instance.getNextChunkToSyncForWorld(World.WorldType.OVERGROUND);
                    return new RequestChunkDataMessage(nextChunkPair.getKey(), nextChunkPair.getValue(), World.WorldType.OVERGROUND);
                }

                context.getLogger().trace("   ==> Finished WorldServer download!");
                TransportManagerClient.instance.registerEvent(new EventClientWorldLoaded());

                return null;
            }

            context.getLogger().trace("   ==> Retrieved new CoordPair in: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms.");

            return new RequestChunkDataMessage(nextChunkPair.getKey(), nextChunkPair.getValue(), type);
        }

        return null;
    }
}
