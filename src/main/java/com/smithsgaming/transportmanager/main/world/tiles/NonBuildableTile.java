package com.smithsgaming.transportmanager.main.world.tiles;

import com.smithsgaming.transportmanager.main.world.World;

/**
 * Created by marcf on 3/13/2016.
 */
public class NonBuildableTile extends Tile {

    public NonBuildableTile (String identity) {
        super(identity);
    }

    @Override
    public boolean canBeOverridenBy(World world, int tileWorldPosX, int tileWorldPosZ, Tile newTile) {
        return false;
    }
}
