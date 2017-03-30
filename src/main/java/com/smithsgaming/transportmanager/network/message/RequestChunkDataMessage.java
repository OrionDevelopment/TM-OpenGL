/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.network.message;

import com.smithsgaming.transportmanager.main.core.WorldManager;
import com.smithsgaming.transportmanager.main.world.World;
import com.smithsgaming.transportmanager.util.network.Side;
import io.netty.channel.Channel;

/**
 *  ------ Class not Documented ------
 */
public class RequestChunkDataMessage extends TMNetworkingMessage {
    private int x;
    private int y;
    private World.WorldType type;

    public RequestChunkDataMessage (int x, int y, World.WorldType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public RequestChunkDataMessage () {
    }

    @Override
    public TMNetworkingMessage onReceived (Channel channel, Side side, MessageContext context) {
        if (side == Side.SERVER) {
            if (type == World.WorldType.OVERGROUND) {
                return new ChunkDataMessage(WorldManager.instance.getOvergroundWorld().getChunkAtPos(x, y));
            } else if (type == World.WorldType.UNDERGROUND) {
                return new ChunkDataMessage(WorldManager.instance.getUndergroundWorld().getChunkAtPos(x, y));
            }
        }
        return null;
    }
}
