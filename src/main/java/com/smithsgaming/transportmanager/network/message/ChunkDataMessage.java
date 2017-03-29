package com.smithsgaming.transportmanager.network.message;

import com.google.common.base.Stopwatch;
import com.smithsgaming.transportmanager.client.TransportManagerClient;
import com.smithsgaming.transportmanager.client.event.EventClientWorldLoaded;
import com.smithsgaming.transportmanager.client.world.WorldClientManager;
import com.smithsgaming.transportmanager.client.world.chunk.ChunkClient;
import com.smithsgaming.transportmanager.main.saveable.WorldSaveHandler;
import com.smithsgaming.transportmanager.main.world.World;
import com.smithsgaming.transportmanager.main.world.chunk.Chunk;
import com.smithsgaming.transportmanager.util.network.Side;
import io.netty.channel.Channel;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

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
