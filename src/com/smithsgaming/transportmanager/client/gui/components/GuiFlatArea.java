package com.smithsgaming.transportmanager.client.gui.components;

import com.smithsgaming.transportmanager.client.graphics.Camera;
import com.smithsgaming.transportmanager.client.gui.GuiComponent;
import com.smithsgaming.transportmanager.client.registries.GeometryRegistry;
import com.smithsgaming.transportmanager.client.registries.ShaderRegistry;
import com.smithsgaming.transportmanager.client.render.core.geometry.Geometry;
import com.smithsgaming.transportmanager.util.OpenGLUtil;
import com.smithsgaming.transportmanager.util.math.graphical.GuiPlaneI;
import org.joml.Vector3f;

import java.awt.*;

/**
 * @Author Marc (Created on: 27.03.2016)
 */
public class GuiFlatArea extends GuiComponent {
    GuiPlaneI area;
    Color color;
    private Geometry geometryToRender;

    public GuiFlatArea(GuiComponent parent, GuiPlaneI area, Color color) {
        super(parent);
        this.geometryToRender = GeometryRegistry.getDefaultQuadGeometry();
        this.area = area;
        this.color = color;
    }

    @Override
    public GuiPlaneI getOccupiedArea() {
        return area;
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
        Camera.Gui.translateModel(new Vector3f(area.getCenterCoord().x, area.getCenterCoord().y, 0f));
        Camera.Gui.scaleModel(new Vector3f(area.getWidth(), area.getHeight(), 1f));
        Camera.Gui.pushMatrix();

        OpenGLUtil.drawGeometryWithShader(Camera.Gui, geometryToRender, ShaderRegistry.Shaders.guiColored);

        Camera.Gui.popMatrix();
        Camera.Gui.popMatrix();
    }
}
