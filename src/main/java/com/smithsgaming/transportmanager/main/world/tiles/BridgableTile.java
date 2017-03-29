package com.smithsgaming.transportmanager.main.world.tiles;

import com.smithsgaming.transportmanager.main.world.World;

/**
 * Created by Tim on 27/03/2016.
 */
public class BridgableTile extends Tile {

    public BridgableTile (String identity) {
        super(identity);
    }

    @Override
    public boolean canBeOverridenBy (World world, int tileWorldPosX, int tileWorldPosZ, Tile newTile) {
        return false;
    }

    @Override
    public boolean shouldBeUsedInWorldGenNoise() {
        return false;
    }
}
