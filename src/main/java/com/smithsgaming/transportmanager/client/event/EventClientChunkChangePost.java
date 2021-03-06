package com.smithsgaming.transportmanager.client.event;

import com.smithsgaming.transportmanager.client.event.util.ChunkChangeType;
import com.smithsgaming.transportmanager.main.world.World;
import com.smithsgaming.transportmanager.network.client.TMNetworkingClient;
import com.smithsgaming.transportmanager.network.message.RequestChunkDataMessage;
import com.smithsgaming.transportmanager.util.network.Side;

import java.io.Serializable;

/**
 * Created by Tim on 11/04/2016.
 */
public class EventClientChunkChangePost extends TMClientEvent implements Serializable {

    private World.WorldType type;
    private ChunkChangeType changeType;
    private int chunkX, chunkY;

    public EventClientChunkChangePost(World.WorldType type, ChunkChangeType changeType, int chunkX, int chunkY) {
        this.type = type;
        this.changeType = changeType;
        this.chunkX = chunkX;
        this.chunkY = chunkY;
    }

    @Override
    public void processEvent(Side side) {
        TMNetworkingClient.sendMessage(new RequestChunkDataMessage(chunkX, chunkY, type));
    }
}
