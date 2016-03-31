package com.smithsgaming.transportmanager.client.gui;

import com.smithsgaming.transportmanager.client.*;
import com.smithsgaming.transportmanager.client.gui.components.*;
import com.smithsgaming.transportmanager.client.registries.*;
import com.smithsgaming.transportmanager.util.*;
import org.lwjgl.util.*;

/**
 * @Author Marc (Created on: 25.03.200.25f6)
 */
public class GuiGameLoading extends GuiAbstract {

    @Override
    protected void loadGui () {
        float horizontalHalf = TransportManagerClient.instance.getSettings().getCurrentScale().getHorizontalResolution() / 2f;
        float verticalHalf = TransportManagerClient.instance.getSettings().getCurrentScale().getVerticalResolution() / 2f;

        registerComponent(new GuiComponentFlatArea(this, new GuiPlane(-horizontalHalf, horizontalHalf, horizontalHalf, -horizontalHalf, verticalHalf, verticalHalf, -verticalHalf, -verticalHalf), (Color) Color.WHITE));
        registerComponent(new GuiComponentImage(this, "/textures/gui/background/logo.png", 0, 0, 354, 123, true, true));
        registerComponent(new GuiComponentText(this, TextureRegistry.Fonts.Courier, "Loading...", 0f, -200f, true, (Color) Color.BLUE));
    }
}
