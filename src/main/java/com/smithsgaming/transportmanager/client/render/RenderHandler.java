/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.client.render;

import com.smithsgaming.transportmanager.client.graphics.SkyBox;
import com.smithsgaming.transportmanager.client.gui.GuiController;
import com.smithsgaming.transportmanager.client.render.test.ResetIndexedRenderer;
import com.smithsgaming.transportmanager.client.render.test.StitchedTextureRenderer;
import com.smithsgaming.transportmanager.client.render.world.WorldRenderer;

/**
 *  ------ Class not Documented ------
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

        if (false) {
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
