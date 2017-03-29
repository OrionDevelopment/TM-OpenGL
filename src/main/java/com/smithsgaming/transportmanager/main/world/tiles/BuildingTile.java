package com.smithsgaming.transportmanager.main.world.tiles;

import com.smithsgaming.transportmanager.main.tileentity.ITileEntityProvider;
import com.smithsgaming.transportmanager.main.tileentity.TileEntity;
import com.smithsgaming.transportmanager.main.tileentity.TileEntityBuilding;
import com.smithsgaming.transportmanager.main.world.World;

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
