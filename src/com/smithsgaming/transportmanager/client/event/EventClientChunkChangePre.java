package com.smithsgaming.transportmanager.client.event;

import com.smithsgaming.transportmanager.main.world.World;
import com.smithsgaming.transportmanager.client.event.util.ChunkChangeType;
import com.smithsgaming.transportmanager.util.network.Side;

import java.io.Serializable;

/**
 * Created by Tim on 11/04/2016.
 */
public class EventClientChunkChangePre extends TMClientEvent implements Serializable {

    private World.WorldType type;
    private ChunkChangeType changeType;
    private int chunkX, chunkY;

    public EventClientChunkChangePre(World.WorldType type, ChunkChangeType changeType, int chunkX, int chunkY) {
        this.type = type;
        this.changeType = changeType;
        this.chunkX = chunkX;
        this.chunkY = chunkY;
    }

    @Override
    public void processEvent(Side side) {
    }
}
