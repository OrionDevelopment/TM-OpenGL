package com.smithsgaming.transportmanager.util.math.graphical;

import com.smithsgaming.transportmanager.client.graphics.GuiScale;
import com.smithsgaming.transportmanager.client.input.MouseInputHandler;
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

        this.centerCoord = new Vector2i(topLeftCoordinate.x + (width / 2), topLeftCoordinate.y - (height / 2));
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

    public GuiPlaneI() {
        this(new Vector2i((int) -(GuiScale.FWVGA.getHorizontalResolution() / 2), (int) (GuiScale.FWVGA.getVerticalResolution() / 2)), new Vector2i((int) (GuiScale.FWVGA.getHorizontalResolution() / 2), (int) -(GuiScale.FWVGA.getVerticalResolution() / 2)));
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

    public boolean isPointInPlane(Vector2i point) {
        return (point.x >= topLeftCoordinate.x) && (point.x <= topRightCoordinate.x) && (point.y <= topLeftCoordinate.y) && (point.y >= lowerLeftCoordinate.y);
    }

    public boolean isMouseInPlane() {
        return isPointInPlane(new Vector2i((int) MouseInputHandler.instance.getMouseX(), (int) MouseInputHandler.instance.getMouseY()));
    }

    public GuiPlaneI getMovedVariant(Vector2i diff) {
        return new GuiPlaneI(topLeftCoordinate.add(diff), lowerRightCoordinate.add(diff));
    }

    /**
     * Returns a shrinked variant of this GuiPlaneI
     *
     * @param shrinkAmount The amount to move the sides.
     * @return A shrinked version of this GuiPlaneI
     */
    public GuiPlaneI getShrinkedVariant(int shrinkAmount) {
        return getShrinkedVariant(shrinkAmount, shrinkAmount, shrinkAmount, shrinkAmount);
    }

    /**
     * Returns a shrinked variant of this GuiPlaneI
     *
     * @param topShrink    The amount to lower the top side of the new GuiPlane
     * @param rightShrink  The amount to move the right side to the left of the new GuiPlane
     * @param bottomShrink The amount to raise the bottom side of the new GuiPlane
     * @param leftShrink   The amount to move the left side to the right of the new GuiPlane
     * @return A shrunken GuiPlane
     */
    public GuiPlaneI getShrinkedVariant(int topShrink, int rightShrink, int bottomShrink, int leftShrink) {
        return new GuiPlaneI(new Vector2i(getTopLeftCoordinate().x + leftShrink, topLeftCoordinate.y - topShrink), new Vector2i(getLowerRightCoordinate().x - rightShrink, getLowerRightCoordinate().y + bottomShrink));
    }


}
