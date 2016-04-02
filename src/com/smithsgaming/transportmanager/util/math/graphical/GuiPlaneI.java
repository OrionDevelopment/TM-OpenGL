package com.smithsgaming.transportmanager.util.math.graphical;

import com.smithsgaming.transportmanager.util.math.Vector2i;

/**
 * @Author Marc (Created on: 25.03.2016)
 */
public class GuiPlaneI {

    private final Vector2i topLeftCoordinate;
    private final Vector2i lowerRightCoordinate;
    private final Vector2i topRightCoordinate;
    private final Vector2i lowerLeftCoordinate;

    private final int width;
    private final int height;

    private final Vector2i centerCoord;

    public GuiPlaneI(Vector2i topLeftCoordinate, Vector2i lowerRightCoordinate) {
        if (topLeftCoordinate.x > lowerRightCoordinate.x || topLeftCoordinate.y < lowerRightCoordinate.y)
            throw new IllegalArgumentException("Coordinate order is not correct. Top Left first!");

        this.topLeftCoordinate = topLeftCoordinate;
        this.lowerRightCoordinate = lowerRightCoordinate;

        this.lowerLeftCoordinate = new Vector2i(topLeftCoordinate.x, lowerRightCoordinate.y);
        this.topRightCoordinate = new Vector2i(lowerRightCoordinate.x, topLeftCoordinate.y);

        this.width = lowerRightCoordinate.x - topLeftCoordinate.x;
        this.height = topLeftCoordinate.y - lowerRightCoordinate.y;

        this.centerCoord = new Vector2i(topLeftCoordinate.x + (width / 2), topLeftCoordinate.y + (height / 2));
    }

    public GuiPlaneI(GuiPlaneI toCopy)
    {
        this.topLeftCoordinate = toCopy.getTopLeftCoordinate().copy();
        this.lowerRightCoordinate = toCopy.getLowerRightCoordinate().copy();

        this.lowerLeftCoordinate = toCopy.getLowerLeftCoordinate().copy();
        this.topRightCoordinate = toCopy.getTopRightCoordinate().copy();

        this.width = toCopy.getWidth();
        this.height = toCopy.getHeight();

        this.centerCoord = toCopy.getCenterCoord().copy();
    }

    public Vector2i getTopLeftCoordinate() {
        return topLeftCoordinate;
    }

    public Vector2i getLowerRightCoordinate() {
        return lowerRightCoordinate;
    }

    public Vector2i getTopRightCoordinate() {
        return topRightCoordinate;
    }

    public Vector2i getLowerLeftCoordinate() {
        return lowerLeftCoordinate;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector2i getCenterCoord() {
        return centerCoord;
    }
}
