package com.smithsgaming.transportmanager.client.gui;

import com.smithsgaming.transportmanager.client.TransportManagerClient;
import com.smithsgaming.transportmanager.client.gui.components.GuiComponentButton;
import com.smithsgaming.transportmanager.client.gui.components.GuiComponentFlatArea;
import com.smithsgaming.transportmanager.client.gui.components.GuiComponentImage;
import com.smithsgaming.transportmanager.client.gui.components.GuiComponentText;
import com.smithsgaming.transportmanager.client.gui.components.inputhandling.IInputSAM;
import com.smithsgaming.transportmanager.client.registries.TextureRegistry;
import com.smithsgaming.transportmanager.util.math.Vector2i;
import com.smithsgaming.transportmanager.util.math.graphical.GuiPlaneI;
import org.lwjgl.util.Color;

/**
 * Created by marcf on 4/3/2016.
 */
public class GuiMainMenu extends GuiAbstract {
    @Override
    protected void loadGui() {
        float horizontalHalf = TransportManagerClient.instance.getSettings().getCurrentScale().getHorizontalResolution() / 2f;
        float verticalHalf = TransportManagerClient.instance.getSettings().getCurrentScale().getVerticalResolution() / 2f;

        registerComponent(new GuiComponentFlatArea(this, new GuiPlaneI(new Vector2i((int) -horizontalHalf, (int) verticalHalf), new Vector2i((int) horizontalHalf, (int) -verticalHalf)), (Color) Color.WHITE));
        registerComponent(new GuiComponentImage(this, "/textures/gui/background/Menu.png", new GuiPlaneI(new Vector2i((int) -horizontalHalf, (int) verticalHalf), new Vector2i((int) horizontalHalf, (int) -verticalHalf))));
        registerComponent(new GuiComponentButton(this, new GuiComponentText(this, TextureRegistry.Fonts.Courier, "Create new Game", new Vector2i(0, 0), true, (Color) Color.WHITE), new GuiPlaneI(new Vector2i((int) (horizontalHalf / 2), (int) (verticalHalf - 10)), new Vector2i((int) (horizontalHalf - 10), (int) (verticalHalf - 20 - TextureRegistry.Fonts.Courier.getLineHeight()))), true, new IInputSAM() {
            @Override
            public void invoke() {
                TransportManagerClient.clientLogger.info("Creating new World.");
            }
        }));
    }

    @Override
    public GuiPlaneI getOccupiedArea() {
        return new GuiPlaneI();
    }
}
