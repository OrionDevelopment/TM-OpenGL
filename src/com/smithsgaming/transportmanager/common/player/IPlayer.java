package com.smithsgaming.transportmanager.common.player;

import com.smithsgaming.transportmanager.common.entity.*;
import com.smithsgaming.transportmanager.util.constants.*;

/**
 * @Author Marc (Created on: 24.04.2016)
 */
public interface IPlayer extends IEntity {
    /**
     * Method to get the Display name of a Player
     *
     * @return The Display name of the player
     */
    String getDisplayName ();

    /**
     * Method to get the account data of the Player
     *
     * @return The Account data of the player.
     */
    IAccountData getAccountData ();

    @Override
    default String getIdentity () {
        return Identities.PLAYER.getValue() + getAccountData().getUserName();
    }
}
