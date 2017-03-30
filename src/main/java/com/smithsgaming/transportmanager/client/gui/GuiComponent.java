/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.client.gui;

import com.smithsgaming.transportmanager.client.render.IRenderer;
import com.smithsgaming.transportmanager.util.math.graphical.GuiPlaneI;

/**
 *  ------ Class not Documented ------
 */
public abstract class GuiComponent implements IRenderer {

    protected GuiComponent parent;

    public GuiComponent() {
        this.parent = this;
    }

    public GuiComponent(GuiComponent parent) {
        this.parent = parent;
    }

    public GuiComponent getParent () {
        return parent;
    }

    public abstract GuiPlaneI getOccupiedArea();

    public abstract void loadTextures ();

    public abstract void loadGeometry ();

    public abstract void unLoadTextures ();

    public abstract void unLoadGeometry ();
}
