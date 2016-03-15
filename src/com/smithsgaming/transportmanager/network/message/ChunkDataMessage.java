package com.smithsgaming.transportmanager.network.message;

import com.smithsgaming.transportmanager.main.world.chunk.*;
import io.netty.channel.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class ChunkDataMessage extends NBTPayloadMessage {

    public ChunkDataMessage() {}

    public ChunkDataMessage(Chunk chunk)
    {
        super(chunk.getDataTag());
    }

    @Override
    public TMNetworkingMessage onReceived (Channel channel, NetworkingSide side) {
        System.out.println("Received data package on side: " + side);

        return null;
    }
}
