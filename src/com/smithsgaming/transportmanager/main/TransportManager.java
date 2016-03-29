
package com.smithsgaming.transportmanager.main;

import com.smithsgaming.transportmanager.main.core.TileRegistry;
import com.smithsgaming.transportmanager.main.core.WorldManager;
import com.smithsgaming.transportmanager.network.server.*;
import com.smithsgaming.transportmanager.util.*;
import com.smithsgaming.transportmanager.util.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Game class for the TransportManager game.
 * Initializes the game and runs it.
 * <p>
 * Created by Marc on 05.03.2016.
 */
public class TransportManager implements Runnable, IEventController {

    public static final Logger serverLogger = LogManager.getLogger();
    public static final TransportManager instance = new TransportManager();

    public static boolean isRunning = true;
    static Thread serverNetworkThread;
    static int targetUPS = 60;

    private Queue<TMEvent> eventQueue = new ArrayDeque<>();

    private TransportManager() {
    }

    @Override
    public void run() {
        TileRegistry.init();
        WorldManager.instance.generateWorld();

        serverNetworkThread = new Thread(new TMNetworkingServer(1000));
        serverNetworkThread.start();

        long lastTime = System.nanoTime();
        final double ns = 1000000000 / targetUPS;
        double delta = 0;

        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                updateServer();
                delta--;
                synchronized (eventQueue) {
                    if (eventQueue.size() == 0) {
                        continue;
                    }
                    for (TMEvent event : eventQueue) {
                        try {
                            if (!isRunning) {
                                return;
                            }
                            event.processEvent(Side.SERVER);
                        } catch (Exception ex) {
                            System.err.println("Exception while trying to process event: " + event.toString());
                            ex.printStackTrace();
                        }
                    }
                    eventQueue.clear();
                }
            }
        }
    }

    public Queue<TMEvent> getEventQueue() {
        return eventQueue;
    }

    private void updateServer() {
        WorldManager.instance.updateWorld();
    }
}
