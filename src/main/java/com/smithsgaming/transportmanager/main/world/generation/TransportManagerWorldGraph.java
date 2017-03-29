package com.smithsgaming.transportmanager.main.world.generation;

import com.hoten.delaunay.voronoi.Center;
import com.hoten.delaunay.voronoi.Corner;
import com.hoten.delaunay.voronoi.Edge;
import com.hoten.delaunay.voronoi.VoronoiGraph;
import com.hoten.delaunay.voronoi.nodename.as3delaunay.Voronoi;
import com.smithsgaming.transportmanager.main.core.BiomeManager;
import com.smithsgaming.transportmanager.main.world.biome.Biome;
import com.smithsgaming.transportmanager.util.math.Vector2i;
import javafx.util.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Tim on 29/03/2016.
 */
public class TransportManagerWorldGraph extends VoronoiGraph {

    private final WorldGenerationData worldGenerationData;

    public TransportManagerWorldGraph(Voronoi v, int numLloydRelaxations, WorldGenerationData worldGenerationData) {
        super(v, numLloydRelaxations, worldGenerationData.getGenerationRandom());
        this.worldGenerationData = worldGenerationData;

        LAKE = Biome.LAKE.getGenerationColor();
        OCEAN = Biome.OCEAN.getGenerationColor();
        RIVER = Biome.RIVER.getGenerationColor();
        BEACH = Biome.BEACH.getGenerationColor();
    }

    @Override
    protected Enum getBiome (Center center) {
        return BiomeManager.instance.getBaseBiomeForCenter(center);
    }

    @Override
    protected Color getColor (Enum anEnum) {
        return ( (Biome) anEnum ).getGenerationColor();
    }

    @Override
    public BufferedImage createMap() {
        return super.createMap();
    }

    @Override
    public void paint(Graphics2D g, boolean drawBiomes, boolean drawRivers, boolean drawSites, boolean drawCorners, boolean drawDelaunay, boolean drawVoronoi) {
        int numSites = this.centers.size();
        Color[] defaultColors = null;

        if(!drawBiomes) {
            defaultColors = new Color[numSites];

            for(int pixelCenterGraphics = 0; pixelCenterGraphics < defaultColors.length; ++pixelCenterGraphics) {
                defaultColors[pixelCenterGraphics] = new Color(this.worldGenerationData.getGenerationRandom().nextInt(255), this.worldGenerationData.getGenerationRandom().nextInt(255), this.worldGenerationData.getGenerationRandom().nextInt(255));
            }
        }

        Graphics2D var13 = this.pixelCenterMap.createGraphics();
        Iterator var11 = this.centers.iterator();

        while(var11.hasNext()) {
            Center e = (Center)var11.next();
            this.drawPolygon(g, e, drawBiomes?this.getColor(e.biome):defaultColors[e.index]);
            this.drawPolygon(var13, e, new Color(e.index));
        }

        var11 = this.edges.iterator();

        while(var11.hasNext()) {
            Edge var14 = (Edge)var11.next();
            if(drawDelaunay) {
                g.setStroke(new BasicStroke(1.0F));
                g.setColor(Color.YELLOW);
                g.drawLine((int)var14.d0.loc.x, (int)var14.d0.loc.y, (int)var14.d1.loc.x, (int)var14.d1.loc.y);
            }

            if(drawRivers && var14.river > 0) {
                g.setStroke(new BasicStroke((float)(1 + (int)Math.sqrt((double)(var14.river * 2)))));
                g.setColor(this.RIVER);
                g.drawLine((int)var14.v0.loc.x, (int)var14.v0.loc.y, (int)var14.v1.loc.x, (int)var14.v1.loc.y);
            }
        }

        if(drawSites) {
            g.setColor(Color.BLACK);
            this.centers.stream().forEach((s) -> {
                g.fillOval((int)(s.loc.x - 2.0D), (int)(s.loc.y - 2.0D), 4, 4);
            });
        }

        if(drawCorners) {
            g.setColor(Color.WHITE);
            this.corners.stream().forEach((c) -> {
                g.fillOval((int)(c.loc.x - 2.0D), (int)(c.loc.y - 2.0D), 4, 4);
            });
        }

        g.setColor(Color.WHITE);
        g.drawRect((int)this.bounds.x, (int)this.bounds.y, (int)this.bounds.width, (int)this.bounds.height);
    }

