package com.smithsgaming.transportmanager.main;

import com.smithsgaming.transportmanager.client.*;

/**
 * Main class for the TransportManager game, responsible for starting and running an instance of the TransportManager
 * class.
 *
 * @Author Marc (Created on: 05.03.2016)
 */
public class Main {

    static TransportManagerClient client;
    static TransportManager server;

    static Thread clientThread;
    static Thread serverThread;

    public static void main(String[] args) {
        server = new TransportManager();

        serverThread = new Thread(server, "TM-OpenGL -  Server");
        serverThread.start();

        client = new TransportManagerClient();

        clientThread = new Thread(client, "TM-OpenGL - Client");
        clientThread.start();

    }
}
