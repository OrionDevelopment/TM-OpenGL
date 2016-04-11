package com.smithsgaming.transportmanager.client.event;

import com.smithsgaming.transportmanager.main.world.World;
import com.smithsgaming.transportmanager.util.Side;
import com.smithsgaming.transportmanager.util.event.TMEvent;

/**
 * Created by Tim on 11/04/2016.
 */
public class EventClientChunkChangePre extends TMEvent {

    private World.WorldType type;
    private int chunkX, chunkY;

    public EventClientChunkChangePre(World.WorldType type, int chunkX, int chunkY) {
        this.type = type;
        this.chunkX = chunkX;
        this.chunkY = chunkY;
    }

    @Override
    public void processEvent(Side side) {
    }
}
