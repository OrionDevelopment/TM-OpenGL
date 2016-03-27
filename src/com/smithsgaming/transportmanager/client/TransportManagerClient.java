package com.smithsgaming.transportmanager.client;

import com.smithsgaming.transportmanager.client.event.*;
import com.smithsgaming.transportmanager.client.graphics.*;
import com.smithsgaming.transportmanager.client.gui.*;
import com.smithsgaming.transportmanager.client.registries.*;
import com.smithsgaming.transportmanager.main.*;
import com.smithsgaming.transportmanager.network.client.*;
import com.smithsgaming.transportmanager.util.*;
import com.smithsgaming.transportmanager.util.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;


/**
 * This class is responsible for handling all the parts of Transport Manager that you can see and interact with. It
 * handle the communication between You and the program in the form of Key and Mouse inputs as well as outputs through
 * the screen.
 *
 * @Author Marc (Created on: 05.03.2016)
 */
public class TransportManagerClient implements Runnable, IEventController {

    public static final Logger clientLogger = LogManager.getLogger();
    public static TransportManagerClient instance = new TransportManagerClient();
    private static Thread clientNetworkThread;
    private static Thread displayThread;
    private static Display display;
    private static int targetUPS = 60;
    private Queue<TMEvent> eventQueue = new ArrayDeque<>();

    private TransportManagerClient() {
    }

    public static Display getDisplay() {
        return display;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used to create a thread, starting the thread
     * causes the object's <code>run</code> method to be called in that separately executing thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        display = new Display();
        displayThread = new Thread(display, "TM-OpenGL - Display");
        displayThread.start();

        display.registerEvent(new OpenGuiEvent(new GuiGameLoading()));

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        clientNetworkThread = new Thread(new TMNetworkingClient("127.0.0.1", 1000));
        //clientNetworkThread.start();

        long lastTime = System.nanoTime();
        final double ns = 1000000000 / targetUPS;
        double delta = 0;

        while (TransportManager.isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                updateClient();
                delta--;

                synchronized (eventQueue) {
                    if (eventQueue.size() == 0)
                        continue;

                    for (TMEvent event : eventQueue) {
                        try {
                            if (!TransportManager.isRunning)
                                return;

                            event.processEvent(Side.CLIENT);
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

    private void updateClient() {

    }

    public void loadGraphics() {
        TextureRegistry.Textures.init();
        ShaderRegistry.Shaders.init();
    }

    public void unLoadGraphics() {
        TextureRegistry.instance.unLoad();
        GeometryRegistry.instance.unLoad();
        ShaderRegistry.instance.unLoad();
    }
}
