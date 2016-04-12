package com.smithsgaming.transportmanager.main.world.generation;

import com.hoten.delaunay.voronoi.nodename.as3delaunay.*;
import com.smithsgaming.transportmanager.client.TransportManagerClient;
import com.smithsgaming.transportmanager.client.event.EventClientChunkChangePost;
import com.smithsgaming.transportmanager.client.event.EventClientChunkChangePre;
import com.smithsgaming.transportmanager.main.world.*;
import com.smithsgaming.transportmanager.main.world.biome.*;
import com.smithsgaming.transportmanager.main.world.chunk.*;
import com.smithsgaming.transportmanager.main.tileentity.*;
import com.smithsgaming.transportmanager.main.world.tiles.*;
import com.smithsgaming.transportmanager.network.message.ClientEventProcessMessage;
import com.smithsgaming.transportmanager.network.server.TMNetworkingServer;

import java.awt.image.*;
import java.io.*;
import java.util.*;

/**
 * Created by Tim on 29/03/2016.
 */
public class WorldGenerationData implements Serializable {

    private final int WORLD_WIDTH;
    private final int WORLD_HEIGHT;
    private final long WORLD_SEED;
    private final int WATER_HEIGHT;
    private final int MAX_TILE_HEIGHT;

    public transient World world;
    private transient Random generationRandom;

    private transient Voronoi voronoiGenerator;
    private transient TransportManagerWorldGraph worldGraph;
    private transient BufferedImage pregenImage;

    private transient int[][] heightMap;
    private transient BaseBiome[][] biomeMap;
    private transient Chunk[][] chunks;

    public WorldGenerationData(long worldSeed, int worldWidth, int worldHeight, int waterHeight, int maxTileHeight) {
        this.WORLD_SEED = worldSeed;

        this.generationRandom = new Random(worldSeed);
        this.voronoiGenerator = new Voronoi((worldWidth / 20) * (worldHeight / 20), worldWidth, worldHeight, this.getGenerationRandom(), null);

        this.WORLD_WIDTH = worldWidth;
        this.WORLD_HEIGHT = worldHeight;
        this.WATER_HEIGHT = waterHeight;
        this.MAX_TILE_HEIGHT = maxTileHeight;

        this.heightMap = new int[worldWidth][worldHeight];
        this.biomeMap = new BaseBiome[worldWidth][worldHeight];
        this.chunks = new Chunk[worldWidth / Chunk.chunkSize + 1][worldHeight / Chunk.chunkSize + 1];
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public int getWorldWidth() {
        return WORLD_WIDTH;
    }

    public int getWorldHeight() {
        return WORLD_HEIGHT;
    }

    public long getWorldSeed() {
        return WORLD_SEED;
    }

    public TransportManagerWorldGraph getWorldGraph() {
        return worldGraph;
    }

    public void setTransportManagerWorldGraph(TransportManagerWorldGraph worldGraph) {
        this.worldGraph = worldGraph;
    }

    public BufferedImage getPregenImage() {
        return pregenImage;
    }

    public void setPregenImage(BufferedImage pregenImage) {
        this.pregenImage = pregenImage;
    }

    public void loadFromDisk() {
        worldGraph = new TransportManagerWorldGraph(this.getVoronoiGenerator(), 2, this.getGenerationRandom());
        this.pregenImage = worldGraph.createMap();
    }

    public int getWaterHeight() {
        return WATER_HEIGHT;
    }

    public int getMaxTileHeight() {
        return MAX_TILE_HEIGHT;
    }

    public int[][] getHeightMap() {
        return heightMap;
    }

    public void setHeightMap(int[][] heightMap) {
        this.heightMap = heightMap;
    }

    public int getHeightAtPos(int x, int y) {
        if (x < 0 || y < 0 || x >= WORLD_WIDTH || y >= WORLD_HEIGHT) {
            return Integer.MAX_VALUE;
        }
        return heightMap[x][y];
    }

    public BaseBiome[][] getBiomeMap() {
        return biomeMap;
    }

    public void setBiomeMap(BaseBiome[][] biomeMap) {
        this.biomeMap = biomeMap;
    }

    public BaseBiome getBiomeAtPos(int x, int y) {
        if (x < 0 || y < 0 || x >= WORLD_WIDTH || y >= WORLD_HEIGHT) {
            return null;
        }
        return biomeMap[x][y];
    }

    public Chunk[][] getChunkMap() {
        return chunks;
    }

    public void setChunks(Chunk[][] chunks) {
        this.chunks = chunks;
    }

    public Random getGenerationRandom() {
        return generationRandom;
    }

    public Voronoi getVoronoiGenerator() {
        return voronoiGenerator;
    }
}
