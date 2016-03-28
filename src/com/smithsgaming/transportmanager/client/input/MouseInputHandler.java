package com.smithsgaming.transportmanager.client.input;

import org.lwjgl.glfw.*;

import java.util.*;

/**
 * @Author Marc (Created on: 28.03.2016)
 */
public class MouseInputHandler {

    public static final MouseInputHandler instance = new MouseInputHandler();

    public static final GLFWMouseButtonCallback MOUSE_BUTTON_CALLBACK = new GLFWMouseButtonCallback() {
        @Override
        public void invoke (long window, int button, int action, int mods) {
            if (action == GLFW.GLFW_PRESS)
                instance.buttonStates.put(button, true);
            else
                instance.buttonStates.put(button, false);

        }
    };

    public static final GLFWCursorPosCallback CURSOR_POS_CALLBACK = new GLFWCursorPosCallback() {
        @Override
        public void invoke (long window, double xpos, double ypos) {
            instance.mouseX = (float) xpos;
            instance.mouseY = (float) ypos;
        }
    };

    public static final GLFWScrollCallback SCROLL_CALLBACK = new GLFWScrollCallback() {
        @Override
        public void invoke (long window, double xoffset, double yoffset) {
            for (IScrollInputHandler handler : instance.scrollInputHandlers)
                handler.handleScroll((float) xoffset, (float) yoffset);
        }
    };

    private HashMap<Integer, Boolean> buttonStates = new HashMap<>();

    private ArrayList<IScrollInputHandler> scrollInputHandlers = new ArrayList<>();

    private float mouseX;

    private float mouseY;

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

    public interface IScrollInputHandler {
        void handleScroll (float xOffSet, float yOffSet);
    }
}
