package com.smithsgaming.transportmanager.client.gui.components;

import com.smithsgaming.transportmanager.client.render.*;

/**
 * @Author Marc (Created on: 25.03.2016)
 */
public abstract class GuiComponent implements IRenderer {

    protected GuiComponent parent;

    public GuiComponent () {
        this.parent = this;
    }

    public GuiComponent (GuiComponent parent) {
        this.parent = parent;
    }

    public GuiComponent getParent () {
        return parent;
    }

    public abstract void loadTextures ();

    public abstract void loadGeometry ();

    public abstract void unLoadTextures ();

    public abstract void unLoadGeometry ();
}
