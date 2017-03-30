/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.client.render;

/**
 * Main interface used for rendering Objects into the current GL Context.
 *
 *  ------ Class not Documented ------
 */
public interface IRenderer {

    /**
     * Method called by the RenderManager to process the rendering for this renderer.
     */
    void render ();
}
