package com.smithsgaming.transportmanager.client.gui.components;

import com.smithsgaming.transportmanager.client.graphics.*;
import com.smithsgaming.transportmanager.client.registries.*;
import com.smithsgaming.transportmanager.util.*;
import org.lwjgl.util.vector.*;

/**
 * @Author Marc (Created on: 25.03.2016)
 */
public class ComponentImage extends GuiComponent {

    private String resourcePath;

    private TextureRegistry.Texture textureToRender;
    private GeometryRegistry.Geometry geometryToRender;

    public ComponentImage (GuiComponent parent, String resourcePath, GuiPlane area) {
        super(parent);
        this.resourcePath = resourcePath;
        this.geometryToRender = GeometryRegistry.QuadGeometry.constructFromPlaneForTexture(area, new GuiPlane(0, 1, 1, 0, 1, 1, 0, 0));
    }

    @Override
    public void loadTextures () {
        textureToRender = ResourceUtil.loadPNGTexture(resourcePath);
        OpenGLUtil.loadTextureIntoGPU(textureToRender);
    }

    @Override
    public void loadGeometry () {
        OpenGLUtil.loadGeometryIntoGPU(geometryToRender);
    }

    @Override
    public void unLoadTextures () {
        OpenGLUtil.destroyTexture(textureToRender);
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
        OpenGLUtil.drawGeometryWithShader(Camera.GUI, geometryToRender, textureToRender, new Matrix4f(), ShaderRegistry.Shaders.guiTextured);
    }
}
