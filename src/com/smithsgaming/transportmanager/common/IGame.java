package com.smithsgaming.transportmanager.common;

import com.smithsgaming.transportmanager.common.init.InitializationHandler;
import com.smithsgaming.transportmanager.util.common.Side;
import org.apache.logging.log4j.Logger;


/**
 * @Author Marc (Created on: 05.05.2016)
 */
public interface IGame {

    Side getSide ();

    boolean isInitialized ();

    /**
     * Method called by an instance of InitializationHandler to get its InitControllers.
     *
     * @param handler The handler that requests to be Initialized.
     */
    void initializeInitHandler (InitializationHandler handler);

    /**
     * Getter for this instance of the games Logger
     *
     * @return The games Logger
     */
    Logger getLogger();

    /**
     * Setter for the Logger.
     */
    void setLogger(Logger logger);
}
