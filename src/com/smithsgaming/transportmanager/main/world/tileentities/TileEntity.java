package com.smithsgaming.transportmanager.main.world.tileentities;

import org.jnbt.*;

import java.io.*;
import java.util.*;

/**
 * Created by marcf on 3/13/2016.
 */
public abstract class TileEntity implements Serializable {

    private String identity;

    protected TileEntity(String identity) {
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }

    public abstract Map<String, Tag> writeDataToDisk();

    public abstract void readDataFromDisk(Map<String, Tag> data);
}
