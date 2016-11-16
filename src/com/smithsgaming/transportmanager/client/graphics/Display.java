package com.smithsgaming.transportmanager.client.graphics;

import com.smithsgaming.transportmanager.client.*;
import com.smithsgaming.transportmanager.client.event.*;
import com.smithsgaming.transportmanager.client.gui.menus.*;
import com.smithsgaming.transportmanager.client.gui.screens.*;
import com.smithsgaming.transportmanager.client.input.*;
import com.smithsgaming.transportmanager.client.render.*;
import com.smithsgaming.transportmanager.main.*;
import com.smithsgaming.transportmanager.util.*;
import com.smithsgaming.transportmanager.util.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import javax.sound.midi.SysexMessage;
import java.io.*;
import java.nio.*;
import java.util.*;

import static com.smithsgaming.transportmanager.client.TransportManagerClient.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Main class that handles the Rendering of the Game.
 *
 * @Author Marc (Created on: 05.03.2016)
 */
public class Display implements Runnable, IEventController
{
    public static final Logger displayLogger = LogManager.getLogger(Definitions.Loggers.DISPLAY);
    private static int max_texture_size = -1;
    private int resolutionHorizontal = 1240;
    private int resolutionVertical = 720;
    private boolean fullScreen = false;
    private int sizeHorizontal = resolutionHorizontal;
    private int sizeVertical = resolutionVertical;
    private boolean resized = false;
    private GLFWErrorCallback errorCallback;
    private GLFWFramebufferSizeCallback resizeWindow;
    private GLDebugMessageCallback debugMessageKHRCallback;
    private long window;
    private Queue<TMEvent> eventQueue = new ArrayDeque<>();
    private long lastFPS = getTime();
    private int fps;

    public Display(){
    }

    public static int getMaxTextureSize () {
        if (max_texture_size != -1) return max_texture_size;
        for (int i = 0x4000; i > 0; i >>= 1) {
            GL11.glTexImage2D(GL11.GL_PROXY_TEXTURE_2D, 0, GL11.GL_RGBA, i, i, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
            if (GL11.glGetTexLevelParameteri(GL11.GL_PROXY_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH) != 0) {
                max_texture_size = i;
                return i;
            }
        }
        return -1;
    }

    private void init () throws Exception {
        OSUtil.setLWJGLLibsForOS();

        displayLogger.info("Initializing UI System, LWJGL natives directory set to: " + new File(System.getProperty("org.lwjgl.librarypath")).getAbsolutePath() + " with LWJGL Library: " + Library.JNI_LIBRARY_NAME);

        try {
            glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint());

            debugMessageKHRCallback = new GLDebugMessageCallback() {
                @Override
                public void invoke(int source, int type, int id, int severity, int length, long message, long userParam) {
                    displayLogger.error("###########################################");
                    displayLogger.error("GLERROR: " + type + "/" + id);
                    displayLogger.error(GLDebugMessageCallback.getMessage(length, message));
                    displayLogger.error("###########################################");
                }
            };

            if (glfwInit() != GL11.GL_TRUE) throw new IllegalStateException("Unable to initialize GLFW");

            glfwDefaultWindowHints();
            glfwWindowHint(GLFW_VISIBLE, GL11.GL_FALSE);
            glfwWindowHint(GLFW_RESIZABLE, GL11.GL_TRUE);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            if (fullScreen) {
                window = glfwCreateWindow(sizeHorizontal, sizeVertical, "Hello WorldServer!", glfwGetPrimaryMonitor(), MemoryUtil.NULL);
                if (window == MemoryUtil.NULL)
                    throw new RuntimeException("Failed to create the GLFW window");
            } else {
                window = glfwCreateWindow(sizeHorizontal, sizeVertical, "Hello WorldServer!", MemoryUtil.NULL, MemoryUtil.NULL);
                if (window == MemoryUtil.NULL)
                    throw new RuntimeException("Failed to create the GLFW window");

                // Center our window
                glfwSetWindowPos(
                        window,
                        ( vidmode.width() - sizeHorizontal ) / 2,
                        ( vidmode.height() - sizeVertical ) / 2
                );
            }

            resizeWindow = new GLFWFramebufferSizeCallback() {
                @Override
                public void invoke (long window, int width, int height) {
                    resized = true;
                    sizeVertical = height;
                    sizeHorizontal = width;
                }
            };

            glfwSetFramebufferSizeCallback(window, resizeWindow);

            glfwSetCursorPosCallback(window, MouseInputHandler.CURSOR_POS_CALLBACK);
            glfwSetMouseButtonCallback(window, MouseInputHandler.MOUSE_BUTTON_CALLBACK);
            glfwSetKeyCallback(window, KeyboardInputHandler.KEY_CALLBACK);
            glfwSetScrollCallback(window, MouseInputHandler.SCROLL_CALLBACK);

            glfwMakeContextCurrent(window);
            glfwSwapInterval(0);

            // Make the window visible
            glfwShowWindow(window);

            GL.createCapabilities();

            KHRDebug.glDebugMessageCallback(debugMessageKHRCallback, 0);

            //glClearColor(1f, 1f, 1f, 1f);
            glClearColor(102f / 255f, 152f / 255f, 1.0f, 1.0f);

            OpenGLUtil.checkGlState("Display - Init");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void runRender () {
        while (glfwWindowShouldClose(window) != GL11.GL_TRUE) {
            if (resized) {
                GL11.glViewport(0, 0, sizeHorizontal, sizeVertical);
                OpenGLUtil.setAspectRatio(( (float) sizeHorizontal / (float) sizeVertical ));
                resized = false;
            }

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            doRenderLoop();

            doProcessEventLoop();

            glfwSwapBuffers(window); // swap the color buffers

            glfwPollEvents();

            updateFPS();
        }
    }

    private void doRenderLoop () {
        RenderHandler.doRender();
    }

    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS () {
        if (getTime() - lastFPS > 1000) {
            glfwSetWindowTitle(window, "FPS: " + fps);
            fps = 0; //reset the FPS counter
            lastFPS += 1000; //add one second
        }

        fps++;
    }

    private void doProcessEventLoop () {
        synchronized (eventQueue) {
            if (eventQueue.size() == 0)
                return;

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
        try {
            init();

            TransportManagerClient.instance.preLoadGraphics();

            RenderHandler.getGuiController().openGui(new GuiGameLoading());

            TransportManagerClient.instance.loadGraphics();

            Thread delayedLoadingScreenThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    displayLogger.info("Starting loading screen wait");

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    displayLogger.info("Going to main menu");

                    TransportManagerClient.getDisplay().registerEvent(new EventClientGuiClose());
                    TransportManagerClient.getDisplay().registerEvent(new EventClientGuiOpen(new GuiMainMenu()));
                }
            });

            delayedLoadingScreenThread.start();

            runRender();

            glfwDestroyWindow(window);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            TransportManagerClient.instance.unLoadGraphics();

            glfwTerminate();
            errorCallback.release();

            TransportManager.isRunning = false;
        }
    }

    public Queue<TMEvent> getEventQueue() {
        return eventQueue;
    }

    public int getResolutionHorizontal() {
        return resolutionHorizontal;
    }

    public int getResolutionVertical() {
        return resolutionVertical;
    }

    public int getSizeHorizontal () {
        return sizeHorizontal;
    }

    public int getSizeVertical () {
        return sizeVertical;
    }

    public long getWindow () {
        return window;
    }
}
