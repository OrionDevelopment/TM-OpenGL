package com.smithsgaming.transportmanager.client.gui.components;

import com.smithsgaming.transportmanager.util.math.graphical.GuiPlaneI;
import org.lwjgl.util.*;

/**
 * @Author Marc (Created on: 31.03.2016)
 */
public class GuiComponentButton extends GuiComponentAbstract {

    private GuiComponentAbstract componentOuterBackground;
    private GuiComponentAbstract componentInnerBackground;
    private GuiComponentAbstract componentContent;

    private GuiPlaneI area;
    private boolean centerAlign;

    public GuiComponentButton (GuiComponentAbstract parent, GuiComponentAbstract componentContent, GuiPlaneI area, boolean centerAlign) {
        super(parent);
        this.componentContent = componentContent;
        this.area = area;
        this.centerAlign = centerAlign;

        this.componentOuterBackground = new GuiComponentFlatArea(parent, new GuiPlaneI(area), (Color) Color.DKGREY);
    }

    @Override
    public void loadTextures () {
        componentContent.loadTextures();
    }

    @Override
    public void loadGeometry () {
        componentOuterBackground.loadGeometry();
        componentInnerBackground.loadGeometry();
        componentContent.loadGeometry();
    }

    @Override
    public void unLoadTextures () {
        componentContent.unLoadTextures();
    }

    @Override
    public void unLoadGeometry () {
        componentOuterBackground.unLoadGeometry();
        componentInnerBackground.unLoadGeometry();
        componentContent.unLoadGeometry();
    }

    /**
     * Method called by the RenderManager to process the rendering for this renderer.
     */
    @Override
    public void render () {

    }
}
