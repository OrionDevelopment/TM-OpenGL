package com.smithsgaming.transportmanager.client.input;

import com.smithsgaming.transportmanager.client.graphics.*;
import org.lwjgl.glfw.*;
import org.lwjgl.util.vector.*;

import java.util.*;

/**
 * @Author Marc (Created on: 28.03.2016)
 */
public class WorldInputHandler implements KeyboardInputHandler.IKeyInputHandler, MouseInputHandler.IScrollInputHandler {

    private static ArrayList moveKeys = new ArrayList();
    private static ArrayList keyActions = new ArrayList();

    static {
        moveKeys.add(GLFW.GLFW_KEY_W);
        moveKeys.add(GLFW.GLFW_KEY_A);
        moveKeys.add(GLFW.GLFW_KEY_S);
        moveKeys.add(GLFW.GLFW_KEY_D);

        keyActions.add(GLFW.GLFW_PRESS);
        keyActions.add(GLFW.GLFW_REPEAT);
    }

    @Override
    public ArrayList<Integer> getHandledKeys () {
        return moveKeys;
    }

    @Override
    public ArrayList<Integer> getActionTypeForKey (Integer key) {
        return keyActions;
    }

    @Override
    public void onKeyPressed (int key, int action) {
        switch (key) {
            case GLFW.GLFW_KEY_W:
                Camera.Player.moveCamera(new Vector3f(0, 0, -0.25f * Camera.Player.getCameraPosition().y));
                break;
            case GLFW.GLFW_KEY_S:
                Camera.Player.moveCamera(new Vector3f(0, 0, 0.25f * Camera.Player.getCameraPosition().y));
                break;
            case GLFW.GLFW_KEY_A:
                Camera.Player.moveCamera(new Vector3f(-0.25f * Camera.Player.getCameraPosition().y, 0, 0));
                break;
            case GLFW.GLFW_KEY_D:
                Camera.Player.moveCamera(new Vector3f(0.25f * Camera.Player.getCameraPosition().y, 0, 0));
                break;
        }
    }

    @Override
    public void handleScroll (float xOffSet, float yOffSet) {
        if (Camera.Player.getCameraPosition().getY() + yOffSet < 2F || Camera.Player.getCameraPosition().getY() + yOffSet > 549F)
            return;

        Camera.Player.moveCamera(new Vector3f(0, yOffSet, 0));
    }
}
