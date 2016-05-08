package com.smithsgaming.transportmanager.client.framework;

import net.smert.frameworkgl.*;

/**
 * @Author Marc (Created on: 07.05.2016)
 */
public class TMGameConfiguration extends Configuration {

    public static TMGameConfiguration instance = null;

    public TMGameConfiguration (String[] args) {
        super(args);

        if (instance == null)
            instance = this;

        withOpenGL33ProfileCore();

        setWindowTitle("Transportmanager - SmithsGaming.");
    }
}
