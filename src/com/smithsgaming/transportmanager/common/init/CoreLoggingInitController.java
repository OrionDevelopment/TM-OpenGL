package com.smithsgaming.transportmanager.common.init;

import com.smithsgaming.transportmanager.common.IGame;
import com.smithsgaming.transportmanager.util.common.MethodHandlingResult;
import org.apache.logging.log4j.LogManager;

/**
 * Created by marcf on 5/6/2016.
 */
public class CoreLoggingInitController implements IInitController {

    @Override
    public MethodHandlingResult onInitStarting(IGame game) {
        game.setLogger(LogManager.getLogger("Transportmanager - " + game.getSide().toString()));
        return MethodHandlingResult.HANDLED;
    }
}
