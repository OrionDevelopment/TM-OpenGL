package com.smithsgaming.transportmanager.util;

import java.io.File;

/**
 * Created by marcf on 11/13/2016.
 */
public class OSUtil {

    public static void setLWJGLLibsForOS() throws Exception {
        try {
            File JGLLib = new File(System.getProperty("user.dir") + "/libs/LWJGL/native");

            System.setProperty("org.lwjgl.librarypath", JGLLib.getAbsolutePath());
        } catch (Exception ex) {

        }
    }
}
