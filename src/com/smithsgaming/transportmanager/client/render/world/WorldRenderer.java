package com.smithsgaming.transportmanager.client.render.world;

import com.google.common.base.*;
import com.smithsgaming.transportmanager.client.graphics.*;
import com.smithsgaming.transportmanager.client.registries.*;
import com.smithsgaming.transportmanager.client.render.*;
import com.smithsgaming.transportmanager.client.render.world.chunk.*;
import com.smithsgaming.transportmanager.client.world.*;
import com.smithsgaming.transportmanager.client.world.chunk.*;
import com.smithsgaming.transportmanager.main.world.chunk.*;
import com.smithsgaming.transportmanager.util.*;

import java.util.*;
import java.util.concurrent.*;

/**
 * Class used to render the world.
 *
 * @Author Marc (Created on: 06.03.2016)
 */
public class WorldRenderer implements IRenderer {

    WorldClient worldClient;
    HashMap<ChunkClient, ChunkRenderer> rendererHashMap = new HashMap<>();

    /**
     * Method called by the RenderManager to process the rendering for this renderer.
     */
    @Override
    public void render () {
        if (worldClient == null)
            OpenGLUtil.drawGeometryWithShaderAndTexture(Camera.Player, GeometryRegistry.getDefaultQuadGeometry(), TextureRegistry.Textures.Tiles.deepWater, ShaderRegistry.Shaders.textured);

        for (int x = 0; x < worldClient.getCoreData().getWorldWidth() / Chunk.chunkSize + 1; x++) {
            for (int z = 0; z < worldClient.getCoreData().getWorldHeight() / Chunk.chunkSize + 1; z++) {
                drawChunk(worldClient.getChunkAtPos(x, z));
            }
        }
    }

    private boolean isChunkInView (ChunkClient chunk) {
        return Camera.Player.getActiveFrustum().boxInFrustum(chunk.getBoundingBox()).ordinal() > 0 && Camera.Player.isPointInViewDistance(chunk.getChunkCenterForCamera(Camera.Player));
    }

    private void drawChunk (ChunkClient chunk) {

        if (!isChunkInView(chunk)) {
            ChunkRenderer oldGeometry = rendererHashMap.remove(chunk);

            if (oldGeometry != null)
                oldGeometry.onDestroyed();

            return;
        }

        if (!rendererHashMap.containsKey(chunk)) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            rendererHashMap.put(chunk, new ChunkRenderer(chunk));
            System.out.println("Finished generating ChunkCache " + chunk.getChunkX() + "-" + chunk.getChunkZ() + " in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " Ms.");
            stopwatch.stop();
        }

        rendererHashMap.get(chunk).render();
    }

    public WorldClient getWorldClient () {
        return worldClient;
    }

    public void setWorldClient (WorldClient worldClient) {
        this.worldClient = worldClient;
    }
}
