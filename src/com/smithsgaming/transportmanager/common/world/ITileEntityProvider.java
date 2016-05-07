package com.smithsgaming.transportmanager.common.world;

import com.smithsgaming.transportmanager.util.common.*;

/**
 * @Author Marc (Created on: 07.05.2016)
 */
public interface ITileEntityProvider extends ITile {

    ITileEntity getDefaultTileEntity (WorldPosition position);
}
