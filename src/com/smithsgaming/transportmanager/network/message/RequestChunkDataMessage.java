package com.smithsgaming.transportmanager.network.message;

import com.smithsgaming.transportmanager.main.world.*;
import com.smithsgaming.transportmanager.main.core.WorldManager;
import com.smithsgaming.transportmanager.util.*;
import io.netty.channel.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class RequestChunkDataMessage extends TMNetworkingMessage {
    private int x;
    private int y;
    private World.WorldType type;

    public RequestChunkDataMessage(int x, int y, World.WorldType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public RequestChunkDataMessage() {
    }

    @Override
    public TMNetworkingMessage onReceived(Channel channel, Side side) {
        if (side == Side.SERVER) {
            if (type == World.WorldType.OVERGROUND) {
                return new ChunkDataMessage(WorldManager.instance.getOvergroundWorld().getCoreData().getChunkAtPos(x, y));
            } else if (type == World.WorldType.UNDERGROUND) {
                return new ChunkDataMessage(WorldManager.instance.getUndergroundWorld().getCoreData().getChunkAtPos(x, y));
            }
        }
        return null;
    }
}
