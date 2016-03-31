package com.smithsgaming.transportmanager.client.event;

import com.smithsgaming.transportmanager.client.*;
import com.smithsgaming.transportmanager.client.render.*;
import com.smithsgaming.transportmanager.client.world.*;
import com.smithsgaming.transportmanager.main.world.*;
import com.smithsgaming.transportmanager.util.*;
import com.smithsgaming.transportmanager.util.event.*;

/**
 * @Author Marc (Created on: 16.03.2016)
 */
public class EventClientWorldLoaded extends TMEvent {

    @Override
    public void processEvent (Side side) {
        RenderHandler.getWorldRenderer().setWorldClient(WorldClientManager.instance.getWorld(World.WorldType.OVERGROUND));
        TransportManagerClient.getDisplay().registerEvent(new EventClientGuiClose());
    }
}
