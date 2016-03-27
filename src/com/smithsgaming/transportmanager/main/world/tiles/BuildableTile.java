package com.smithsgaming.transportmanager.main.world.tiles;

import com.smithsgaming.transportmanager.main.world.World;

/**
 * Created by Tim on 27/03/2016.
 */
public class BuildableTile extends Tile {

    protected BuildableTile(String identity) {
        super(identity);
    }

    @Override
    public boolean canBeOverridenBy(World world, int tileWorldPosX, int tileWorldPosY, int tileWorldPosZ, Tile newTile) {
        return true;
    }
}
