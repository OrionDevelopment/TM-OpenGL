/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.client.gui.screens;

import com.smithsgaming.transportmanager.client.TransportManagerClient;
import com.smithsgaming.transportmanager.client.gui.GuiScreen;
import com.smithsgaming.transportmanager.client.gui.components.GuiFlatArea;
import com.smithsgaming.transportmanager.client.gui.components.GuiImage;
import com.smithsgaming.transportmanager.client.gui.components.GuiText;
import com.smithsgaming.transportmanager.client.registries.TextureRegistry;
import com.smithsgaming.transportmanager.util.math.Vector2i;
import com.smithsgaming.transportmanager.util.math.graphical.GuiPlaneI;

import java.awt.*;

/**
 *  ------ Class not Documented ------
 */
public class GuiGameLoading extends GuiScreen {

    @Override
    protected void loadGui () {
        float horizontalHalf = TransportManagerClient.instance.getSettings().getCurrentScale().getHorizontalResolution() / 2f;
        float verticalHalf = TransportManagerClient.instance.getSettings().getCurrentScale().getVerticalResolution() / 2f;

        registerComponent(new GuiFlatArea(this, new GuiPlaneI(new Vector2i((int) -horizontalHalf, (int) verticalHalf), new Vector2i((int) horizontalHalf, (int) -verticalHalf)),
                                           Color.WHITE));
        registerComponent(new GuiImage(this, "/textures/gui/background/logo.png", new GuiPlaneI(new Vector2i(-177, 62), new Vector2i(177, -62))));
        registerComponent(new GuiText(this, TextureRegistry.Fonts.Courier, "Loading...", new Vector2i(0, -80), true, Color.BLUE));
    }

    @Override
    public GuiPlaneI getOccupiedArea() {
        //TODO: update the GUISystem to make it autocalculate its bounds.
        return null;
    }
}
