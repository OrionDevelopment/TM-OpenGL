package com.smithsgaming.transportmanager.client.gui.components;

import com.smithsgaming.transportmanager.client.graphics.Camera;
import com.smithsgaming.transportmanager.client.graphics.TrueTypeFont;
import com.smithsgaming.transportmanager.util.math.Vector2i;
import com.smithsgaming.transportmanager.util.math.graphical.GuiPlaneI;
import org.lwjgl.util.Color;

import static org.lwjgl.opengl.GL11.*;

/**
 * @Author Marc (Created on: 27.03.2016)
 */
public class GuiComponentText extends GuiComponentAbstract {

    private TrueTypeFont font;
    private String text;
    private boolean center;
    private Color color;
    private GuiPlaneI area;

    public GuiComponentText(GuiComponentAbstract parent, TrueTypeFont font, String text, Vector2i location, boolean center, Color color) {
        super(parent);
        this.font = font;
        this.text = text;
        this.center = center;
        this.color = color;
        this.area = font.getOccupiedAreaForText(text).getMovedVariant(location);
    }


    @Override
    public GuiPlaneI getOccupiedArea() {
        return area;
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
        Camera.Gui.setActiveColor(color);
        font.drawString(Camera.Gui, area.getTopLeftCoordinate().x, area.getTopLeftCoordinate().y, text, center ? TrueTypeFont.ALIGN_CENTER : TrueTypeFont.ALIGN_LEFT);
        glDisable(GL_BLEND);
    }
}
