package com.smithsgaming.transportmanager.server.init;

import com.smithsgaming.transportmanager.common.*;
import com.smithsgaming.transportmanager.common.init.*;
import com.smithsgaming.transportmanager.server.world.tile.*;
import com.smithsgaming.transportmanager.util.common.*;
import com.smithsgaming.transportmanager.util.constants.*;

import java.util.*;

/**
 * @Author Marc (Created on: 07.05.2016)
 */
public class ServerTileRegistrationInitController implements IInitController {

    @Override
    public MethodHandlingResult onPreInit (IGame game) {
        //TODO: Put the tiles that are buildable in here.
        //NOTE: Might change the way the buildables are registered and do that in the Init phase.
        ArrayList baseReplaceIdentities = new ArrayList(Arrays.asList(new String[]{}));

        GameController.tileRegistry.registerTile(new ServerBaseTile(Identities.OCEAN.getValue()));
        GameController.tileRegistry.registerTile(new ServerBaseTile(Identities.OCEAN.getValue()));
        GameController.tileRegistry.registerTile(new ServerSelectBuildableTile(Identities.GRASS.getValue(), baseReplaceIdentities));
        GameController.tileRegistry.registerTile(new ServerSelectBuildableTile(Identities.DRY_GRASS.getValue(), baseReplaceIdentities));
        GameController.tileRegistry.registerTile(new ServerSelectBuildableTile(Identities.BEACH.getValue(), baseReplaceIdentities));
        GameController.tileRegistry.registerTile(new ServerSelectBuildableTile(Identities.DESERT.getValue(), baseReplaceIdentities));
        GameController.tileRegistry.registerTile(new ServerSelectBuildableTile(Identities.RIVER.getValue(), baseReplaceIdentities));
        GameController.tileRegistry.registerTile(new ServerSelectBuildableTile(Identities.SNOW.getValue(), baseReplaceIdentities));
        GameController.tileRegistry.registerTile(new ServerSelectBuildableTile(Identities.STONE_OVERGROUND.getValue(), baseReplaceIdentities));
        GameController.tileRegistry.registerTile(new ServerSelectBuildableTile(Identities.STONE_UNDERGROUND.getValue(), baseReplaceIdentities));
        GameController.tileRegistry.registerTile(new ServerSelectBuildableTile(Identities.ICE.getValue(), baseReplaceIdentities));
        GameController.tileRegistry.registerTile(new ServerSelectBuildableTile(Identities.ICE_BUSH_BROWN.getValue(), baseReplaceIdentities));
        GameController.tileRegistry.registerTile(new ServerBaseTile(Identities.LAKE.getValue()));
        GameController.tileRegistry.registerTile(new ServerSelectBuildableTile(Identities.SCORCHED.getValue(), baseReplaceIdentities));

        //GameController.tileRegistry.registerTile(new BuildingTile(TileNames.BUILDING));

        return MethodHandlingResult.HANDLED;
    }
}

