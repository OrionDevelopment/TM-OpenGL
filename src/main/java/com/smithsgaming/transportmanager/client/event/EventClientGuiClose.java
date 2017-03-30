/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.client.event;

import com.smithsgaming.transportmanager.client.render.RenderHandler;
import com.smithsgaming.transportmanager.util.network.Side;

/**
 *  ------ Class not Documented ------
 */
public class EventClientGuiClose extends TMClientEvent {

    @Override
    public void processEvent (Side side) {
        if (side == Side.SERVER)
            return;

        RenderHandler.getGuiController().closeGui();
    }
}
