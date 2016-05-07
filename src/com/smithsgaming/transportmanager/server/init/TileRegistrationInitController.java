package com.smithsgaming.transportmanager.server.init;

import com.smithsgaming.transportmanager.common.*;
import com.smithsgaming.transportmanager.common.init.*;
import com.smithsgaming.transportmanager.server.world.tile.*;
import com.smithsgaming.transportmanager.util.common.*;
import com.smithsgaming.transportmanager.util.constants.*;

/**
 * @Author Marc (Created on: 07.05.2016)
 */
public class TileRegistrationInitController implements IInitController {

    @Override
    public MethodHandlingResult onPreInit (IGame game) {
        GameController.tileRegistry.registerTile(new ServerBaseTile(Identities.OCEAN.getValue()));

        return MethodHandlingResult.HANDLED;
    }
}

