package com.smithsgaming.transportmanager.client;

import com.smithsgaming.transportmanager.client.init.*;
import com.smithsgaming.transportmanager.common.*;
import com.smithsgaming.transportmanager.common.init.*;
import com.smithsgaming.transportmanager.util.common.*;
import org.apache.logging.log4j.*;

/**
 * @Author Marc (Created on: 07.05.2016)
 */
public class ClientGame implements IGame {

    String[] programStartupArgs;
    Logger logger;
    InitializationHandler initializationHandler;
    boolean initialized;

    public ClientGame (String[] programStartupArgs) {
        this.programStartupArgs = programStartupArgs;
    }

    @Override
    public String[] getProgramStartupArgs () {
        return programStartupArgs;
    }

    @Override
    public Side getSide () {
        return Side.CLIENT;
    }

    @Override
    public boolean isInitialized () {
        return initialized;
    }

    @Override
    public void initializeInitHandler (InitializationHandler handler) {
        handler.registerInitController(new CoreLoggingInitController());
        handler.registerInitController(new LWJGLSetupInitController());
        handler.registerInitController(new FrameworkSetupInitController());

        handler.registerInitController(new ClientTileRegistrationInitController());
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
            initialized = true;

        } catch (Exception ex) {
            if (getLogger() != null) {
                getLogger().log(Level.ERROR, ex);
            } else {
                ex.printStackTrace();
            }
        }
    }
}
