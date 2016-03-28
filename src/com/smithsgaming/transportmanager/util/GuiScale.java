package com.smithsgaming.transportmanager.util;

/**
 * @Author Marc (Created on: 27.03.2016)
 */
public enum GuiScale {
    HD(1280, 720),
    FULLHD(1920, 1080);

    private float sizeHorizontal;
    private float sizeVertical;

    GuiScale (int sizeHorizontal, int sizeVertical) {
        this.sizeHorizontal = sizeHorizontal;
        this.sizeVertical = sizeVertical;
    }

    public float getHorizontalResolution () {
        return sizeHorizontal;
    }

    public float getVerticalResolution () {
        return sizeVertical;
    }

    public float getHorizontalScaleFactor (int horizontalSize) {
        return 1f / ( horizontalSize / sizeHorizontal );
    }

    public float getVerticalScaleFactor (int verticalSize) {
        return 1f / ( verticalSize / sizeVertical );
    }
}
