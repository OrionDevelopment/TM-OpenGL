

package com.smithsgaming.transportmanager.main.world.tiles;

import com.smithsgaming.transportmanager.main.world.*;

import java.io.*;

/**
 * Created by marcf on 3/13/2016.
 */
public abstract class Tile implements Serializable {

    private String identity;

    protected Tile(String identity) {
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }

    public abstract boolean canBeOverridenBy(World world, int tileWorldPosX, int tileWorldPosY, int tileWorldPosZ, Tile newTile);
}
