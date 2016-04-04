package com.smithsgaming.transportmanager.client.gui;

import com.smithsgaming.transportmanager.client.TransportManagerClient;
import com.smithsgaming.transportmanager.client.gui.input.IMouseInputComponent;
import com.smithsgaming.transportmanager.client.input.MouseInputHandler;
import com.smithsgaming.transportmanager.client.render.IRenderer;
import com.smithsgaming.transportmanager.util.ActionProcessingResult;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @Author Marc (Created on: 25.03.2016)
 */
public class GuiController implements IRenderer, MouseInputHandler.IMouseInputHandler {

    public static final GuiController instance = new GuiController();
    private static ArrayList<Integer> mousebuttons = new ArrayList<>();
    private static ArrayList<Integer> mouseactions = new ArrayList<>();

    Stack<GuiScreen> openedGuiStack = new Stack<>();

    private GuiController() {
        if (mousebuttons == null) {
            mousebuttons = new ArrayList<>();
            mouseactions = new ArrayList<>();
        }

        if (mousebuttons.size() == 0) {
            mousebuttons.add(GLFW.GLFW_MOUSE_BUTTON_LEFT);
            mousebuttons.add(GLFW.GLFW_MOUSE_BUTTON_RIGHT);
            mousebuttons.add(GLFW.GLFW_MOUSE_BUTTON_MIDDLE);

            mouseactions.add(GLFW.GLFW_PRESS);
        }

        MouseInputHandler.instance.registerMouseInputHandler(this);
    }

    /**
     * Method called by the RenderManager to process the rendering for this renderer.
     */
    @Override
    public void render () {
        if (openedGuiStack.size() == 0)
            return;

        GuiScreen guiOpen = openedGuiStack.peek();
        guiOpen.render();
    }

    public void openGui (GuiScreen gui) {
        openedGuiStack.push(gui);

        gui.loadGui();
        gui.loadGeometry();
        gui.loadTextures();
    }

    public void closeGui () {
        if (openedGuiStack.size() == 0)
            throw new IllegalStateException("No gui open!");

        GuiScreen guiToBeClosed = openedGuiStack.pop();
        guiToBeClosed.unLoadGeometry();
        guiToBeClosed.unLoadTextures();
    }

    public boolean isGuiOpen () {
        return openedGuiStack.size() != 0;
    }

    @Override
    public ArrayList<Integer> getHandledButtons() {
        return mousebuttons;
    }

    @Override
    public ArrayList<Integer> getActionTypeForButton(Integer key) {
        return mouseactions;
    }

    @Override
    public ActionProcessingResult onKeyPressed(int key, int action) {
        TransportManagerClient.clientLogger.info("Handled key click!");

        ActionProcessingResult result = ActionProcessingResult.NEUTRAL;

        for (GuiComponent componentAbstract : openedGuiStack.peek().getComponents()) {
            if (componentAbstract instanceof IMouseInputComponent && componentAbstract.getOccupiedArea().isMouseInPlane()) {
                IMouseInputComponent mouseInputComponent = (IMouseInputComponent) componentAbstract;
                if (mouseInputComponent.componentCanHandleMouseInput()) {
                    result = mouseInputComponent.handleKeyAction(key, action);
                    if (result != ActionProcessingResult.NEUTRAL) {
                        return result;
                    }
                }
            }
        }

        return result;
    }
}
