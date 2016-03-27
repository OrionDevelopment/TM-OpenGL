package com.smithsgaming.transportmanager.client.gui.components;

import com.smithsgaming.transportmanager.client.graphics.*;
import com.smithsgaming.transportmanager.client.registries.*;
import com.smithsgaming.transportmanager.util.*;
import org.lwjgl.util.*;

/**
 * @Author Marc (Created on: 27.03.2016)
 */
public class ComponentFlatArea extends GuiComponent {
    private GeometryRegistry.Geometry geometryToRender;

    public ComponentFlatArea (GuiComponent parent, GuiPlane area, Color color) {
        this(parent, area, color, color, color, color);
    }

    public ComponentFlatArea (GuiComponent parent, GuiPlane area, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        super(parent);
        this.geometryToRender = GeometryRegistry.QuadGeometry.constructFromPlaneForColored(area, topLeftColor, topRightColor, bottomRightColor, bottomLeftColor);
    }

    @Override
    public void loadTextures () {
    }

    @Override
    public void loadGeometry () {
        OpenGLUtil.loadGeometryIntoGPU(geometryToRender);
    }

    @Override
    public void unLoadTextures () {
    }

    @Override
    public void unLoadGeometry () {
        OpenGLUtil.deleteGeometry(geometryToRender);
    }

    /**
     * Method called by the RenderManager to process the rendering for this renderer.
     */
    @Override
    public void render () {
        OpenGLUtil.drawGeometryWithShader(Camera.Gui, geometryToRender, ShaderRegistry.Shaders.guiColored);
    }
}
