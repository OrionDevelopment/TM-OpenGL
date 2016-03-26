package com.smithsgaming.transportmanager.client.graphics;

import com.smithsgaming.transportmanager.client.registries.*;
import com.smithsgaming.transportmanager.client.render.*;
import com.smithsgaming.transportmanager.util.*;
import org.lwjgl.util.vector.*;

/**
 * @Author Marc (Created on: 17.03.2016)
 */
public class SkyBox implements IRenderer {

    private static final int SkyBoxSize = 2000;

    private GeometryRegistry.Geometry oceanGeometry;

    public SkyBox () {
        TexturedVertex[] oceanGeometryVextexes = new TexturedVertex[4];

        oceanGeometryVextexes[0] = new TexturedVertex().setRGB(1f, 1f, 1f).setST(0, SkyBoxSize).setXYZ(-( SkyBoxSize / 2f ), 0f, ( SkyBoxSize / 2f ));
        oceanGeometryVextexes[2] = new TexturedVertex().setRGB(1f, 1f, 1f).setST(SkyBoxSize, SkyBoxSize).setXYZ(( SkyBoxSize / 2f ), 0f, ( SkyBoxSize / 2f ));
        oceanGeometryVextexes[3] = new TexturedVertex().setRGB(1f, 1f, 1f).setST(SkyBoxSize, 0).setXYZ(( SkyBoxSize / 2f ), 0f, -( SkyBoxSize / 2f ));
        oceanGeometryVextexes[1] = new TexturedVertex().setRGB(1f, 1f, 1f).setST(0, 0).setXYZ(-( SkyBoxSize / 2f ), 0f, -( SkyBoxSize / 2f ));

        oceanGeometry = new GeometryRegistry.Geometry(GeometryRegistry.GeometryType.QUAD, oceanGeometryVextexes);

        GeometryRegistry.instance.registerNewGeometry(oceanGeometry);
    }

    /**
     * Method called by the RenderManager to process the rendering for this renderer.
     */
    @Override
    public void render () {
        OpenGLUtil.drawGeometryWithShader(Camera.PLAYER, oceanGeometry, TextureRegistry.Textures.SkyBox.skyBoxOcean, new Matrix4f(), ShaderRegistry.Shaders.textured);
    }
}
