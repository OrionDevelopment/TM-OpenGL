package com.smithsgaming.transportmanager.util;

/**
 * @Author Marc (Created on: 27.03.2016)
 */
public enum GuiScale {
    SD(640, 480),
    HD(1280, 720),
    FULLHD(1920, 1080);

    private int sizeHorizontal;
    private int sizeVertical;

    GuiScale (int sizeHorizontal, int sizeVertical) {
        this.sizeHorizontal = sizeHorizontal;
        this.sizeVertical = sizeVertical;
    }

    public float getHorizontalScaleFactor (int horizontalSize) {
        return 1f;
    }
}
