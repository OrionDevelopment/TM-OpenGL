package com.smithsgaming.transportmanager.common.init;

import com.google.common.base.Stopwatch;
import com.smithsgaming.transportmanager.common.IGame;
import com.smithsgaming.transportmanager.exception.InitializationException;
import com.smithsgaming.transportmanager.util.common.MethodHandlingResult;
import com.smithsgaming.transportmanager.util.common.StringUtil;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @Author Marc (Created on: 05.05.2016)
 */
public class InitializationHandler {
    ArrayList<IInitController> initControllers;
    IGame instance;

    public InitializationHandler (IGame instance) {
        this.instance = instance;
        this.initControllers = new ArrayList<>();

        instance.initializeInitHandler(this);
    }

    public void registerInitController (IInitController controller) {
        initControllers.add(controller);
    }

    public void initializeGame () throws InitializationException {
        onInitStarting();

        instance.getLogger().log(Level.INFO, "Starting Transportmanager.");
        instance.getLogger().log(Level.INFO, "Initializing " + instance.getSide().name() + ".");

        Stopwatch initTimer = Stopwatch.createStarted();
        instance.getLogger().log(Level.INFO, "");
        instance.getLogger().log(Level.INFO, "======================Pre-Init======================");
        onPreInit();
        instance.getLogger().log(Level.INFO, StringUtil.fillCentered("Finished Pre-Init after: " + initTimer.elapsed(TimeUnit.MILLISECONDS) + " Ms.", "=", 52));

        initTimer = Stopwatch.createStarted();
        instance.getLogger().log(Level.INFO, "");
        instance.getLogger().log(Level.INFO, "========================Init========================");
        onInit();
        instance.getLogger().log(Level.INFO, StringUtil.fillCentered("Finished Pre-Init after: " + initTimer.elapsed(TimeUnit.MILLISECONDS) + " Ms.", "=", 52));

        initTimer = Stopwatch.createStarted();
        instance.getLogger().log(Level.INFO, "");
        instance.getLogger().log(Level.INFO, "======================Post-Init=====================");
        onPostInit();
        instance.getLogger().log(Level.INFO, StringUtil.fillCentered("Finished Pre-Init after: " + initTimer.elapsed(TimeUnit.MILLISECONDS) + " Ms.", "=", 52));

        onInitComplete();

        instance.getLogger().log(Level.INFO, "Finished initializing " + instance.getSide().name() + ".");
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
