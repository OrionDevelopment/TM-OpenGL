

package com.smithsgaming.transportmanager.main.world;

import com.smithsgaming.transportmanager.main.world.chunk.*;
import com.smithsgaming.transportmanager.main.world.saveable.*;
import com.smithsgaming.transportmanager.main.world.tileentities.*;
import com.smithsgaming.transportmanager.main.world.tiles.*;

import static com.smithsgaming.transportmanager.main.world.tiles.TileRegistry.*;

/**
 * Created by marcf on 3/13/2016.
 */
public class World {

    public static final int WORLDHEIGHT = 256;
    public static int WORLDWIDTH;
    public static int WORLDLENGTH;

    private Chunk[][] chunks = new Chunk[WORLDWIDTH / Chunk.chunkSize + 1][WORLDLENGTH / Chunk.chunkSize + 1];
    private TileSaveHandler saveHandler;

    public World() {
        saveHandler = new TileSaveHandler(this);
    }

    public Chunk getChunkForPos(int chunkPosX, int chunkPosZ) {
        return chunks[chunkPosX][chunkPosZ];
    }

    public Tile getTileForPos(int tileWorldPosX, int tileWorldPosY, int tileWorldPosZ) {
        return getChunkForPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).getTileOnPos(tileWorldPosX % Chunk.chunkSize, tileWorldPosY, tileWorldPosZ % Chunk.chunkSize);
    }

    public TileEntity getTileEntityForPos(int tileWorldPosX, int tileWorldPosY, int tileWorldPosZ) {
        return getChunkForPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).getTileEntityOnPos(tileWorldPosX % Chunk.chunkSize, tileWorldPosY, tileWorldPosZ % Chunk.chunkSize);
    }

    public TileSaveHandler getSaveHandler() {
        return saveHandler;
    }

    protected void initializeChunkMap() {
        for (int x = 0; x < chunks.length; x++) {
            for (int z = 0; z < chunks[0].length; z++) {
                chunks[x][z] = new Chunk(this, x, z);
            }
        }
    }

    public void generate() {
        for (int x = 0; x < chunks.length; x++) {
            for (int z = 0; z < chunks[0].length; z++) {
                Chunk chunk = chunks[x][z];

                for (int cx = 0; cx < Chunk.chunkSize; cx++) {
                    for (int cy = 0; cy < WORLDHEIGHT; cy++) {
                        for (int cz = 0; cz < Chunk.chunkSize; cz++) {
                            chunk.setTileOnPos(TileRegistry.instance.getTileForIdentity(TileNames.OCEAN), cx, cy, cz);
                        }
                    }
                }
            }
        }
    }

}
