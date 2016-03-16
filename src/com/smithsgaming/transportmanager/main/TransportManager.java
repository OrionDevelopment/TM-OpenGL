
package com.smithsgaming.transportmanager.main;

import com.smithsgaming.transportmanager.main.world.*;
import com.smithsgaming.transportmanager.network.server.*;
import com.smithsgaming.transportmanager.util.*;
import com.smithsgaming.transportmanager.util.event.*;

/**
 * Game class for the TransportManager game.
 * Initializes the game and runs it.
 *
 * Created by Marc on 05.03.2016.
 */
public class TransportManager implements Runnable, IEventController {

    public static boolean isRunning = true;
    static Thread serverNetworkThread;
    static int targetUPS = 60;

    @Override
    public void run() {
        WorldManager.instance.generateWorld();

        serverNetworkThread = new Thread(new TMNetworkingServer(1000));
        serverNetworkThread.start();

        long lastTime = System.nanoTime();
        final double ns = 1000000000 / targetUPS;
        double delta = 0;

        while(isRunning)
        {
            long now = System.nanoTime();
            delta += ( now - lastTime ) / ns;
            lastTime = now;

            while (delta >= 1) {
                updateServer();
                delta--;

                synchronized (eventQueu) {
                    if (eventQueu.size() == 0)
                        continue;

                    for (TMEvent event : eventQueu) {
                        try {
                            if (!isRunning)
                                return;

                            event.processEvent(Side.SERVER);
                        } catch (Exception ex) {
                            System.err.println("Exception while trying to process event: " + event.toString());
                            ex.printStackTrace();
                        }
                    }

                    eventQueu.clear();
                }
            }
        }
    }

    private void updateServer () {

    }
}
