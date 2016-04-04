package com.smithsgaming.transportmanager.util.math.graphical;

import com.smithsgaming.transportmanager.client.graphics.*;
import com.smithsgaming.transportmanager.client.input.*;
import org.lwjgl.util.vector.*;

/**
 * @Author Marc (Created on: 25.03.2016)
 */
public class GuiPlaneF {

    private final Vector2f topLeftCoordinate;
    private final Vector2f lowerRightCoordinate;
    private final Vector2f topRightCoordinate;
    private final Vector2f lowerLeftCoordinate;

    private final float width;
    private final float height;

    private final Vector2f centerCoord;

    public GuiPlaneF (Vector2f topLeftCoordinate, Vector2f lowerRightCoordinate) {
        this.topLeftCoordinate = topLeftCoordinate;
        this.lowerRightCoordinate = lowerRightCoordinate;

        this.lowerLeftCoordinate = new Vector2f(topLeftCoordinate.x, lowerRightCoordinate.y);
        this.topRightCoordinate = new Vector2f(lowerRightCoordinate.x, topLeftCoordinate.y);

        this.width = lowerRightCoordinate.x - topLeftCoordinate.x;
        this.height = topLeftCoordinate.y - lowerRightCoordinate.y;

        this.centerCoord = new Vector2f(topLeftCoordinate.x + ( width / 2 ), topLeftCoordinate.y - ( height / 2 ));
    }

    public GuiPlaneF (GuiPlaneF toCopy) {
        this(new Vector2f(toCopy.getTopLeftCoordinate()), new Vector2f(toCopy.getLowerRightCoordinate()));
    }

    public GuiPlaneF () {
        this(new Vector2f(-( GuiScale.FWVGA.getHorizontalResolution() / 2f ), ( GuiScale.FWVGA.getVerticalResolution() / 2f )), new Vector2f(( GuiScale.FWVGA.getHorizontalResolution() / 2f ), -( GuiScale.FWVGA.getVerticalResolution() / 2f )));
    }

    public Vector2f getTopLeftCoordinate () {
        return topLeftCoordinate;
    }

    public Vector2f getLowerRightCoordinate () {
        return lowerRightCoordinate;
    }

    public Vector2f getTopRightCoordinate () {
        return topRightCoordinate;
    }

    public Vector2f getLowerLeftCoordinate () {
        return lowerLeftCoordinate;
    }

    public float getWidth () {
        return width;
    }

    public float getHeight () {
        return height;
    }

    public Vector2f getCenterCoord () {
        return centerCoord;
    }

    public boolean isPointInPlane (Vector2f point) {
        return ( point.x >= topLeftCoordinate.x ) && ( point.x <= topRightCoordinate.x ) && ( point.y <= topLeftCoordinate.y ) && ( point.y >= lowerLeftCoordinate.y );
    }

    public boolean isMouseInPlane () {
        return isPointInPlane(new Vector2f(MouseInputHandler.instance.getMouseX(), MouseInputHandler.instance.getMouseY()));
    }

    public GuiPlaneF getMovedVariant (Vector2f diff) {
        return new GuiPlaneF(Vector2f.add(topLeftCoordinate, diff, new Vector2f()), Vector2f.add(lowerRightCoordinate, diff, new Vector2f()));
    }

    /**
     * Returns a shrinked variant of this GuiPlaneI
     *
     * @param shrinkAmount The amount to move the sides.
     *
     * @return A shrinked version of this GuiPlaneI
     */
    public GuiPlaneF getShrinkedVariant (int shrinkAmount) {
        return getShrinkedVariant(shrinkAmount, shrinkAmount, shrinkAmount, shrinkAmount);
    }

    /**
     * Returns a shrinked variant of this GuiPlaneI
     *
     * @param topShrink    The amount to lower the top side of the new GuiPlane
     * @param rightShrink  The amount to move the right side to the left of the new GuiPlane
     * @param bottomShrink The amount to raise the bottom side of the new GuiPlane
     * @param leftShrink   The amount to move the left side to the right of the new GuiPlane
     *
     * @return A shrunken GuiPlane
     */
    public GuiPlaneF getShrinkedVariant (int topShrink, int rightShrink, int bottomShrink, int leftShrink) {
        return new GuiPlaneF(new Vector2f(getTopLeftCoordinate().x + leftShrink, topLeftCoordinate.y - topShrink), new Vector2f(getLowerRightCoordinate().x - rightShrink, getLowerRightCoordinate().y + bottomShrink));
    }


}
