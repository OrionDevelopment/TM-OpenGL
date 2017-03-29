/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

/*
 * Copyright (c)  2015-2017, SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.client.graphics;

import org.joml.Vector3f;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Copyright 2008 SmithsGaming Inc.
 */
class AABoxTest {

    private static final int CONSTANT_POINTS_IN_BOX = 8;
    private static final float CONSTANT_FLOAT_COMPARISON_EPSILON = 0.0001f;

    private AABoxTestData posData;
    private AABoxTestData negData;

    @BeforeEach
    void setUp() {
        posData = new AABoxTestData(false);
        negData = new AABoxTestData(true);
    }

    @Test
    void getMinCorner() {
        assertTrue(posData.getBox().getMinCorner().equals(posData.getMinCorner()), "The minimal corner of the positive Box was not constructed properly.");
        assertTrue(negData.getBox().getMinCorner().equals(negData.getMinCorner()), "The minimal corner of the negative Box was not constructed properly.");
    }

    @Test
    void getX() {
        assertTrue(posData.getBox().getX() == posData.getWidth(), "The width (X-Dimension) of the positive Box was not constructed properly.");
        assertTrue(negData.getBox().getX() == negData.getWidth(), "The width (X-Dimension) of the negative Box was not constructed properly.");
    }

    @Test
    void getY() {
        assertTrue(posData.getBox().getY() == posData.getHeight(), "The height (Y-Dimension) of the positive Box was not constructed properly.");
        assertTrue(negData.getBox().getY() == negData.getHeight(), "The height (Y-Dimension) of the negative Box was not constructed properly.");
    }

    @Test
    void getZ() {
        assertTrue(posData.getBox().getZ() == posData.getDepth(), "The depth (Z-Dimension) of the positive Box was not constructed properly.");
        assertTrue(negData.getBox().getZ() == negData.getDepth(), "The depth (Z-Dimension) of the negative Box was not constructed properly.");
    }

    @Test
    void getMaxCorner() {
        assertTrue(posData.getBox().getMaxCorner().equals(posData.getMaxCorner()), "The maximal corner of the positive Box was not constructed properly.");
        assertTrue(negData.getBox().getMaxCorner().equals(negData.getMaxCorner()), "The maximal corner of the negative Box was not constructed properly.");
    }

    @Test
    void getPoints() {
        for (int i = 0; i < CONSTANT_POINTS_IN_BOX; i++) {
            assertTrue(posData.getBox().getPoints()[i].equals(posData.getPoints()[i]), "Point #" + i + " was not properly calculated in the positive Box.");
            assertTrue(negData.getBox().getPoints()[i].equals(negData.getPoints()[i]), "Point #" + i + " was not properly calculated in the negative Box.");
        }
    }

    private static class AABoxTestData {
        private AABox box;
        private Vector3f minCorner;
        private Vector3f maxCorner;
        private float width;
        private float height;
        private float depth;
        private Vector3f[] points = new Vector3f[8];

        private AABoxTestData(boolean generateNegDimensions) {
            Random random = new Random();
            minCorner = new Vector3f(getRandomFloat(random, generateNegDimensions), getRandomFloat(random, generateNegDimensions), getRandomFloat(random, generateNegDimensions));

            width = getRandomFloat(random, generateNegDimensions);
            height = getRandomFloat(random, generateNegDimensions);
            depth = getRandomFloat(random, generateNegDimensions);

            box = new AABox(new Vector3f(minCorner), width, height, depth);

            if (generateNegDimensions) {
                width *= -1;
                height *= -1;
                depth *= -1;

                minCorner.x -= width;
                minCorner.y -= height;
                minCorner.z -= depth;
            }

            int i = 0;
            points[i++] = new Vector3f(minCorner);
            points[i++] = minCorner.add(width, 0, 0, new Vector3f());
            points[i++] = minCorner.add(width, height, 0, new Vector3f());
            points[i++] = minCorner.add(0, height, 0, new Vector3f());
            points[i++] = minCorner.add(0, 0, depth, new Vector3f());
            points[i++] = minCorner.add(width, 0, depth, new Vector3f());
            points[i++] = minCorner.add(width, height, depth, new Vector3f());
            points[i] = minCorner.add(0, height, depth, new Vector3f());

            maxCorner = minCorner.add(width, height, depth, new Vector3f());
        }

        private float getRandomFloat(Random random, boolean generateNegDimensions) {
            return (generateNegDimensions ? -1 : 1) * Math.abs(random.nextInt() + random.nextFloat());
        }

        AABox getBox() {
            return box;
        }

        Vector3f getMinCorner() {
            return minCorner;
        }

        Vector3f getMaxCorner() {
            return maxCorner;
        }

        float getWidth() {
            return width;
        }

        float getHeight() {
            return height;
        }

        float getDepth() {
            return depth;
        }

        Vector3f[] getPoints() {
            return points;
        }
    }
}