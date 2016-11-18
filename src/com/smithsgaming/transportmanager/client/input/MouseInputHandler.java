package com.smithsgaming.transportmanager.client.input;

import com.smithsgaming.transportmanager.client.TransportManagerClient;
import com.smithsgaming.transportmanager.client.graphics.GuiScale;
import com.smithsgaming.transportmanager.util.network.ActionProcessingResult;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Author Marc (Created on: 28.03.2016)
 */
public class MouseInputHandler {

    public static final MouseInputHandler instance = new MouseInputHandler();
    private HashMap<Integer, Boolean> buttonStates = new HashMap<>();
    private ArrayList<IScrollInputHandler> scrollInputHandlers = new ArrayList<>();
    public static final GLFWScrollCallback SCROLL_CALLBACK = new GLFWScrollCallback() {
        @Override
        public void invoke (long window, double xoffset, double yoffset) {
            for (IScrollInputHandler handler : instance.scrollInputHandlers)
                handler.handleScroll((float) xoffset, (float) yoffset);
        }
    };
    private HashMap<Integer, HashMap<Integer, ArrayList<IMouseInputHandler>>> mouseInputHandlerHashmap = new HashMap<>();
    private float mouseX;
    private float mouseY;
    public static final GLFWMouseButtonCallback MOUSE_BUTTON_CALLBACK = new GLFWMouseButtonCallback() {
        @Override
        public void invoke(long window, int button, int action, int mods) {
            if (action == GLFW.GLFW_PRESS)
                instance.buttonStates.put(button, true);
            else
                instance.buttonStates.put(button, false);

            if (!instance.mouseInputHandlerHashmap.containsKey(button))
                return;

            if (!instance.mouseInputHandlerHashmap.get(button).containsKey(action))
                return;

            TransportManagerClient.clientLogger.info("Handled mouse click on: " + instance.getMouseX() + "-" + instance.getMouseY());

            ActionProcessingResult result = ActionProcessingResult.NEUTRAL;
            for (IMouseInputHandler handler : instance.mouseInputHandlerHashmap.get(button).get(action)) {
                result = handler.onKeyPressed(button, action);
                if (result != ActionProcessingResult.NEUTRAL)
                    return;
            }
        }
    };
    public static final GLFWCursorPosCallback CURSOR_POS_CALLBACK = new GLFWCursorPosCallback() {
        @Override
        public void invoke(long window, double xpos, double ypos) {
            instance.mouseX = (float) (xpos / TransportManagerClient.getDisplay().getSizeHorizontal()) * GuiScale.FWVGA.getHorizontalResolution() - (GuiScale.FWVGA.getHorizontalResolution() / 2);
            instance.mouseY = (float) (1 - (ypos / TransportManagerClient.getDisplay().getSizeVertical())) * GuiScale.FWVGA.getVerticalResolution() - (GuiScale.FWVGA.getVerticalResolution() / 2);
        }
    };

    private MouseInputHandler () {
    }

    public boolean isButtonPressed (int mouseButton) {
        if (!buttonStates.containsKey(mouseButton))
            return false;

        return buttonStates.get(mouseButton);
    }

    public float getMouseX () {
        return mouseX;
    }

    public float getMouseY () {
        return mouseY;
    }

    public void registerScrollInputHandler (IScrollInputHandler handler) {
        scrollInputHandlers.add(handler);
    }

    public void unregisterScrollInputHandler (IScrollInputHandler handler) {
        scrollInputHandlers.remove(handler);
    }

    public void registerMouseInputHandler(IMouseInputHandler handler) {
        for (Integer key : handler.getHandledButtons()) {
            if (!mouseInputHandlerHashmap.containsKey(key))
                mouseInputHandlerHashmap.put(key, new HashMap<>());

            for (Integer action : handler.getActionTypeForButton(key)) {
                if (!mouseInputHandlerHashmap.get(key).containsKey(action))
                    mouseInputHandlerHashmap.get(key).put(action, new ArrayList<>());

                mouseInputHandlerHashmap.get(key).get(action).add(handler);
            }
        }
    }

    public void unregisterMouseInputHandler(IMouseInputHandler handler) {
        for (Integer key : handler.getHandledButtons()) {
            if (!mouseInputHandlerHashmap.containsKey(key))
                continue;

            for (Integer action : handler.getActionTypeForButton(key)) {
                if (!mouseInputHandlerHashmap.get(key).containsKey(action))
                    continue;

                mouseInputHandlerHashmap.get(key).get(action).remove(handler);
            }
        }
    }

    public interface IScrollInputHandler {
        void handleScroll (float xOffSet, float yOffSet);
    }

    public interface IMouseInputHandler {

        ArrayList<Integer> getHandledButtons();

        ArrayList<Integer> getActionTypeForButton(Integer key);

        ActionProcessingResult onKeyPressed(int key, int action);
    }
}
