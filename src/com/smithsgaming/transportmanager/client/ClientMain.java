package com.smithsgaming.transportmanager.client;

import com.smithsgaming.transportmanager.common.*;

/**
 * @Author Marc (Created on: 24.04.2016)
 */
public class ClientMain {

    public static void main (String[] args) {
        GameController.runningInstance = new ClientGame(args);
        GameController.runningInstance.run();
    }

}
