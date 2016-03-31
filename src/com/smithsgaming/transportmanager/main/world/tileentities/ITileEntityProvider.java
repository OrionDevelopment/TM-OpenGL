package com.smithsgaming.transportmanager.main.world.tileentities;

import com.smithsgaming.transportmanager.main.world.*;

/**
 * Created by marcf on 3/13/2016.
 */
public interface ITileEntityProvider {

    TileEntity createTileEntity (World world, int tileWorldPosX, int tileWorldPosZ);
}
