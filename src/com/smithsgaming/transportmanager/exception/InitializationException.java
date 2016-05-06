package com.smithsgaming.transportmanager.exception;

import com.smithsgaming.transportmanager.common.*;

/**
 * @Author Marc (Created on: 05.05.2016)
 */
public class InitializationException extends Exception {

    IGame game;

    public InitializationException (String message, IGame game) {
        super(message);
        this.game = game;
    }
}
