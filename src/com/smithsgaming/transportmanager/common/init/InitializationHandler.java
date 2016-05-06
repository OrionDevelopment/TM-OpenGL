package com.smithsgaming.transportmanager.common.init;

import com.smithsgaming.transportmanager.common.*;
import com.smithsgaming.transportmanager.exception.*;
import com.smithsgaming.transportmanager.util.common.*;

import java.util.*;

/**
 * @Author Marc (Created on: 05.05.2016)
 */
public class InitializationHandler {
    ArrayList<IInitController> initControllers;
    IGame instance;

    public InitializationHandler (IGame instance) {
        this.instance = instance;

        instance.initializeInitHandler(this);
    }

    public void initializeGame () throws InitializationException {
        onInitStarting();

        ///TODO: Implement proper logging system.
    }

    private void onInitStarting () throws InitializationException {
        for (IInitController controller : initControllers) {
            if (controller.onInitStarting() == MethodHandlingResult.DENIED)
                throw new InitializationException("Could not initialize the InitHandler.", instance);
        }
    }

    private void onPreInit () throws InitializationException {
        for (IInitController controller : initControllers) {
            if (controller.onPreInit() == MethodHandlingResult.DENIED)
                throw new InitializationException("Could not Pre-Initialize the Game. Failed to run the: " + controller.getClass().getCanonicalName() + "-Controller", instance);
        }
    }

    private void onInit () throws InitializationException {
        for (IInitController controller : initControllers) {
            if (controller.onInit() == MethodHandlingResult.DENIED)
                throw new InitializationException("Could not Initialize the Game. Failed to run the: " + controller.getClass().getCanonicalName() + "-Controller", instance);
        }
    }

    private void onPostInit () throws InitializationException {
        for (IInitController controller : initControllers) {
            if (controller.onPostInit() == MethodHandlingResult.DENIED)
                throw new InitializationException("Could not Post-Initialize the Game. Failed to run the: " + controller.getClass().getCanonicalName() + "-Controller", instance);
        }
    }

    private void onInitComplete () throws InitializationException {
        for (IInitController controller : initControllers) {
            if (controller.onInitComplete() == MethodHandlingResult.DENIED)
                throw new InitializationException("Could not  complete the Initialization of the Game. Failed to run the: " + controller.getClass().getCanonicalName() + "-Controller", instance);
        }
    }
}
