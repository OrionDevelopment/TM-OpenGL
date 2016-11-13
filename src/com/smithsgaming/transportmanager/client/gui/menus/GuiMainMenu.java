package com.smithsgaming.transportmanager.client.gui.menus;

import com.smithsgaming.transportmanager.client.TransportManagerClient;
import com.smithsgaming.transportmanager.client.gui.GuiScreen;
import com.smithsgaming.transportmanager.client.gui.components.GuiButton;
import com.smithsgaming.transportmanager.client.gui.components.GuiFlatArea;
import com.smithsgaming.transportmanager.client.gui.components.GuiImage;
import com.smithsgaming.transportmanager.client.gui.components.GuiText;
import com.smithsgaming.transportmanager.client.gui.input.IButtonListener;
import com.smithsgaming.transportmanager.client.registries.TextureRegistry;
import com.smithsgaming.transportmanager.util.math.Vector2i;
import com.smithsgaming.transportmanager.util.math.graphical.GuiPlaneI;

import java.awt.*;

/**
 * Created by marcf on 4/3/2016.
 */
public class GuiMainMenu extends GuiScreen implements IButtonListener {
    @Override
    protected void loadGui() {
        float horizontalHalf = TransportManagerClient.instance.getSettings().getCurrentScale().getHorizontalResolution() / 2f;
        float verticalHalf = TransportManagerClient.instance.getSettings().getCurrentScale().getVerticalResolution() / 2f;

        registerComponent(new GuiFlatArea(this, new GuiPlaneI(new Vector2i((int) -horizontalHalf, (int) verticalHalf), new Vector2i((int) horizontalHalf, (int) -verticalHalf)), (Color) Color.WHITE));
        registerComponent(new GuiImage(this, "/textures/gui/background/Menu.png", new GuiPlaneI(new Vector2i((int) -horizontalHalf, (int) verticalHalf), new Vector2i((int) horizontalHalf, (int) -verticalHalf))));
        registerComponent(new GuiButton(0, this, new GuiText(this, TextureRegistry.Fonts.Courier, "Create new Game", new Vector2i(0, 0), true, (Color) Color.WHITE), new GuiPlaneI(new Vector2i((int) (horizontalHalf - 20 - TextureRegistry.Fonts.Courier.getOccupiedAreaForText("Create new Game").getWidth()), (int) (verticalHalf - 10)), new Vector2i((int) (horizontalHalf - 10), (int) (verticalHalf - 20 - TextureRegistry.Fonts.Courier.getLineHeight()))), true, this));
    }

    @Override
    public GuiPlaneI getOccupiedArea() {
        return new GuiPlaneI();
    }

    @Override
    public void buttonPressed(int buttonID, GuiButton button) {
        if (buttonID == 0) {
            TransportManagerClient.clientLogger.info("Creating new World.");
        }
    }
}
