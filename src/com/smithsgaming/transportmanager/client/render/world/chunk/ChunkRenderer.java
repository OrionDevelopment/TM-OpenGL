package com.smithsgaming.transportmanager.client.render.world.chunk;

import com.smithsgaming.transportmanager.client.render.*;
import com.smithsgaming.transportmanager.client.world.chunk.*;
import com.smithsgaming.transportmanager.main.core.*;
import com.smithsgaming.transportmanager.main.world.tiles.*;
import com.smithsgaming.transportmanager.util.*;

import java.util.*;

/**
 * @Author Marc (Created on: 04.04.2016)
 */
public class ChunkRenderer implements IRenderer {

    private ChunkClient chunkClient;
    private HashMap<Tile, ChunkTileGeometry> geometryHashMap = new HashMap<>();

    public ChunkRenderer (ChunkClient chunkClient) {
        this.chunkClient = chunkClient;

        onRebuild();
    }

    public void onRebuild () {
        onDestroyed();

        for (Tile tile : TileRegistry.instance.getTiles()) {
            if (tile == null)
                continue;

            if (!tile.shouldUseDefaultRenderer())
                continue;

            ChunkTileGeometry geometry = new ChunkTileGeometry(chunkClient, tile);
            if (geometry.getVertexCount() > 0)
                geometryHashMap.put(tile, geometry);

        }

        geometryHashMap.values().forEach(OpenGLUtil::loadGeometryIntoGPU);
    }

    public void onDestroyed () {
        geometryHashMap.values().forEach(OpenGLUtil::deleteGeometry);
        geometryHashMap.clear();
    }

    @Override
    public void render () {
        geometryHashMap.values().forEach(ChunkTileGeometry::render);
    }
}
