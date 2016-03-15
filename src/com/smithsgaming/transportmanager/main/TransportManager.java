
package com.smithsgaming.transportmanager.main;

import com.smithsgaming.transportmanager.network.server.*;

/**
 * Game class for the TransportManager game.
 * Initializes the game and runs it.
 *
 * Created by Marc on 05.03.2016.
 */
public class TransportManager implements Runnable {

    static Thread serverNetworkThread;

    public static boolean isRunning = true;

    @Override
    public void run() {
        serverNetworkThread = new Thread(new TMNetworkingServer(1000));
        serverNetworkThread.start();

        while(isRunning)
        {

        }
    }
}
