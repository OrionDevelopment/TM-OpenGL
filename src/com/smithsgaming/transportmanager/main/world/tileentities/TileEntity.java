package com.smithsgaming.transportmanager.main.world.tileentities;

import com.smithsgaming.transportmanager.main.world.entity.AbstractEntity;
import com.smithsgaming.transportmanager.main.world.saveable.ISavable;
import com.smithsgaming.transportmanager.main.world.tiles.Tile;
import org.jnbt.*;

import java.io.*;
import java.util.*;

/**
 * Created by marcf on 3/13/2016.
 */
public abstract class TileEntity extends AbstractEntity implements Serializable, ISavable {

    public TileEntity() {
    }

    public abstract Tile getTile();

}
