package com.smithsgaming.transportmanager.client.event;

import com.smithsgaming.transportmanager.client.gui.GuiScreen;
import com.smithsgaming.transportmanager.client.render.RenderHandler;
import com.smithsgaming.transportmanager.util.Side;

/**
 * @Author Marc (Created on: 25.03.2016)
 */
public class EventClientGuiOpen extends TMClientEvent {

    GuiScreen guiToOpen;

    public EventClientGuiOpen(GuiScreen guiToOpen) {
        this.guiToOpen = guiToOpen;
    }

    @Override
    public void processEvent(Side side) {
        if (side == Side.SERVER)
            return;

        RenderHandler.getGuiController().openGui(guiToOpen);
    }
}