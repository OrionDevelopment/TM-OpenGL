/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

/*
 * Copyright (c)  2015-2017, SmithsGaming Inc.
 */

/*
 * Copyright (c)  2015-2017, SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.main;

import com.smithsgaming.transportmanager.client.TransportManagerClient;
import com.smithsgaming.transportmanager.util.OSUtil;

/**
 * Main class for the TransportManager game, responsible for starting and running an instance of the TransportManager
 * class.
 *
 *  ------ Class not Documented ------
 */
public class Main {
    static TransportManagerClient client;
    static TransportManager server;

    static Thread clientThread;
    static Thread serverThread;

    public static void main(String[] args) {
        OSUtil.initializeLOG4J2();

        server = TransportManager.instance;

        serverThread = new Thread(server, "TM-OpenGL - Server");
        serverThread.start();

        client = TransportManagerClient.instance;
        clientThread = new Thread(client, "TM-OpenGL - Client");
        clientThread.start();
    }
}
