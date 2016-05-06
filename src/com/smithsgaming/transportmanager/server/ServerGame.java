package com.smithsgaming.transportmanager.server;

import com.smithsgaming.transportmanager.common.*;
import com.smithsgaming.transportmanager.common.init.*;
import com.smithsgaming.transportmanager.util.common.*;

/**
 * @Author Marc (Created on: 05.05.2016)
 */
public class ServerGame implements IGame, Runnable {

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

    }

    @Override
    public void run () {
        initializationHandler = new InitializationHandler(this);
        initializationHandler.initializeGame();
    }
}
