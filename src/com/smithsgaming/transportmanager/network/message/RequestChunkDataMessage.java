package com.smithsgaming.transportmanager.network.message;

import com.smithsgaming.transportmanager.main.world.*;
import com.smithsgaming.transportmanager.util.*;
import io.netty.channel.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class RequestChunkDataMessage extends TMNetworkingMessage {
    private int x;
    private int y;

    public RequestChunkDataMessage (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public RequestChunkDataMessage () {
    }

    @Override
    public TMNetworkingMessage onReceived (Channel channel, Side side) {
        if (side == Side.SERVER) {
            return new ChunkDataMessage(WorldManager.instance.getLoadedWorld().getChunkAtPos(x, y));
        }

        return null;
    }
}
