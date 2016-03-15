package com.smithsgaming.transportmanager.network.message;

import com.smithsgaming.transportmanager.client.world.*;
import com.smithsgaming.transportmanager.main.world.chunk.*;
import com.smithsgaming.transportmanager.util.*;
import io.netty.channel.*;
import javafx.util.*;

import java.io.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class ChunkDataMessage extends NBTPayloadMessage implements Serializable {

    private int x;
    private int z;

    public ChunkDataMessage() {}

    public ChunkDataMessage(Chunk chunk)
    {
        super(chunk.getDataTag());
        this.x = chunk.getChunkX();
        this.z = chunk.getChunkZ();
    }

    @Override
    public TMNetworkingMessage onReceived (Channel channel, Side side) {
        System.out.println("Received data package on side: " + side + " for chunk: " + x + "-" + z);

        if (side == Side.CLIENT) {
            WorldClientManager.instance.getSaveHandler().loadChunkForTagIntoWorld(WorldClientManager.instance.getWorld(), getPayLoad(), x, z);
            Pair<Integer, Integer> nextChunkPair = WorldClientManager.instance.getNextChunkToSyncForWorld();

            if (nextChunkPair == null)
                return null;

            return new RequestChunkDataMessage(nextChunkPair.getKey(), nextChunkPair.getValue());
        }

        return null;
    }
}
