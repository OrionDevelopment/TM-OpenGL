/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.client.render.world;

import com.google.common.base.Stopwatch;
import com.smithsgaming.transportmanager.client.graphics.Camera;
import com.smithsgaming.transportmanager.client.graphics.Display;
import com.smithsgaming.transportmanager.client.registries.GeometryRegistry;
import com.smithsgaming.transportmanager.client.registries.ShaderRegistry;
import com.smithsgaming.transportmanager.client.registries.TextureRegistry;
import com.smithsgaming.transportmanager.client.render.IRenderer;
import com.smithsgaming.transportmanager.client.render.core.geometry.Geometry;
import com.smithsgaming.transportmanager.client.render.world.chunk.ChunkRenderer;
import com.smithsgaming.transportmanager.client.world.WorldClient;
import com.smithsgaming.transportmanager.client.world.chunk.ChunkClient;
import com.smithsgaming.transportmanager.main.world.chunk.Chunk;
import com.smithsgaming.transportmanager.util.OpenGLUtil;
import com.smithsgaming.transportmanager.util.math.graphical.GuiPlaneF;
import org.joml.Vector2f;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Class used to render the world.
 *
 *  ------ Class not Documented ------
 */
public class WorldRenderer implements IRenderer {

    WorldClient worldClient;
    HashMap<ChunkClient, ChunkRenderer> rendererHashMap = new HashMap<>();

    Geometry testGeometry;

    /**
     * Method called by the RenderManager to process the rendering for this renderer.
     */
    @Override
    public void render () {
        if (worldClient == null)
            OpenGLUtil.drawGeometryWithShaderAndTexture(Camera.Player, GeometryRegistry.getDefaultQuadGeometry(), TextureRegistry.Textures.Tiles.deepWater, ShaderRegistry.Shaders.textured);

        if (false) {
            if (testGeometry == null) {
                testGeometry = GeometryRegistry.QuadGeometry.constructFromPlaneForTextureOnZ(new GuiPlaneF(new Vector2f(0, 0), new Vector2f(1, -1)),
                  TextureRegistry.Textures.Tiles.grass.getArea());
                OpenGLUtil.loadGeometryIntoGPU(testGeometry);
            }
            OpenGLUtil.drawGeometryWithShaderAndTexture(Camera.Player, testGeometry, TextureRegistry.Textures.Tiles.grass, ShaderRegistry.Shaders.textured);

            return;
        }

        OpenGLUtil.activateTextureInGPU(TextureRegistry.instance.getTextureForName("Stitched-0"));

        for (int x = 0; x < worldClient.getCoreData().getWorldWidth() / Chunk.chunkSize + 1; x++) {
            for (int z = 0; z < worldClient.getCoreData().getWorldHeight() / Chunk.chunkSize + 1; z++) {
                drawChunk(worldClient.getChunkAtPos(x, z));
            }
        }
    }

    private void drawChunk (ChunkClient chunk) {


        if (!isChunkInView(chunk)) {
            if (rendererHashMap.containsKey(chunk)) {
                ChunkRenderer oldGeometry = rendererHashMap.remove(chunk);

                Display.displayLogger.debug("Destroying geometry ChunkCache " + chunk.getChunkX() + "-" + chunk.getChunkZ());

                if (oldGeometry != null) {
                    oldGeometry.onDestroyed();
                }
            }

            return;
        }

        if (!rendererHashMap.containsKey(chunk)) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            rendererHashMap.put(chunk, new ChunkRenderer(chunk));
            Display.displayLogger.debug("Finished generating ChunkCache " + chunk.getChunkX() + "-" + chunk.getChunkZ() + " in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " Ms.");
            stopwatch.stop();
        }

        rendererHashMap.get(chunk).render();
    }

    private boolean isChunkInView(ChunkClient chunk)
    {
        return Camera.Player.getActiveFrustum().boxInFrustum(chunk.getBoundingBox()).ordinal() > 0;
    }

    public WorldClient getWorldClient () {
        return worldClient;
    }

    public void setWorldClient (WorldClient worldClient) {
        this.worldClient = worldClient;
    }
}
