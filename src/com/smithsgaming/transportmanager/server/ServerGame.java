package com.smithsgaming.transportmanager.server;

import com.smithsgaming.transportmanager.common.*;
import com.smithsgaming.transportmanager.common.init.*;
import com.smithsgaming.transportmanager.util.common.*;
import org.apache.logging.log4j.*;

/**
 * @Author Marc (Created on: 05.05.2016)
 */
public class ServerGame implements IGame, Runnable {

    Logger logger;
    InitializationHandler initializationHandler;
    boolean initialized;

    @Override
    public Side getSide () {
        return Side.SERVER;
    }

    @Override
    public boolean isInitialized () {
        return initialized;
    }

    @Override
    public void initializeInitHandler (InitializationHandler handler) {
        handler.registerInitController(new CoreLoggingInitController());
    }

    @Override
    public Logger getLogger () {
        return logger;
    }

    @Override
    public void setLogger (Logger logger) {
        this.logger = logger;
    }

    @Override
    public void run () {
        try {
            initializationHandler = new InitializationHandler(this);
            initializationHandler.initializeGame();
        } catch (Exception ex) {
            if (getLogger() != null) {
                getLogger().log(Level.ERROR, ex);
            } else {
                ex.printStackTrace();
            }
        }
    }
}
