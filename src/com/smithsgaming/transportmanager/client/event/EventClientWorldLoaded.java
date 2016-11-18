package com.smithsgaming.transportmanager.client.event;

import com.smithsgaming.transportmanager.client.TransportManagerClient;
import com.smithsgaming.transportmanager.client.render.RenderHandler;
import com.smithsgaming.transportmanager.client.world.WorldClientManager;
import com.smithsgaming.transportmanager.main.world.World;
import com.smithsgaming.transportmanager.util.network.Side;

/**
 * @Author Marc (Created on: 16.03.2016)
 */
public class EventClientWorldLoaded extends TMClientEvent {

    @Override
    public void processEvent(Side side) {
        RenderHandler.getWorldRenderer().setWorldClient(WorldClientManager.instance.getWorld(World.WorldType.OVERGROUND));
        TransportManagerClient.getDisplay().registerEvent(new EventClientGuiClose());
    }
}