    private void drawPolygon(Graphics2D g, Center c, Color color) {
        g.setColor(color);
        Corner edgeCorner1 = null;
        Corner edgeCorner2 = null;
        c.area = 0.0D;
        Iterator x = c.neighbors.iterator();

        while(x.hasNext()) {
            Center y = (Center)x.next();
            Edge e = this.edgeWithCenters(c, y);
            if(e.v0 != null) {
                Corner cornerWithOneAdjacent = e.v0.border?e.v0:e.v1;
                if(cornerWithOneAdjacent.border) {
                    if(edgeCorner1 == null) {
                        edgeCorner1 = cornerWithOneAdjacent;
                    } else {
                        edgeCorner2 = cornerWithOneAdjacent;
                    }
                }

                this.drawTriangle(g, e.v0, e.v1, c);
                c.area += Math.abs(c.loc.x * (e.v0.loc.y - e.v1.loc.y) + e.v0.loc.x * (e.v1.loc.y - c.loc.y) + e.v1.loc.x * (c.loc.y - e.v0.loc.y)) / 2.0D;
            }
        }

        if(edgeCorner2 != null) {
            if(this.closeEnough(edgeCorner1.loc.x, edgeCorner2.loc.x, 1.0D)) {
                this.drawTriangle(g, edgeCorner1, edgeCorner2, c);
            } else {
                int[] x1 = new int[4];
                int[] y1 = new int[4];
                x1[0] = (int)c.loc.x;
                y1[0] = (int)c.loc.y;
                x1[1] = (int)edgeCorner1.loc.x;
                y1[1] = (int)edgeCorner1.loc.y;
                x1[2] = (int)(!this.closeEnough(edgeCorner1.loc.x, this.bounds.x, 1.0D) && !this.closeEnough(edgeCorner2.loc.x, this.bounds.x, 0.5D)?this.bounds.right:this.bounds.x);
                y1[2] = (int)(!this.closeEnough(edgeCorner1.loc.y, this.bounds.y, 1.0D) && !this.closeEnough(edgeCorner2.loc.y, this.bounds.y, 0.5D)?this.bounds.bottom:this.bounds.y);
                x1[3] = (int)edgeCorner2.loc.x;
                y1[3] = (int)edgeCorner2.loc.y;
                g.fillPolygon(x1, y1, 4);
                c.area += 0.0D;
            }
        }
    }

    private Edge edgeWithCenters(Center c1, Center c2) {
        Iterator var3 = c1.borders.iterator();

        Edge e;
        do {
            if(!var3.hasNext()) {
                return null;
            }

            e = (Edge)var3.next();
        } while(e.d0 != c2 && e.d1 != c2);

        return e;
    }

    private void drawTriangle(Graphics2D g, Corner c1, Corner c2, Center center) {
        if (worldGenerationData.getNoisyEdgeMap().containsKey(new Pair<>(c1, c2)))  {
            drawDynamicTriangle(g, c1, c2, center);
            return;
        }

        int[] x = new int[3];
        int[] y = new int[3];
        x[0] = (int)center.loc.x;
        y[0] = (int)center.loc.y;
        x[1] = (int)c1.loc.x;
        y[1] = (int)c1.loc.y;
        x[2] = (int)c2.loc.x;
        y[2] = (int)c2.loc.y;
        g.fillPolygon(x, y, 3);
    }

    private boolean closeEnough(double d1, double d2, double diff)
    {
        return Math.abs(d1 - d2) <= diff;
    }

    private void drawDynamicTriangle(Graphics2D g, Corner c1, Corner c2, Center center) {
        ArrayList<Vector2i> points = worldGenerationData.getNoisyEdgeMap().get(new Pair<>(c1, c2));

        int[] x = new int[points.size() + 1];
        int[] y = new int[points.size() + 1];

        for (int i = 0; i < points.size(); i++) {
            x[i] = points.get(i).x;
            y[i] = points.get(i).y;
        }

        x[points.size()] = (int) center.loc.x;
        y[points.size()] = (int) center.loc.y;

        g.fillPolygon(x, y, points.size() + 1);
    }
}
