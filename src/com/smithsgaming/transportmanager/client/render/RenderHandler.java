package com.smithsgaming.transportmanager.client.render;

import com.smithsgaming.transportmanager.client.graphics.*;

/**
 * @Author Marc (Created on: 06.03.2016)
 */
public class RenderHandler {
    static WorldRenderer worldRenderer = new WorldRenderer();
    static SkyBox skyBox = new SkyBox();

    public static void doRender () {
        //worldRenderer.render();
        skyBox.render();
    }

    public static WorldRenderer getWorldRenderer () {
        return worldRenderer;
    }
}
