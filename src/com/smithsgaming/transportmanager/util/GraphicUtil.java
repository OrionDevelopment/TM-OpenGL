

package com.smithsgaming.transportmanager.util;

import java.awt.*;
import java.nio.*;

/**
 * @Author Marc (Created on: 05.03.2016)
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
