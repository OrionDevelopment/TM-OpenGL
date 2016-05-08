package com.smithsgaming.transportmanager.client.framework;

import com.smithsgaming.transportmanager.client.render.gui.*;
import com.smithsgaming.transportmanager.common.*;
import net.smert.frameworkgl.*;
import net.smert.frameworkgl.gui.*;
import net.smert.frameworkgl.opengl.*;
import net.smert.frameworkgl.opengl.shader.basic.*;
import net.smert.frameworkgl.utils.*;
import org.apache.logging.log4j.*;

import java.io.*;

/**
 * @Author Marc (Created on: 07.05.2016)
 */
public class GameScreen extends Screen {
    private DiffuseTextureShader diffuseTextureShader;
    private FpsTimer fpsTimer;
    private GuiScreen guiScreen;

    public GameScreen (String[] args) {
    }

    @Override
    public void destroy () {
        System.out.println("destroy");
        System.out.println(Fw.timer);
        diffuseTextureShader.destroy();
    }

    @Override
    public void init () {
        System.out.println("init");

        // Register assets
        try {
            Fw.files.registerAssets("res", false);
        } catch (Exception e) {
            GameController.runningInstance.getLogger().log(Level.ERROR, e);
        }

        // Switch renderer and factory to OpenGL 3
        Fw.graphics.switchOpenGLVersion(3);

        // Create timer
        fpsTimer = new FpsTimer();

        // Create GUI screen
        guiScreen = new GuiLoading();
        //Text Rendering: guiScreen.init(Fw.graphics.getRenderer());

        // Initialize GUI
        Fw.gui.init();

        // Switch GUI screen
        Fw.gui.setScreen(guiScreen);

        // Build shaders
        try {
            diffuseTextureShader = DiffuseTextureShader.Factory.Create();
            diffuseTextureShader.init();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


        GL.o1.clear();
    }

    @Override
    public void pause () {
        System.out.println("pause");
    }

    @Override
    public void render () {
        fpsTimer.update();
        GL.o1.clear();
        GL.o1.setBlendingFunctionSrcAlphaAndOneMinusSrcAlpha();
        GL.o1.enableBlending();
        Fw.graphics.switchShader(diffuseTextureShader);
        Fw.graphics.set2DMode();
        Fw.gui.update();
        Fw.gui.render();
    }

    @Override
    public void resize (int width, int height) {
        System.out.println("resize");
        GL.o1.setViewport(0, 0, width, height);
    }

    @Override
    public void resume () {
        System.out.println("resume");
    }
}
