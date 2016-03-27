package com.smithsgaming.transportmanager.main.world.saveable;

import org.jnbt.CompoundTag;

/**
 * Created by Tim on 26/03/2016.
 */
public interface ISavable {

    CompoundTag writeToDisk();

    void loadFromDisk(CompoundTag tag);

}
