package com.smithsgaming.transportmanager.client.render.world;

import com.smithsgaming.transportmanager.client.graphics.Camera;
import com.smithsgaming.transportmanager.client.registries.GeometryRegistry;
import com.smithsgaming.transportmanager.client.registries.ShaderRegistry;
import com.smithsgaming.transportmanager.client.registries.TextureRegistry;
import com.smithsgaming.transportmanager.client.render.IRenderer;
import com.smithsgaming.transportmanager.client.world.WorldClient;
import com.smithsgaming.transportmanager.client.world.chunk.ChunkClient;
import com.smithsgaming.transportmanager.util.OpenGLUtil;

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
            OpenGLUtil.drawGeometryWithShaderAndTexture(Camera.Player, GeometryRegistry.getDefaultQuadGeometry(), TextureRegistry.Textures.Tiles.deepWater, ShaderRegistry.Shaders.textured);

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
