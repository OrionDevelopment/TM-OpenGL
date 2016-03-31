package com.smithsgaming.transportmanager.client.gui.components;

import com.smithsgaming.transportmanager.util.*;
import org.lwjgl.util.*;

/**
 * @Author Marc (Created on: 31.03.2016)
 */
public class GuiComponentButton extends GuiComponentAbstract {

    private GuiComponentAbstract componentOuterBackground;
    private GuiComponentAbstract componentInnerBackground;
    private GuiComponentAbstract componentContent;

    private GuiPlane area;
    private boolean centerAlign;

    public GuiComponentButton (GuiComponentAbstract parent, GuiComponentAbstract componentContent, GuiPlane area, boolean centerAlign) {
        super(parent);
        this.componentContent = componentContent;
        this.area = area;
        this.centerAlign = centerAlign;

        this.componentOuterBackground = new GuiComponentFlatArea(parent, new GuiPlane(0f, Math.abs(area.getCoord1X() - area.getCoord2X()), Math.abs(area.getCoord1X() - area.getCoord2X()), 0, Math.abs(area.getCoord1Y() - area.getCoord4Y()), Math.abs(area.getCoord1Y() - area.getCoord4Y()), 0, 0), (Color) Color.DKGREY);
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
