package com.smithsgaming.transportmanager.main.world.generation;

import com.hoten.delaunay.voronoi.Center;
import com.hoten.delaunay.voronoi.Corner;
import com.hoten.delaunay.voronoi.Edge;
import com.smithsgaming.transportmanager.util.math.Vector2i;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tim on 29/03/2016.
 */
public class WorldGenUtil {

    public static Center getPolygonForPoint(WorldGenerationData worldGenerationData, Vector2i point) {
        for(Center center : worldGenerationData.getWorldGraph().centers) {
            if (isPointInPolygon(center, point))
                return center;
        }

        return null;
    }

    public static boolean isPointInPolygon (Center polygonCenter, Vector2i point) {
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = polygonCenter.corners.size() - 1; i < polygonCenter.corners.size(); j = i++) {
            if (( polygonCenter.corners.get(i).loc.y > point.y ) != ( polygonCenter.corners.get(j).loc.y > point.y ) &&
                    ( point.x < ( polygonCenter.corners.get(j).loc.x - polygonCenter.corners.get(i).loc.x ) * ( point.y - polygonCenter.corners.get(i).loc.y ) / ( polygonCenter.corners.get(j).loc.y - polygonCenter.corners.get(i).loc.y ) + polygonCenter.corners.get(i).loc.x )) {
                result = !result;
            }
        }
        return result;
    }

    public static List<Edge> getClosestEdgeToPoint(Center polygon, Vector2i point, double delta) {
        List<Edge> closestEdge = new ArrayList<>();

        for(Edge edge : polygon.borders) {
            double distance = getDistanceToLineFromPoints(edge.v0, edge.v1, point);

            if (distance <= delta) {
                closestEdge.add(edge);
            }
        }

        return closestEdge;
    }

    public static double getDistanceToLineFromPoints(Corner p1, Corner p2, Vector2i target) {
        if (p1 == null || p2 == null || target == null || p1.loc == null || p2.loc ==null)
            return Double.MAX_VALUE;

        return (Math.abs((p2.loc.y - p1.loc.y)*target.x - (p2.loc.x - p1.loc.x)*target.y + (p2.loc.x*p1.loc.y) - (p2.loc.y*p1.loc.x))) / (Math.sqrt(Math.pow((p2.loc.y - p1.loc.y), 2) + Math.pow((p2.loc.x - p1.loc.x), 2)));
    }


}
