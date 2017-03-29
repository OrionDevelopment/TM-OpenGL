package com.smithsgaming.transportmanager.main.tileentity;

import com.smithsgaming.transportmanager.main.entity.AbstractEntity;
import com.smithsgaming.transportmanager.main.saveable.ISavable;
import com.smithsgaming.transportmanager.main.world.tiles.Tile;

import java.io.Serializable;

/**
 * Created by marcf on 3/13/2016.
 */
public abstract class TileEntity extends AbstractEntity implements Serializable, ISavable {

    public TileEntity() {
    }

    public abstract Tile getTile();

}
