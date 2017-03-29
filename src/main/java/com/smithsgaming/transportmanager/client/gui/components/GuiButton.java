package com.smithsgaming.transportmanager.client.gui.components;

import com.smithsgaming.transportmanager.client.graphics.Camera;
import com.smithsgaming.transportmanager.client.gui.GuiComponent;
import com.smithsgaming.transportmanager.client.gui.input.IButtonListener;
import com.smithsgaming.transportmanager.client.gui.input.IMouseInputComponent;
import com.smithsgaming.transportmanager.util.math.Vector2i;
import com.smithsgaming.transportmanager.util.math.graphical.GuiPlaneI;
import com.smithsgaming.transportmanager.util.network.ActionProcessingResult;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

/**
 * @Author Marc (Created on: 31.03.2016)
 */
public class GuiButton extends GuiComponent implements IMouseInputComponent {

    private GuiComponent componentOuterBackground;
    private GuiComponent componentInnerBackground;
    private GuiComponent componentInnerBackgroundHovered;
    private GuiComponent componentContent;

    private GuiPlaneI area;
    private boolean centerAlign;
    private Vector2i contentLocation;

    private int id;
    private IButtonListener inputHandler;

    public GuiButton(int id, GuiComponent parent, GuiComponent componentContent, GuiPlaneI area, boolean centerAlign, IButtonListener sam) {
        super(parent);
        this.id = id;
        this.componentContent = componentContent;
        this.area = area;
        this.centerAlign = centerAlign;

        this.componentOuterBackground = new GuiFlatArea(parent, new GuiPlaneI(area), Color.DARK_GRAY);
        this.componentInnerBackground = new GuiFlatArea(parent, new GuiPlaneI(area.getShrinkedVariant(5)), new Color(65, 65, 65));
        this.componentInnerBackgroundHovered = new GuiFlatArea(parent, new GuiPlaneI(area.getShrinkedVariant(5)), new Color(75, 75, 75));

        if (!this.centerAlign) {
            contentLocation = new Vector2i(20, 20);
        } else {
            GuiPlaneI contentArea = componentContent.getOccupiedArea();
            this.contentLocation = new Vector2i(0, 0);
        }

        this.inputHandler = sam;
    }

    /**
     * Method called by the RenderManager to process the rendering for this renderer.
     */
    @Override
    public void render()
    {
        componentOuterBackground.render();

        if (getOccupiedArea().isMouseInPlane())
        {
            componentInnerBackgroundHovered.render();
        }
        else
        {
            componentInnerBackground.render(
            );
        }

        Camera.Gui.pushMatrix();
        Camera.Gui.translateModel(new Vector3f(area.getCenterCoord().x, area.getCenterCoord().y, 0f));
        Camera.Gui.translateModel(new Vector3f(contentLocation.x, contentLocation.y, 0f));
        Camera.Gui.pushMatrix();

        componentContent.render();

        Camera.Gui.popMatrix();
        Camera.Gui.popMatrix();
    }

    @Override
    public GuiPlaneI getOccupiedArea() {
        return area;
    }

    @Override
    public void loadTextures () {
        componentContent.loadTextures();
    }

    @Override
    public void loadGeometry () {
        componentOuterBackground.loadGeometry();
        componentInnerBackground.loadGeometry();
        componentInnerBackgroundHovered.loadGeometry();
        componentContent.loadGeometry();
    }

    @Override
    public void unLoadTextures () {
        componentContent.unLoadTextures();
    }

    @Override
    public void unLoadGeometry () {
        componentOuterBackground.unLoadGeometry();
        componentInnerBackground.unLoadGeometry();
        componentInnerBackgroundHovered.unLoadGeometry();
        componentContent.unLoadGeometry();
    }

    @Override
    public boolean componentCanHandleMouseInput() {
        //TODO: Make the button have a possibilty to be disabled.
        return true;
    }

    @Override
    public ActionProcessingResult handleKeyAction(int key, int action) {
        if (key == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            inputHandler.buttonPressed(id, this);
            return ActionProcessingResult.ACCEPTED;
        }
        return ActionProcessingResult.NEUTRAL;
    }
}
