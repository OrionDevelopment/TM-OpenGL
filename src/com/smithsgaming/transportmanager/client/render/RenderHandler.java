package com.smithsgaming.transportmanager.client.render;

import com.smithsgaming.transportmanager.client.graphics.*;
import com.smithsgaming.transportmanager.client.gui.*;
import com.smithsgaming.transportmanager.client.render.world.*;

/**
 * @Author Marc (Created on: 06.03.2016)
 */
public class RenderHandler {
    static WorldRenderer worldRenderer = new WorldRenderer();
    static SkyBox skyBox = new SkyBox();
    static GuiController guiController = GuiController.instance;

    public static void doRender () {
        if (guiController.isGuiOpen()) {
            guiController.render();
            return;
        }

        skyBox.render();

        if (worldRenderer.getWorldClient() != null)
            worldRenderer.render();
    }

    public static WorldRenderer getWorldRenderer () {
        return worldRenderer;
    }

    public static GuiController getGuiController () {
        return guiController;
    }
}
