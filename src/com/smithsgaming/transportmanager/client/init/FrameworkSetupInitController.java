package com.smithsgaming.transportmanager.client.init;

import com.smithsgaming.transportmanager.client.framework.*;
import com.smithsgaming.transportmanager.common.*;
import com.smithsgaming.transportmanager.common.init.*;
import com.smithsgaming.transportmanager.util.common.*;
import net.smert.frameworkgl.*;

/**
 * @Author Marc (Created on: 07.05.2016)
 */
public class FrameworkSetupInitController implements IInitController {

    @Override
    public MethodHandlingResult onInitStarting (IGame game) {
        Bootstrap boot = new Bootstrap();
        boot.start(TMGameConfiguration.class, GameScreen.class, game.getProgramStartupArgs());

        return MethodHandlingResult.HANDLED;
    }
}
