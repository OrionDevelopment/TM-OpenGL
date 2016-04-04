package com.smithsgaming.transportmanager.client.event;

import com.smithsgaming.transportmanager.client.gui.*;
import com.smithsgaming.transportmanager.client.render.*;
import com.smithsgaming.transportmanager.util.*;
import com.smithsgaming.transportmanager.util.event.*;

/**
 * @Author Marc (Created on: 25.03.2016)
 */
public class EventClientGuiOpen extends TMEvent {

    GuiScreen guiToOpen;

    public EventClientGuiOpen (GuiScreen guiToOpen) {
        this.guiToOpen = guiToOpen;
    }

    @Override
    public void processEvent (Side side) {
        if (side == Side.SERVER)
            return;

        RenderHandler.getGuiController().openGui(guiToOpen);
    }
}
