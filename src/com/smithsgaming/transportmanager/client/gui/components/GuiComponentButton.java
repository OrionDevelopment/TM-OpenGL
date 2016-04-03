package com.smithsgaming.transportmanager.client.gui.components;

import com.smithsgaming.transportmanager.client.graphics.Camera;
import com.smithsgaming.transportmanager.client.gui.components.inputhandling.IInputSAM;
import com.smithsgaming.transportmanager.client.gui.components.inputhandling.IMouseInputComponent;
import com.smithsgaming.transportmanager.util.ActionProcessingResult;
import com.smithsgaming.transportmanager.util.math.Vector2i;
import com.smithsgaming.transportmanager.util.math.graphical.GuiPlaneI;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;

/**
 * @Author Marc (Created on: 31.03.2016)
 */
public class GuiComponentButton extends GuiComponentAbstract implements IMouseInputComponent {

    private GuiComponentAbstract componentOuterBackground;
    private GuiComponentAbstract componentInnerBackground;
    private GuiComponentAbstract componentInnerBackgroundHovered;
    private GuiComponentAbstract componentContent;

    private GuiPlaneI area;
    private boolean centerAlign;
    private Vector2i contentLocation;

    private IInputSAM inputHandler;

    public GuiComponentButton(GuiComponentAbstract parent, GuiComponentAbstract componentContent, GuiPlaneI area, boolean centerAlign, IInputSAM sam) {
        super(parent);
        this.componentContent = componentContent;
        this.area = area;
        this.centerAlign = centerAlign;

        this.componentOuterBackground = new GuiComponentFlatArea(parent, new GuiPlaneI(area), (Color) Color.DKGREY);
        this.componentInnerBackground = new GuiComponentFlatArea(parent, new GuiPlaneI(area.getShrinkedVariant(5)), new Color(65, 65, 65));
        this.componentInnerBackgroundHovered = new GuiComponentFlatArea(parent, new GuiPlaneI(area.getShrinkedVariant(5)), new Color(75, 75, 75));

        if (!this.centerAlign) {
            contentLocation = new Vector2i(20, 20);
        } else {
            GuiPlaneI contentArea = componentContent.getOccupiedArea();
            this.contentLocation = new Vector2i(area.getCenterCoord()).sub(new Vector2i(contentArea.getWidth() / 2, contentArea.getHeight() / 2));
        }

        this.inputHandler = sam;
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

    /**
     * Method called by the RenderManager to process the rendering for this renderer.
     */
    @Override
    public void render () {
        componentOuterBackground.render();

        if (getOccupiedArea().isMouseInPlane()) {
            componentInnerBackgroundHovered.render();
        } else {
            componentInnerBackground.render(
            );
        }

        Camera.Gui.pushMatrix();
        Camera.Gui.translateModel(new Vector3f(area.getCenterCoord().x, area.getCenterCoord().y, 0f));
        Camera.Gui.pushMatrix();

        componentContent.render();

        Camera.Gui.popMatrix();
        Camera.Gui.popMatrix();
    }

    @Override
    public boolean componentCanHandleMouseInput() {
        //TODO: Make the button have a possibilty to be disabled.
        return true;
    }

    @Override
    public ActionProcessingResult handleKeyAction(int key, int action) {
        if (key == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            inputHandler.invoke();
            return ActionProcessingResult.ACCEPTED;
        }

        return ActionProcessingResult.NEUTRAL;
    }
}
