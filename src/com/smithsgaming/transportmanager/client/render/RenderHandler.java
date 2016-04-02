package com.smithsgaming.transportmanager.client.render;

import com.smithsgaming.transportmanager.client.graphics.SkyBox;
import com.smithsgaming.transportmanager.client.gui.GuiController;

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
    }

    public static WorldRenderer getWorldRenderer () {
        return worldRenderer;
    }

    public static GuiController getGuiController () {
        return guiController;
    }
}
