/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.client.graphics;

/**
 *  ------ Class not Documented ------
 */
public enum GuiAspectRatio {
    HD(16f, 9f);

    private float aspectRatio;
    private float commonHorizontalAspect;
    private float commonVerticalAspect;

    GuiAspectRatio (float commonHorizontalAspect, float commonVerticalAspect) {
        this.aspectRatio = ( commonHorizontalAspect / commonVerticalAspect );
        this.commonHorizontalAspect = commonHorizontalAspect;
        this.commonVerticalAspect = commonVerticalAspect;
    }

    public float getAspectRatio () {
        return aspectRatio;
    }

    public float getCommonHorizontalAspect () {
        return commonHorizontalAspect;
    }

    public float getCommonVerticalAspect () {
        return commonVerticalAspect;
    }
}
