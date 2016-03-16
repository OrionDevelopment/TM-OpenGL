package com.smithsgaming.transportmanager.client.render;

/**
 * @Author Marc (Created on: 06.03.2016)
 */
public class RenderHandler {
    static WorldRenderer worldRenderer = new WorldRenderer();

    public static void doRender () {
        worldRenderer.render();
    }

    public static WorldRenderer getWorldRenderer () {
        return worldRenderer;
    }
}
