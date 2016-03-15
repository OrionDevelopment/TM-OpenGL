package com.smithsgaming.transportmanager.main.world.tiles;

import com.smithsgaming.transportmanager.main.world.*;
import com.smithsgaming.transportmanager.main.world.tileentities.*;

/**
 * Created by marcf on 3/13/2016.
 */
public interface ITileEntityProvider {

    TileEntity generateNewDefaultEntity(World world, int tileWorldPosX, int tileWorldPosY, int tileWorldPosZ);
}
