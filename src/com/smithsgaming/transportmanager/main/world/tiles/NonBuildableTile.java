package com.smithsgaming.transportmanager.main.world.tiles;

import com.smithsgaming.transportmanager.main.world.*;

/**
 * Created by marcf on 3/13/2016.
 */
public class NonBuildableTile extends Tile {

    protected NonBuildableTile(String identity) {
        super(identity);
    }

    @Override
    public boolean canBeOverridenBy(World world, int tileWorldPosX, int tileWorldPosY, int tileWorldPosZ, Tile newTile) {
        return false;
    }
}
