package com.smithsgaming.transportmanager.network.message;

import com.google.common.base.*;
import com.smithsgaming.transportmanager.client.*;
import com.smithsgaming.transportmanager.client.event.*;
import com.smithsgaming.transportmanager.client.world.*;
import com.smithsgaming.transportmanager.client.world.chunk.*;
import com.smithsgaming.transportmanager.main.world.chunk.*;
import com.smithsgaming.transportmanager.main.world.saveable.*;
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

    public ChunkDataMessage() {}

    public ChunkDataMessage(Chunk chunk)
    {
        this.chunk = chunk;
        this.x = chunk.getChunkX();
        this.z = chunk.getChunkZ();
    }

    @Override
    public TMNetworkingMessage onReceived (Channel channel, Side side) {
        System.out.println("Received data package on side: " + side + " for chunk: " + x + "-" + z);

        if (side == Side.CLIENT) {
            Stopwatch stopwatch = Stopwatch.createStarted();

            WorldSaveHandler.instance.setChunkInWorldClient(WorldClientManager.instance.getWorld(), new ChunkClient(chunk));

            System.out.println("   ==> Finished loading in: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms.");
            stopwatch.reset();
            stopwatch.start();

            WorldClientManager.instance.getWorld().setChunkLoadedState(x, z, true);

            Pair<Integer, Integer> nextChunkPair = WorldClientManager.instance.getNextChunkToSyncForWorld();

            if (nextChunkPair == null) {
                System.out.println("   ==> Finished WorldServer download!");
                TransportManagerClient.instance.registerEvent(new WorldClientLoadedEvent());

                return null;
            }

            System.out.println("   ==> Retrieved new CoordPair in: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms.");

            return new RequestChunkDataMessage(nextChunkPair.getKey(), nextChunkPair.getValue());
        }

        return null;
    }
}
