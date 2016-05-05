package com.smithsgaming.transportmanager.server.world;

import com.smithsgaming.transportmanager.common.player.*;
import com.smithsgaming.transportmanager.common.world.*;
import com.smithsgaming.transportmanager.util.common.*;

/**
 * @Author Marc (Created on: 05.05.2016)
 */
public class ServerAbstractTile implements ITile {
    String identity;

    public ServerAbstractTile (String identity) {
        this.identity = identity;
    }

    @Override
    public boolean isOverbuildableBy (IPlayer player, ITile newTile, WorldCoordinate worldCoordinate) {
        return false;
    }

    @Override
    public String getIdentity () {
        return identity;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (!( o instanceof ITile )) return false;

        ITile that = (ITile) o;

        return getIdentity().equals(that.getIdentity());

    }

    @Override
    public int hashCode () {
        return getIdentity().hashCode();
    }
}
