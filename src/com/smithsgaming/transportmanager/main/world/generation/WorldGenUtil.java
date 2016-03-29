package com.smithsgaming.transportmanager.main.world.generation;

import com.hoten.delaunay.voronoi.Center;
import com.smithsgaming.transportmanager.util.math.Vector2i;

/**
 * Created by Tim on 29/03/2016.
 */
public class WorldGenUtil {

    public static boolean isPointInPolygon(Center polygonCenter, Vector2i point) {
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = polygonCenter.corners.size() - 1; i < polygonCenter.corners.size(); j = i++) {
            if ((polygonCenter.corners.get(i).loc.y > point.y) != (polygonCenter.corners.get(j).loc.y > point.y) &&
                    (point.x < (polygonCenter.corners.get(j).loc.x - polygonCenter.corners.get(i).loc.x) * (point.y - polygonCenter.corners.get(i).loc.y) / (polygonCenter.corners.get(j).loc.y - polygonCenter.corners.get(i).loc.y) + polygonCenter.corners.get(i).loc.x)) {
                result = !result;
            }
        }
        return result;
    }

}
