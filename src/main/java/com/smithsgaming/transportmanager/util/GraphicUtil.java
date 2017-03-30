

/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.util;

import java.awt.*;
import java.nio.FloatBuffer;

/**
 *  ------ Class not Documented ------
 */
public class GraphicUtil {

    public static void storeColor (FloatBuffer dest, Color source) {
        dest.clear();
        dest.put(source.getRed() / 255f);
        dest.put(source.getGreen() / 255f);
        dest.put(source.getBlue() / 255f);
        dest.put(source.getAlpha() / 255f);
        dest.flip();
    }
}
