package com.smithsgaming.transportmanager.client.init;

import com.smithsgaming.transportmanager.common.*;
import com.smithsgaming.transportmanager.common.init.*;
import com.smithsgaming.transportmanager.util.common.*;

import java.util.*;

/**
 * @Author Marc (Created on: 07.05.2016)
 */
public class ClientTileRegistrationInitController implements IInitController {

    @Override
    public MethodHandlingResult onPreInit (IGame game) {
        //TODO: Put the tiles that are buildable in here.
        //NOTE: Might change the way the buildables are registered and do that in the Init phase.
        ArrayList baseReplaceIdentities = new ArrayList(Arrays.asList(new String[]{}));

        return MethodHandlingResult.HANDLED;
    }
}

