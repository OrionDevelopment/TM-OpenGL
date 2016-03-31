package com.smithsgaming.transportmanager.client.gui.components;

import com.smithsgaming.transportmanager.client.render.*;

/**
 * @Author Marc (Created on: 25.03.2016)
 */
public abstract class GuiComponentAbstract implements IRenderer {

    protected GuiComponentAbstract parent;

    public GuiComponentAbstract () {
        this.parent = this;
    }

    public GuiComponentAbstract (GuiComponentAbstract parent) {
        this.parent = parent;
    }

    public GuiComponentAbstract getParent () {
        return parent;
    }

    public abstract void loadTextures ();

    public abstract void loadGeometry ();

    public abstract void unLoadTextures ();

    public abstract void unLoadGeometry ();
}
