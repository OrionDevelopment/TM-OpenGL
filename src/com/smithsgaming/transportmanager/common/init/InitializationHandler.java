package com.smithsgaming.transportmanager.common.init;

import com.smithsgaming.transportmanager.common.IGame;
import com.smithsgaming.transportmanager.exception.InitializationException;
import com.smithsgaming.transportmanager.util.common.MethodHandlingResult;

import java.util.ArrayList;

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

        instance.getLogger().log();
    }

    private void onInitStarting () throws InitializationException {
        for (IInitController controller : initControllers) {
            if (controller.onInitStarting(instance) == MethodHandlingResult.DENIED)
                throw new InitializationException("Could not initialize the InitHandler.", instance);
        }
    }

    private void onPreInit () throws InitializationException {
        for (IInitController controller : initControllers) {
            if (controller.onPreInit(instance) == MethodHandlingResult.DENIED)
                throw new InitializationException("Could not Pre-Initialize the Game. Failed to run the: " + controller.getClass().getCanonicalName() + "-Controller", instance);
        }
    }

    private void onInit () throws InitializationException {
        for (IInitController controller : initControllers) {
            if (controller.onInit(instance) == MethodHandlingResult.DENIED)
                throw new InitializationException("Could not Initialize the Game. Failed to run the: " + controller.getClass().getCanonicalName() + "-Controller", instance);
        }
    }

    private void onPostInit () throws InitializationException {
        for (IInitController controller : initControllers) {
            if (controller.onPostInit(instance) == MethodHandlingResult.DENIED)
                throw new InitializationException("Could not Post-Initialize the Game. Failed to run the: " + controller.getClass().getCanonicalName() + "-Controller", instance);
        }
    }

    private void onInitComplete () throws InitializationException {
        for (IInitController controller : initControllers) {
            if (controller.onInitComplete(instance) == MethodHandlingResult.DENIED)
                throw new InitializationException("Could not  complete the Initialization of the Game. Failed to run the: " + controller.getClass().getCanonicalName() + "-Controller", instance);
        }
    }
}
