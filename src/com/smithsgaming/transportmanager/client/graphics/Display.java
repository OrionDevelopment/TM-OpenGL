package com.smithsgaming.transportmanager.client.graphics;

import com.smithsgaming.transportmanager.client.*;
import com.smithsgaming.transportmanager.client.render.*;
import com.smithsgaming.transportmanager.main.*;
import com.smithsgaming.transportmanager.util.*;
import com.smithsgaming.transportmanager.util.event.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.io.*;
import java.nio.*;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Main class that handles the Rendering of the Game.
 *
 * @Author Marc (Created on: 05.03.2016)
 */
public class Display implements Runnable, IEventController
{
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

    private void init () {
        System.out.println("Initializing UI System, LWJGL natives directory set to: " + new File(System.getProperty("java.library.path")).getAbsolutePath() + " with LWJGL Library: " + Library.JNI_LIBRARY_NAME);

        try {
            glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint());

            debugMessageKHRCallback = new GLDebugMessageCallback() {
                @Override
                public void invoke (int source, int type, int id, int severity, int length, long message, long userParam) {
                    System.out.println(GLDebugMessageCallback.getMessage(length, message));
                }
            };

            if (glfwInit() != GL11.GL_TRUE) throw new IllegalStateException("Unable to initialize GLFW");

            glfwDefaultWindowHints();
            glfwWindowHint(GLFW_VISIBLE, GL11.GL_FALSE);
            glfwWindowHint(GLFW_RESIZABLE, GL11.GL_TRUE);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            if (fullScreen) {
                window = glfwCreateWindow(sizeHorizontal, sizeVertical, "Hello World!", glfwGetPrimaryMonitor(), MemoryUtil.NULL);
                if (window == MemoryUtil.NULL)
                    throw new RuntimeException("Failed to create the GLFW window");
            } else {
                window = glfwCreateWindow(sizeHorizontal, sizeVertical, "Hello World!", MemoryUtil.NULL, MemoryUtil.NULL);
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

                    OpenGLUtil.setAspectRatio(((float) width / (float) height));
                }
            };

            glfwSetFramebufferSizeCallback(window, resizeWindow);

            glfwMakeContextCurrent(window);
            glfwSwapInterval(1);

            // Make the window visible
            glfwShowWindow(window);

            GL.createCapabilities();

            KHRDebug.glDebugMessageCallback(debugMessageKHRCallback, 0);

            glClearColor(102f / 255f, 152f / 255f, 1.0f, 1.0f);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void runRender () {
        while (glfwWindowShouldClose(window) == GLFW_FALSE) {
            if (resized) {
                GL11.glViewport(0, 0, sizeHorizontal, sizeVertical);
                resized = false;
            }

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            doRenderLoop();

            doProcessEventLoop();

            glfwSwapBuffers(window); // swap the color buffers

            glfwPollEvents();
        }
    }

    private void doRenderLoop () {
        RenderHandler.doRender();
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

            TransportManagerClient.instance.loadGraphics();

            runRender();

            glfwDestroyWindow(window);
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

    public long getWindow () {
        return window;
    }
}
