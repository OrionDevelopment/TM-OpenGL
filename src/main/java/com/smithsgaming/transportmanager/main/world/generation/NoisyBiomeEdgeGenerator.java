package com.smithsgaming.transportmanager.main.world.generation;

import com.hoten.delaunay.geom.Point;
import com.hoten.delaunay.voronoi.Center;
import com.hoten.delaunay.voronoi.Edge;
import com.smithsgaming.transportmanager.util.concurrent.ProgressionNotifierThread;
import com.smithsgaming.transportmanager.util.math.MathUtil;
import com.smithsgaming.transportmanager.util.math.Vector2i;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by marcf on 11/17/2016.
 */
public class NoisyBiomeEdgeGenerator implements IWorldGenFeature{

    public static final NoisyBiomeEdgeGenerator instance = new NoisyBiomeEdgeGenerator();

    private static final float NOISY_LINE_TRADEOFF = 0.5f;
    private static final float MAX_RELAXATION = 0.8f;
    private static final float MIN_RELAXATION = 0.2f;
    private static final float MAX_DIVISION = 0.4f;
    private static final float MIN_DIVISION = -0.4f;

    @Override
    public void generate(WorldGenerationData worldGenerationData, ProgressionNotifierThread runningThread) {
        for (Center center : worldGenerationData.getWorldGraph().centers) {
            for (Edge edge: center.borders) {
                if (edge.d0 != null && edge.d1 != null && edge.v0 != null && edge.v1 != null && !worldGenerationData.getNoisyEdgeMap().containsKey(new Pair<>(edge.v0, edge.v1))) {
                    Point t = MathUtil.interpolateCoordinate(edge.v0.loc, edge.d0.loc, NOISY_LINE_TRADEOFF);
                    Point q = MathUtil.interpolateCoordinate(edge.v0.loc, edge.d1.loc, NOISY_LINE_TRADEOFF);
                    Point r = MathUtil.interpolateCoordinate(edge.v1.loc, edge.d0.loc, NOISY_LINE_TRADEOFF);
                    Point s = MathUtil.interpolateCoordinate(edge.v1.loc, edge.d1.loc, NOISY_LINE_TRADEOFF);

                    int minLength = 10;
                    if (edge.d0.biome != edge.d1.biome) minLength = 3;
                    if (edge.d0.ocean && edge.d1.ocean) minLength = 100;
                    if (edge.d0.coast || edge.d1.coast) minLength = 2;
                    if (edge.river > 0) minLength = 2;

                    ArrayList<Vector2i> path = buildNoisyLineSegments(worldGenerationData, edge.v0.loc, t, edge.midpoint, q, minLength);
                    path.addAll(buildNoisyLineSegments(worldGenerationData, edge.midpoint, s, edge.v1.loc, r, minLength));

                    worldGenerationData.getNoisyEdgeMap().put(new Pair<>(edge.v0, edge.v1), path);
                }
            }
        }
    }

    private ArrayList<Vector2i> buildNoisyLineSegments(WorldGenerationData worldGenerationData, Point A, Point B, Point C, Point D, int minLength) {
        ArrayList<Vector2i> points = new ArrayList<>();

        points.add(new Vector2i((int)A.x,(int) A.y));

        subDivideQuad(worldGenerationData, A, B, C, D, minLength, points);

        points.add(new Vector2i((int) C.x, (int) C.y));

        return points;
    }

    private void subDivideQuad(WorldGenerationData worldGenerationData, Point A, Point B, Point C, Point D, int minLength, ArrayList<Vector2i> points) {
        if (Point.distance(A, C) <= minLength || Point.distance(B,D) <= minLength)
            return;

        float p = (worldGenerationData.getGenerationRandom().nextFloat() * (MAX_RELAXATION - MIN_RELAXATION)) + MIN_RELAXATION;
        float q = (worldGenerationData.getGenerationRandom().nextFloat() * (MAX_RELAXATION - MIN_RELAXATION)) + MIN_RELAXATION;

        Point E = MathUtil.interpolateCoordinate(A, D, p);
        Point F = MathUtil.interpolateCoordinate(B, C, p);
        Point G = MathUtil.interpolateCoordinate(A, B, q);
        Point I = MathUtil.interpolateCoordinate(D, C, q);

        Point H = MathUtil.interpolateCoordinate(E, F, q);

        float s = (worldGenerationData.getGenerationRandom().nextFloat() * (MAX_DIVISION - MIN_DIVISION)) + MIN_DIVISION;
        float t = (worldGenerationData.getGenerationRandom().nextFloat() * (MAX_DIVISION - MIN_DIVISION)) + MIN_DIVISION;

        subDivideQuad(worldGenerationData, A, G, H, E, minLength, points);
        points.add(new Vector2i((int) H.x, (int) H.y));
        subDivideQuad(worldGenerationData, H, F, C, I, minLength, points);
    }
}
