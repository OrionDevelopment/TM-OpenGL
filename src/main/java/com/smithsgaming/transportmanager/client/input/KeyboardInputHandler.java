/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.client.input;

import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *  ------ Class not Documented ------
 */
public class KeyboardInputHandler {

    public static final KeyboardInputHandler instance = new KeyboardInputHandler();

    public static final GLFWKeyCallback KEY_CALLBACK = new GLFWKeyCallback() {
        @Override
        public void invoke (long window, int key, int scancode, int action, int mods) {
            if (!instance.keyInputHandlers.containsKey(key))
                return;

            if (!instance.keyInputHandlers.get(key).containsKey(action))
                return;

            for (IKeyInputHandler handler : instance.keyInputHandlers.get(key).get(action))
                handler.onKeyPressed(key, action);
        }
    };

    private HashMap<Integer, HashMap<Integer, ArrayList<IKeyInputHandler>>> keyInputHandlers = new HashMap<>();

    private KeyboardInputHandler () {
    }

    public void registerKeyInputHandler (IKeyInputHandler handler) {
        for (Integer key : handler.getHandledKeys()) {
            if (!keyInputHandlers.containsKey(key))
                keyInputHandlers.put(key, new HashMap<>());

            for (Integer action : handler.getActionTypeForKey(key)) {
                if (!keyInputHandlers.get(key).containsKey(action))
                    keyInputHandlers.get(key).put(action, new ArrayList<>());

                keyInputHandlers.get(key).get(action).add(handler);
            }
        }
    }

    public void unregisterKeyInputHandler (IKeyInputHandler handler) {
        for (Integer key : handler.getHandledKeys()) {
            if (!keyInputHandlers.containsKey(key))
                continue;

            for (Integer action : handler.getActionTypeForKey(key)) {
                if (!keyInputHandlers.get(key).containsKey(action))
                    continue;

                keyInputHandlers.get(key).get(action).remove(handler);
            }
        }
    }

    public interface IKeyInputHandler {

        ArrayList<Integer> getHandledKeys ();

        ArrayList<Integer> getActionTypeForKey (Integer key);

        void onKeyPressed (int key, int action);
    }
}
