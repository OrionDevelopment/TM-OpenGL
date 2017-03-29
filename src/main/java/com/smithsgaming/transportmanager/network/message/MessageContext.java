package com.smithsgaming.transportmanager.network.message;

import org.apache.logging.log4j.Logger;

/**
 * Created by marcf on 11/16/2016.
 */
public final class MessageContext {

    private Logger logger;


    public MessageContext(Logger logger) {
        this.logger = logger;
    }

    public Logger getLogger() {
        return logger;
    }
}
