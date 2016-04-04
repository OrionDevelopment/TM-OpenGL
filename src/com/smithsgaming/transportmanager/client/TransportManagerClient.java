package com.smithsgaming.transportmanager.client;

import com.smithsgaming.transportmanager.client.graphics.Display;
import com.smithsgaming.transportmanager.client.input.KeyboardInputHandler;
import com.smithsgaming.transportmanager.client.input.MouseInputHandler;
import com.smithsgaming.transportmanager.client.input.WorldInputHandler;
import com.smithsgaming.transportmanager.client.registries.GeometryRegistry;
import com.smithsgaming.transportmanager.client.registries.ShaderRegistry;
import com.smithsgaming.transportmanager.client.registries.TextureRegistry;
import com.smithsgaming.transportmanager.client.settings.ClientSettings;
import com.smithsgaming.transportmanager.main.TransportManager;
import com.smithsgaming.transportmanager.network.client.TMNetworkingClient;
import com.smithsgaming.transportmanager.util.Side;
import com.smithsgaming.transportmanager.util.event.IEventController;
import com.smithsgaming.transportmanager.util.event.TMEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Queue;


/**
 * This class is responsible for handling all the parts of Transport Manager that you can see and interact with. It
 * handle the communication between You and the program in the form of Key and Mouse inputs as well as outputs through
 * the screen.
 *
 * @author Marc (Created on: 05.03.2016)
 * @author Tim
 */
public class TransportManagerClient implements Runnable, IEventController {

    public static final Logger clientLogger = LogManager.getLogger();
    public static TransportManagerClient instance = new TransportManagerClient();
    private static Thread clientNetworkThread;
    private static Thread displayThread;
    private static Display display;
    private static int targetUPS = 60;

    static {

    }

    private Queue<TMEvent> eventQueue = new ArrayDeque<>();
    private ClientSettings settings = ClientSettings.loadSettings();

    private TransportManagerClient() {
    }

    public static Display getDisplay() {
        return display;
    }

    @Override
    public void run() {
        display = new Display();
        displayThread = new Thread(display, "TM-OpenGL - Display");
        displayThread.start();

        WorldInputHandler inputHandler = new WorldInputHandler();
        MouseInputHandler.instance.registerScrollInputHandler(inputHandler);
        KeyboardInputHandler.instance.registerKeyInputHandler(inputHandler);

        clientLogger.info("Client thread loaded!");
        clientNetworkThread = new Thread(new TMNetworkingClient("127.0.0.1", 1000));

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

    public void loadGraphics() {
        TextureRegistry.Textures.init();
        TextureRegistry.Fonts.init();
        ShaderRegistry.Shaders.init();
    }

    public void unLoadGraphics() {
        TextureRegistry.instance.unLoad();
        TextureRegistry.Fonts.unLoad();
        GeometryRegistry.instance.unLoad();
        ShaderRegistry.instance.unLoad();
    }
}
