package com.smithsgaming.transportmanager.util;

import com.smithsgaming.transportmanager.util.netty.LOG4J2NettyFactory;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by marcf on 11/13/2016.
 */
public class OSUtil {

    public static void setLWJGLLibsForOS() throws Exception {
        try {
            File JGLLib = new File(System.getProperty("user.dir") + File.separator + "libs"+ File.separator + "LWJGL" + File.separator + "native");

            System.setProperty("org.lwjgl.librarypath", JGLLib.getAbsolutePath());
        } catch (Exception ex) {
            System.err.println("Failed to load LWJGL! Setup invalid!");
            System.err.print(ex);
            System.exit(-1);
        }
    }

    public static void initializeLOG4J2() {
        try {
            File logConfigurationFile =
              new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator + "logging" + File.separator + "log4j.xml");

            ConfigurationSource source = new ConfigurationSource(new FileInputStream(logConfigurationFile), logConfigurationFile);
            Configurator.initialize(null, source);

            InternalLoggerFactory.setDefaultFactory(new LOG4J2NettyFactory());
        } catch(Exception ex){
            System.err.println("Failed to load LOG4J2! Setup invalid!");
            System.err.print(ex);
            System.exit(-2);
        }
    }
}
