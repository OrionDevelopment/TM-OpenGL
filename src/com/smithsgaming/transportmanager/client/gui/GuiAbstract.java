package com.smithsgaming.transportmanager.client.gui;

import com.smithsgaming.transportmanager.client.gui.components.*;
import com.smithsgaming.transportmanager.client.render.*;

import java.util.*;

/**
 * @Author Marc (Created on: 25.03.2016)
 */
public abstract class GuiAbstract extends GuiComponentAbstract implements IRenderer {

    ArrayList<GuiComponentAbstract> components = new ArrayList<>();

    protected abstract void loadGui ();

    protected void registerComponent (GuiComponentAbstract component) {
        components.add(component);
    }

    @Override
    public void loadTextures () {
        components.forEach(GuiComponentAbstract::loadTextures);
    }

    @Override
    public void unLoadTextures () {
        components.forEach(GuiComponentAbstract::unLoadTextures);
    }

    @Override
    public void loadGeometry () {
        components.forEach(GuiComponentAbstract::loadGeometry);
    }

    @Override
    public void unLoadGeometry () {
        components.forEach(GuiComponentAbstract::unLoadGeometry);
    }

    /**
     * Method called by the RenderManager to process the rendering for this renderer.
     */
    @Override
    public void render () {
        components.forEach(GuiComponentAbstract::render);
    }
}
