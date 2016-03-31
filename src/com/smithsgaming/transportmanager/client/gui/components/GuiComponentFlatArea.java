package com.smithsgaming.transportmanager.client.gui.components;

import com.smithsgaming.transportmanager.client.graphics.*;
import com.smithsgaming.transportmanager.client.registries.*;
import com.smithsgaming.transportmanager.util.*;
import org.lwjgl.util.*;
import org.lwjgl.util.vector.*;

/**
 * @Author Marc (Created on: 27.03.2016)
 */
public class GuiComponentFlatArea extends GuiComponentAbstract {
    GuiPlane area;
    Color color;
    private GeometryRegistry.Geometry geometryToRender;

    public GuiComponentFlatArea (GuiComponentAbstract parent, GuiPlane area, Color color) {
        super(parent);
        this.geometryToRender = GeometryRegistry.getDefaultQuadGeometry();
        this.area = area;
        this.color = color;
    }

    @Override
    public void loadTextures () {
    }

    @Override
    public void loadGeometry () {
    }

    @Override
    public void unLoadTextures () {
    }

    @Override
    public void unLoadGeometry () {
    }

    /**
     * Method called by the RenderManager to process the rendering for this renderer.
     */
    @Override
    public void render () {
        Camera.Gui.setActiveColor(color);

        Camera.Gui.pushMatrix();
        Camera.Gui.translateModel(new Vector3f(( area.getCoord1X() + area.getCoord2X() ) / 2f, ( area.getCoord1Y() + area.getCoord4Y() ) / 2f, 0));
        Camera.Gui.scaleModel(new Vector3f(Math.abs(area.getCoord1X() - area.getCoord2X()), Math.abs(area.getCoord1Y() - area.getCoord4Y()), 1f));
        Camera.Gui.pushMatrix();

        OpenGLUtil.drawGeometryWithShader(Camera.Gui, geometryToRender, ShaderRegistry.Shaders.guiColored);

        Camera.Gui.popMatrix();
        Camera.Gui.popMatrix();
    }
}
