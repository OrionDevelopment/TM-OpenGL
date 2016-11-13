package com.smithsgaming.transportmanager.client.render.test;

import com.smithsgaming.transportmanager.client.graphics.*;
import com.smithsgaming.transportmanager.client.registries.*;
import com.smithsgaming.transportmanager.client.render.*;
import com.smithsgaming.transportmanager.util.*;
import org.joml.Vector3f;

/**
 * @Author Marc (Created on: 11.04.2016)
 */
public class StitchedTextureRenderer implements IRenderer {
    @Override
    public void render () {
        Camera.Gui.scaleModel(new Vector3f(100, 100, 1));
        OpenGLUtil.drawGeometryWithShaderAndTexture(Camera.Gui, GeometryRegistry.getDefaultQuadGeometry(), TextureRegistry.instance.getTextureForName("Stitched-0"), ShaderRegistry.Shaders.textured);
        Camera.Gui.popMatrix();
    }

}
