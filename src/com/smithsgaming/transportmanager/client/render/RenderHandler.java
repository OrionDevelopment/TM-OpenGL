package com.smithsgaming.transportmanager.client.render;

import com.smithsgaming.transportmanager.client.graphics.*;
import com.smithsgaming.transportmanager.client.gui.*;
import com.smithsgaming.transportmanager.client.render.test.*;
import com.smithsgaming.transportmanager.client.render.world.*;

/**
 * @Author Marc (Created on: 06.03.2016)
 */
public class RenderHandler {
    static WorldRenderer worldRenderer = new WorldRenderer();
    static SkyBox skyBox = new SkyBox();
    static GuiController guiController = GuiController.instance;

    static ResetIndexedRenderer testRenderer;
    static StitchedTextureRenderer stitchedTextureRenderer;

    public static void doRender () {
        if (guiController.isGuiOpen()) {
            guiController.render();
            return;
        }

        skyBox.render();

        if (true) {
            if (testRenderer == null)
                testRenderer = new ResetIndexedRenderer();

            testRenderer.render();

            if (stitchedTextureRenderer == null)
                stitchedTextureRenderer = new StitchedTextureRenderer();

            stitchedTextureRenderer.render();

            return;
        }

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
