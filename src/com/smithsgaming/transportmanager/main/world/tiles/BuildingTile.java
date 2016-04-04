package com.smithsgaming.transportmanager.main.world.tiles;

import com.smithsgaming.transportmanager.main.tileentity.*;
import com.smithsgaming.transportmanager.main.world.*;

/**
 * Created by Tim on 04/04/2016.
 */
public class BuildingTile extends NonBuildableTile implements ITileEntityProvider {

    public BuildingTile(String identity) {
        super(identity);
    }

    @Override
    public TileEntity createTileEntity(World world, int tileWorldPosX, int tileWorldPosZ) {
        TileEntityBuilding te = new TileEntityBuilding();
        te.setWorld(world);
        return te;
    }

    @Override
    public boolean shouldUseDefaultRenderer () {
        return false;
    }
}
