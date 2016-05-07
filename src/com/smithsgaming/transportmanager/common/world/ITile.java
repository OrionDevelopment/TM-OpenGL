package com.smithsgaming.transportmanager.common.world;

import com.smithsgaming.transportmanager.common.player.*;
import com.smithsgaming.transportmanager.util.common.*;

/**
 * @Author Marc (Created on: 24.04.2016)
 */
public interface ITile extends IIdentifyableWorldObject {
    /**
     * Method to check if the player can build over this Tile with the given Tile or not
     *
     * @param player          The player that attempts to build over an instance of this Tile
     * @param newTile         The Tile that the player tries to put over this Tile
     * @param worldCoordinate The coordinate the Player wants to build on.
     *
     * @return True when the player is allowed to build over this Tile, false when not.
     */
    boolean isOverbuildableBy (IPlayer player, ITile newTile, WorldCoordinate worldCoordinate);
}
