package com.smithsgaming.transportmanager.common;

import com.smithsgaming.transportmanager.common.init.*;
import com.smithsgaming.transportmanager.util.common.*;

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
}
