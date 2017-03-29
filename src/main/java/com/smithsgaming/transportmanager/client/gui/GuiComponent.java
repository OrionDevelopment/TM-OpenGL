package com.smithsgaming.transportmanager.client.gui;

import com.smithsgaming.transportmanager.client.render.IRenderer;
import com.smithsgaming.transportmanager.util.math.graphical.GuiPlaneI;

/**
 * @Author Marc (Created on: 25.03.2016)
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
