package com.smithsgaming.transportmanager.main.world.tileentities;

import com.smithsgaming.transportmanager.main.entity.*;
import com.smithsgaming.transportmanager.main.saveable.*;
import com.smithsgaming.transportmanager.main.world.tiles.*;

import java.io.*;

/**
 * Created by marcf on 3/13/2016.
 */
public abstract class TileEntity extends AbstractEntity implements Serializable, ISavable {

    public TileEntity() {
    }

    public abstract Tile getTile();

}
