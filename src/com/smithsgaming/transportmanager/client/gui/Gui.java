package com.smithsgaming.transportmanager.client.gui;

import com.smithsgaming.transportmanager.client.gui.components.*;
import com.smithsgaming.transportmanager.client.render.*;

import java.util.*;

/**
 * @Author Marc (Created on: 25.03.2016)
 */
public abstract class Gui extends GuiComponent implements IRenderer {

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
    public void unLoadTextures () {
        components.forEach(GuiComponent::unLoadTextures);
    }

    @Override
    public void loadGeometry () {
        components.forEach(GuiComponent::loadGeometry);
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
}
