package com.smithsgaming.transportmanager.client.gui.components;

import com.smithsgaming.transportmanager.client.graphics.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * @Author Marc (Created on: 27.03.2016)
 */
public class ComponentText extends GuiComponent {

    private TrueTypeFont font;
    private String text;
    private Float xCoord;
    private Float yCoord;
    private boolean center;

    public ComponentText (GuiComponent parent, TrueTypeFont font, String text, Float xCoord, Float yCoord, boolean center) {
        super(parent);
        this.font = font;
        this.text = text;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.center = center;
    }


    @Override
    public void loadTextures () {

    }

    @Override
    public void loadGeometry () {

    }

    @Override
    public void unLoadTextures () {

    }

    @Override
    public void unLoadGeometry () {

    }

    /**
     * Method called by the RenderManager to process the rendering for this renderer.
     */
    @Override
    public void render () {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        font.drawString(Camera.Gui, xCoord, yCoord, text, TrueTypeFont.ALIGN_CENTER);
        //OpenGLUtil.checkGlState("Render text");
        glDisable(GL_BLEND);
    }
}
