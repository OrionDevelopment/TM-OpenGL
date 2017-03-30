/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.client.graphics;

import com.smithsgaming.transportmanager.client.registries.GeometryRegistry;
import com.smithsgaming.transportmanager.client.registries.ShaderRegistry;
import com.smithsgaming.transportmanager.client.registries.TextureRegistry;
import com.smithsgaming.transportmanager.client.render.IRenderer;
import com.smithsgaming.transportmanager.client.render.core.TexturedVertex;
import com.smithsgaming.transportmanager.client.render.core.VertexInformation;
import com.smithsgaming.transportmanager.client.render.core.geometry.Geometry;
import com.smithsgaming.transportmanager.util.OpenGLUtil;

/**
 *  ------ Class not Documented ------
 */
public class SkyBox implements IRenderer {

    private static final int SkyBoxSize = 2000;

    private Geometry oceanGeometry;

    public SkyBox () {
        TexturedVertex[] oceanGeometryVextexes = new TexturedVertex[4];

        oceanGeometryVextexes[0] = new TexturedVertex().setRGB(1f, 1f, 1f).setST(0, SkyBoxSize).setXYZ(-( SkyBoxSize / 2f ), 0f, ( SkyBoxSize / 2f ));
        oceanGeometryVextexes[2] = new TexturedVertex().setRGB(1f, 1f, 1f).setST(SkyBoxSize, SkyBoxSize).setXYZ(( SkyBoxSize / 2f ), 0f, ( SkyBoxSize / 2f ));
        oceanGeometryVextexes[3] = new TexturedVertex().setRGB(1f, 1f, 1f).setST(SkyBoxSize, 0).setXYZ(( SkyBoxSize / 2f ), 0f, -( SkyBoxSize / 2f ));
        oceanGeometryVextexes[1] = new TexturedVertex().setRGB(1f, 1f, 1f).setST(0, 0).setXYZ(-( SkyBoxSize / 2f ), 0f, -( SkyBoxSize / 2f ));

        oceanGeometry = new Geometry(GeometryRegistry.GeometryType.QUAD, oceanGeometryVextexes, VertexInformation.DEFAULT);

        GeometryRegistry.instance.registerNewGeometry(oceanGeometry);
    }

    /**
     * Method called by the RenderManager to process the rendering for this renderer.
     */
    @Override
    public void render () {
        OpenGLUtil.drawGeometryWithShaderAndTexture(Camera.Player, oceanGeometry, TextureRegistry.Textures.SkyBox.skyBoxOcean, ShaderRegistry.Shaders.textured);
    }
}
