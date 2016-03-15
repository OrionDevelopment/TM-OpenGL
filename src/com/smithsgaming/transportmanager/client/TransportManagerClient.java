package com.smithsgaming.transportmanager.client;

import com.smithsgaming.transportmanager.client.graphics.*;
import com.smithsgaming.transportmanager.client.registries.*;
import com.smithsgaming.transportmanager.network.client.*;
import com.smithsgaming.transportmanager.network.message.*;
import com.smithsgaming.transportmanager.util.*;
import org.jnbt.*;

import java.io.*;


/**
 * This class is responsible for handling all the parts of Transport Manager that you can see and interact with. It
 * handle the communication between You and the program in the form of Key and Mouse inputs as well as outputs through
 * the screen.
 *
 * @Author Marc (Created on: 05.03.2016)
 */
public class TransportManagerClient implements Runnable {

    public static TransportManagerClient instance = new TransportManagerClient();
    static Thread clientNetworkThread;
    private static Display display;
    private static Thread displayThread;

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
    public void run () {
        display = new Display();
        displayThread = new Thread(display, "TM-OpenGL - Display");
        displayThread.start();

        clientNetworkThread = new Thread(new TMNetworkingClient("127.0.0.1", 1000));
        clientNetworkThread.start();

        while (TMNetworkingClient.activeComChannel == null) {
            try {
                System.out.println("No active network connection found, retrying in a second...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Active connection found sending payload...");
        TMNetworkingClient.sendMessage(new NBTPayloadMessage(new StringTag("Test", "Hello Server!")));
    }

    public void loadGraphics () {
        TextureRegistry.Textures.init();
        try {
            OpenGLUtil.loadDefaultShaderProgramm();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void unLoadGraphics () {
        TextureRegistry.instance.unLoad();
        GeometryRegistry.instance.unLoad();

        OpenGLUtil.deleteShader(OpenGLUtil.Shaders.defaultShader);
    }
}
