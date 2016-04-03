package com.smithsgaming.transportmanager.client.gui;

import com.smithsgaming.transportmanager.client.TransportManagerClient;
import com.smithsgaming.transportmanager.client.gui.components.GuiComponentFlatArea;
import com.smithsgaming.transportmanager.client.gui.components.GuiComponentText;
import com.smithsgaming.transportmanager.client.registries.TextureRegistry;
import com.smithsgaming.transportmanager.util.math.Vector2i;
import com.smithsgaming.transportmanager.util.math.graphical.GuiPlaneI;
import org.lwjgl.util.Color;

/**
 * @Author Marc (Created on: 25.03.200.25f6)
 */
public class GuiGameLoading extends GuiAbstract {

    @Override
    protected void loadGui () {
        float horizontalHalf = TransportManagerClient.instance.getSettings().getCurrentScale().getHorizontalResolution() / 2f;
        float verticalHalf = TransportManagerClient.instance.getSettings().getCurrentScale().getVerticalResolution() / 2f;

        registerComponent(new GuiComponentFlatArea(this, new GuiPlaneI(new Vector2i((int) -horizontalHalf, (int) verticalHalf), new Vector2i((int) horizontalHalf, (int) -verticalHalf)), (Color) Color.WHITE));
        //registerComponent(new GuiComponentImage(this, "/textures/gui/background/logo.png", new GuiPlaneI(new Vector2i(-177, 62), new Vector2i(177, -62))));
        registerComponent(new GuiComponentText(this, TextureRegistry.Fonts.Courier, "Loading...", new Vector2i(0, 0), true, (Color) Color.BLUE));
    }

    @Override
    public GuiPlaneI getOccupiedArea() {
        //TODO: update the GUISystem to make it autocalculate its bounds.
        return null;
    }
}
