package com.smithsgaming.transportmanager.client.render;

/**
 * Main interface used for rendering Objects into the current GL Context.
 *
 * @Author Marc (Created on: 06.03.2016)
 */
public interface IRenderer {

    /**
     * Method called by the RenderManager to process the rendering for this renderer.
     */
    void render ();
}
