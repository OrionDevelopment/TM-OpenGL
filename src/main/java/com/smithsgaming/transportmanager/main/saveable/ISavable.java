package com.smithsgaming.transportmanager.main.saveable;

import com.smithsgaming.transportmanager.util.nbt.NBTTagCompound;

/**
 * Created by Tim on 26/03/2016.
 */
public interface ISavable {

    void writeToDisk (NBTTagCompound tag);

    void loadFromDisk (NBTTagCompound tag);

}
