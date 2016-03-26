

package com.smithsgaming.transportmanager.main.world;

import com.smithsgaming.transportmanager.main.world.chunk.*;
import com.smithsgaming.transportmanager.main.world.tileentities.*;
import com.smithsgaming.transportmanager.main.world.tiles.*;

import static com.smithsgaming.transportmanager.main.world.tiles.TileRegistry.*;

/**
 * Created by marcf on 3/13/2016.
 */
public class World {

    WorldCoreData coreData;

    private Chunk[][] chunks;

    public World(WorldCoreData data) {
        coreData = data;
        initializeChunkMap();
    }

    public Chunk getChunkForPos(int chunkPosX, int chunkPosZ) {
        return chunks[chunkPosX][chunkPosZ];
    }

    public void setChunkForPos(Chunk chunkForPos) {
        chunks[chunkForPos.getChunkX()][chunkForPos.getChunkZ()] = chunkForPos;
    }

    public Tile getTileForPos(int tileWorldPosX, int tileWorldPosY, int tileWorldPosZ) {
        return getChunkForPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).getTileOnPos(tileWorldPosX % Chunk.chunkSize, tileWorldPosY, tileWorldPosZ % Chunk.chunkSize);
    }

    public TileEntity getTileEntityForPos(int tileWorldPosX, int tileWorldPosY, int tileWorldPosZ) {
        return getChunkForPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).getTileEntityOnPos(tileWorldPosX % Chunk.chunkSize, tileWorldPosY, tileWorldPosZ % Chunk.chunkSize);
    }

    public void setTileOnPos(Tile tile, int tileWorldPosX, int tileWorldPosY, int tileWorldPosZ) {
        getChunkForPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).setTileOnPos(tile, tileWorldPosX % Chunk.chunkSize, tileWorldPosY, tileWorldPosZ % Chunk.chunkSize);
    }

    public void setTileEntityOnPos(TileEntity tileEntity, int tileWorldPosX, int tileWorldPosY, int tileWorldPosZ) {
        getChunkForPos(tileWorldPosX / Chunk.chunkSize, tileWorldPosZ / Chunk.chunkSize).setTileEntityOnPos(tileEntity, tileWorldPosX % Chunk.chunkSize, tileWorldPosY, tileWorldPosZ % Chunk.chunkSize);
    }

    public WorldCoreData getCoreData() {
        return coreData;
    }

    protected void initializeChunkMap() {
        chunks = new Chunk[coreData.getWorldWidth() / Chunk.chunkSize + 1][coreData.getWorldLength() / Chunk.chunkSize + 1];
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
                    for (int cy = 0; cy < coreData.getWorldHeight(); cy++) {
                        for (int cz = 0; cz < Chunk.chunkSize; cz++) {
                            chunk.setTileOnPos(TileRegistry.instance.getTileForIdentity(TileNames.OCEAN), cx, cy, cz);
                        }
                    }
                }
            }
        }
    }

}
