package com.smithsgaming.transportmanager.server;

import com.smithsgaming.transportmanager.common.*;

/**
 * @Author Marc (Created on: 24.04.2016)
 */
public class ServerMain {

    public static void main (String[] args) {
        GameController.runningInstance = new ServerGame();
        GameController.runningInstance.run();
    }
}
