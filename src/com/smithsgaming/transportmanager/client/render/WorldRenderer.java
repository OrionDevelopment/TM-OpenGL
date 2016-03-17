package com.smithsgaming.transportmanager.client.render;

import com.smithsgaming.transportmanager.client.graphics.*;
import com.smithsgaming.transportmanager.client.registries.*;
import com.smithsgaming.transportmanager.client.world.*;
import com.smithsgaming.transportmanager.client.world.chunk.*;
import com.smithsgaming.transportmanager.util.*;
import org.lwjgl.util.vector.*;

/**
 * Class used to render the world.
 *
 * @Author Marc (Created on: 06.03.2016)
 */
public class WorldRenderer implements IRenderer {

    WorldClient worldClient;

    /**
     * Method called by the RenderManager to process the rendering for this renderer.
     */
    @Override
    public void render () {
        if (worldClient == null)
            OpenGLUtil.drawGeometryWithShader(GeometryRegistry.getDefaultQuadGeometry(), TextureRegistry.Textures.deepWater, new Matrix4f(), OpenGLUtil.Shaders.defaultShader);

    }

    private boolean isChunkInView (ChunkClient chunk) {
        return Camera.Player.getActiveFrustum().boxInFrustum(chunk.getBoundingBox()).ordinal() > 0 && Camera.Player.isPointInViewDistance(chunk.getChunkCenterForCamera(Camera.Player));
    }

    private void drawChunk (ChunkClient chunk) {
        if (!isChunkInView(chunk))
            return;


    }

    public WorldClient getWorldClient () {
        return worldClient;
    }

    public void setWorldClient (WorldClient worldClient) {
        this.worldClient = worldClient;
    }
}
