package com.smithsgaming.transportmanager.client.gui.components;

import com.smithsgaming.transportmanager.client.graphics.*;

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

        //if (center)
        //this.xCoord -= ( this.font.getWidth(this.text) / 2f );
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
        //font.drawString(xCoord, yCoord, text, 1F, 1F);
        //OpenGLUtil.checkGlState("Render text");
    }
}
