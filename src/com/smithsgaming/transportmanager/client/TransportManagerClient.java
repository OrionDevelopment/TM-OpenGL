package com.smithsgaming.transportmanager.client;

import com.smithsgaming.transportmanager.client.graphics.*;
import com.smithsgaming.transportmanager.client.input.*;
import com.smithsgaming.transportmanager.client.registries.*;
import com.smithsgaming.transportmanager.client.settings.*;
import com.smithsgaming.transportmanager.main.*;
import com.smithsgaming.transportmanager.network.client.*;
import com.smithsgaming.transportmanager.util.*;
import com.smithsgaming.transportmanager.util.event.*;
import com.smithsgaming.transportmanager.util.network.Side;
import org.apache.logging.log4j.*;

import java.util.*;


/**
 * This class is responsible for handling all the parts of Transport Manager that you can see and interact with. It
 * handle the communication between You and the program in the form of Key and Mouse inputs as well as outputs through
 * the screen.
 *
 * @author Marc (Created on: 05.03.2016)
 * @author Tim
 */
public class TransportManagerClient implements Runnable, IEventController {

    public static final Logger clientLogger = LogManager.getLogger(Definitions.Loggers.CLIENT);
    public static TransportManagerClient instance = new TransportManagerClient();
    private static Thread clientNetworkThread;
    private static Thread displayThread;
    private static Display display;
    private static int targetUPS = 60;
    private static long lastFrame;
    private static long startTime;

    private Queue<TMEvent> eventQueue = new ArrayDeque<>();
    private ClientSettings settings = ClientSettings.loadSettings();

    private TransportManagerClient() {
    }

    public static Display getDisplay() {
        return display;
    }

    /**
     * Get the time in milliseconds
     *
     * @return The system time in milliseconds
     */
    public static long getTime () {
        return System.nanoTime() / 1000000;
    }

    public static long getStartTime() {
        return startTime;
    }

    public static int getDelta () {
        long time = getTime();
        int delta = (int) ( time - lastFrame );
        lastFrame = time;

        return delta;
    }

    @Override
    public void run() {
        display = new Display();
        displayThread = new Thread(display, "TM-OpenGL - Display");
        displayThread.start();

        WorldInputHandler inputHandler = new WorldInputHandler();
        MouseInputHandler.instance.registerScrollInputHandler(inputHandler);
        KeyboardInputHandler.instance.registerKeyInputHandler(inputHandler);

        clientLogger.info("Client initialization completed. Starting network!");
        clientNetworkThread = new Thread(new TMNetworkingClient("127.0.0.1", 1000));

        startTime = getTime();
        long lastTime = System.nanoTime();
        final double ns = 1000000000 / targetUPS;
        double delta = 0;

        clientLogger.info("Client network initialization complete. Starting main Client loop!");
        while (TransportManager.isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                updateClient();
                delta--;
                synchronized (eventQueue) {
                    if (eventQueue.size() == 0) {
                        continue;
                    }
                    for (TMEvent event : eventQueue) {
                        try {
                            if (!TransportManager.isRunning) {
                                return;
                            }
                            event.processEvent(Side.CLIENT);
                        } catch (Exception ex) {
                            clientLogger.error("Exception while trying to process event: " + event.toString());
                            ex.printStackTrace();
                        }
                    }
                    eventQueue.clear();
                }
            }
        }
    }

    public void startConnection() {
        clientNetworkThread.run();
    }

    public Queue<TMEvent> getEventQueue() {
        return eventQueue;
    }

    public ClientSettings getSettings() {
        return settings;
    }

    private void updateClient() {
    }

    public void preLoadGraphics() {
        clientLogger.info("Start preloading Graphics.");
        TextureRegistry.Fonts.init();
        clientLogger.info("Finished preloading Graphics");
    }

    public void loadGraphics() {
        clientLogger.info("Start loading Graphics.");
        TextureRegistry.Textures.init();
        ShaderRegistry.Shaders.init();
        clientLogger.info("Finished loading Graphics");
    }

    public void unLoadGraphics() {
        TextureRegistry.instance.unLoad();
        TextureRegistry.Fonts.unLoad();
        GeometryRegistry.instance.unLoad();
        ShaderRegistry.instance.unLoad();
    }
}
