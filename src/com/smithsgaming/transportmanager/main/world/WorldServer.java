

package com.smithsgaming.transportmanager.main.world;

import com.smithsgaming.transportmanager.main.world.chunk.Chunk;

/**
 * Created by marcf on 3/13/2016.
 */
public class WorldServer extends World {

    public WorldServer(WorldCoreData data, WorldType type) {
        super(data, type);
    }

    @Override
    protected void initializeChunkMap() {
        chunks = new Chunk[coreData.getWorldWidth() / Chunk.chunkSize + 1][coreData.getWorldLength() / Chunk.chunkSize + 1];
        for (int x = 0; x < chunks.length; x++) {
            for (int z = 0; z < chunks[0].length; z++) {
                chunks[x][z] = new Chunk(this, x, z);
            }
        }
    }

    @Override
    public void update() {
        synchronized (chunks) {
            for (int x = 0; x < chunks.length; x++) {
                for (int z = 0; z < chunks[0].length; z++) {
                    chunks[x][z].update();
                }
            }
        }
    }
}
