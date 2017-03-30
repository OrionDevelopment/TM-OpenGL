/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.client.gui;

import com.smithsgaming.transportmanager.client.render.IRenderer;

import java.util.ArrayList;

/**
 *  ------ Class not Documented ------
 */
public abstract class GuiScreen extends GuiComponent implements IRenderer {

    ArrayList<GuiComponent> components = new ArrayList<>();

    protected abstract void loadGui ();

    protected void registerComponent (GuiComponent component) {
        components.add(component);
    }

    @Override
    public void loadTextures () {
        components.forEach(GuiComponent::loadTextures);
    }

    @Override
    public void loadGeometry () {
        components.forEach(GuiComponent::loadGeometry);
    }

    @Override
    public void unLoadTextures()
    {
        components.forEach(GuiComponent::unLoadTextures);
    }

    @Override
    public void unLoadGeometry () {
        components.forEach(GuiComponent::unLoadGeometry);
    }

    /**
     * Method called by the RenderManager to process the rendering for this renderer.
     */
    @Override
    public void render () {
        components.forEach(GuiComponent::render);
    }

    public ArrayList<GuiComponent> getComponents() {
        return components;
    }
}
