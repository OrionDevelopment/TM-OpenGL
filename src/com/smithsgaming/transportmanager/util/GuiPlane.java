package com.smithsgaming.transportmanager.util;

/**
 * @Author Marc (Created on: 25.03.2016)
 */
public class GuiPlane {
    float coord1X;
    float coord2X;
    float coord3X;
    float coord4X;
    float coord1Y;
    float coord2Y;
    float coord3Y;
    float coord4Y;

    public GuiPlane (float coord1X, float coord2X, float coord3X, float coord4X, float coord1Y, float coord2Y, float coord3Y, float coord4Y) {
        this.coord1X = coord1X;
        this.coord2X = coord2X;
        this.coord3X = coord3X;
        this.coord4X = coord4X;
        this.coord1Y = coord1Y;
        this.coord2Y = coord2Y;
        this.coord3Y = coord3Y;
        this.coord4Y = coord4Y;
    }

    public float getCoord1X () {
        return coord1X;
    }

    public float getCoord2X () {
        return coord2X;
    }

    public float getCoord3X () {
        return coord3X;
    }

    public float getCoord4X () {
        return coord4X;
    }

    public float getCoord1Y () {
        return coord1Y;
    }

    public float getCoord2Y () {
        return coord2Y;
    }

    public float getCoord3Y () {
        return coord3Y;
    }

    public float getCoord4Y () {
        return coord4Y;
    }
}
