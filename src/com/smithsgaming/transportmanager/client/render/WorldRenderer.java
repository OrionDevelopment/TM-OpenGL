package com.smithsgaming.transportmanager.client.render;

import com.smithsgaming.transportmanager.client.registries.*;
import com.smithsgaming.transportmanager.util.*;
import org.lwjgl.util.vector.*;

/**
 * Class used to render the world.
 *
 * @Author Marc (Created on: 06.03.2016)
 */
public class WorldRenderer implements IRenderer {
    /**
     * Method called by the RenderManager to process the rendering for this renderer.
     */
    @Override
    public void render () {
        OpenGLUtil.drawGeometryWithShader(GeometryRegistry.getDefaultQuadGeometry(), TextureRegistry.Textures.deepWater, new Matrix4f(), OpenGLUtil.Shaders.defaultShader);
        //OpenGLUtil.drawGeometryWithShader(GeometryRegistry.getDefaultTriangleGeometry(), TextureRegistry.Textures.deepWater, OpenGLUtil.Shaders.defaultShader);
    }
}
