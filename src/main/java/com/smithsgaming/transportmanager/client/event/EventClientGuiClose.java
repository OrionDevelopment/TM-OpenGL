package com.smithsgaming.transportmanager.client.event;

import com.smithsgaming.transportmanager.client.render.RenderHandler;
import com.smithsgaming.transportmanager.util.network.Side;

/**
 * @Author Marc (Created on: 25.03.2016)
 */
public class EventClientGuiClose extends TMClientEvent {

    @Override
    public void processEvent (Side side) {
        if (side == Side.SERVER)
            return;

        RenderHandler.getGuiController().closeGui();
    }
}
