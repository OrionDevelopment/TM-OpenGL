package com.smithsgaming.transportmanager.main.world.generation;

import com.hoten.delaunay.voronoi.nodename.as3delaunay.Voronoi;
import com.smithsgaming.transportmanager.main.world.biome.BaseBiome;
import com.smithsgaming.transportmanager.main.world.tiles.Tile;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by Tim on 29/03/2016.
 */
public class WorldGenerationData {

    private final int WORLD_WIDTH;
    private final int WORLD_HEIGHT;
    private final long worldSeed;

    private Random generationRandom;

    private Voronoi voronoiGenerator;
    private TransportManagerWorldGraph worldGraph;
    private BufferedImage pregenImage;

    private final int WATER_HEIGHT;
    private final int MAX_TILE_HEIGHT;

    private int[][] heightMap;
    private BaseBiome[][] biomeMap;
    private Tile[][] tileMap;

    public WorldGenerationData(long worldSeed, int worldWidth, int worldHeight, int waterHeight, int maxTileHeight) {
        this.worldSeed = worldSeed;

        this.generationRandom = new Random(worldSeed);
        this.voronoiGenerator = new Voronoi((worldWidth / 20) * (worldHeight / 20), worldWidth, worldHeight, this.getGenerationRandom(), null);

        this.WORLD_WIDTH = worldWidth;
        this.WORLD_HEIGHT = worldHeight;
        this.WATER_HEIGHT = waterHeight;
        this.MAX_TILE_HEIGHT = maxTileHeight;

        this.heightMap = new int[worldWidth][worldHeight];
        this.biomeMap = new BaseBiome[worldWidth][worldHeight];
        this.tileMap = new Tile[worldWidth][worldHeight];
    }

    public int getWorldWidth() {
        return WORLD_WIDTH;
    }

    public int getWorldHeight() {
        return WORLD_HEIGHT;
    }

    public long getWorldSeed() {
        return worldSeed;
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

    public int getHeightAtPos(int x, int y) {
        if (x < 0 || y < 0 || x >= WORLD_WIDTH || y >= WORLD_HEIGHT) {
            return Integer.MAX_VALUE;
        }
        return heightMap[x][y];
    }

    public void setHeightMap(int[][] heightMap) {
        this.heightMap = heightMap;
    }

    public BaseBiome[][] getBiomeMap() {
        return biomeMap;
    }

    public BaseBiome getBiomeAtPos(int x, int y) {
        if (x < 0 || y < 0 || x >= WORLD_WIDTH || y >= WORLD_HEIGHT) {
            return null;
        }
        return biomeMap[x][y];
    }

    public void setBiomeMap(BaseBiome[][] biomeMap) {
        this.biomeMap = biomeMap;
    }

    public Tile[][] getTileMap() {
        return tileMap;
    }

    public void setTileMap(Tile[][] tileMap) {
        this.tileMap = tileMap;
    }

    public Random getGenerationRandom() {
        return generationRandom;
    }

    public Voronoi getVoronoiGenerator() {
        return voronoiGenerator;
    }

}
