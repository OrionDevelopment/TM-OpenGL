package com.smithsgaming.transportmanager.client.graphics;

/**
 * @Author Marc (Created on: 27.03.2016)
 */
public enum GuiScale {
    FWVGA(854, 480, 1, GuiAspectRatio.HD),
    HD720P(1280, 720, 2, GuiAspectRatio.HD),
    FULLHD(1920, 1080, 0, GuiAspectRatio.HD);

    private float sizeHorizontal;
    private float sizeVertical;
    private int nextScaleFactor;
    private GuiAspectRatio aspectRatio;

    GuiScale (int sizeHorizontal, int sizeVertical, int nextScaleFactor, GuiAspectRatio aspectRatio) {
        this.sizeHorizontal = sizeHorizontal;
        this.sizeVertical = sizeVertical;
        this.nextScaleFactor = nextScaleFactor;
        this.aspectRatio = aspectRatio;
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

    public GuiScale getNextScale () {
        return GuiScale.values()[nextScaleFactor];
    }

    public GuiAspectRatio getAspectRatio () {
        return aspectRatio;
    }
}
