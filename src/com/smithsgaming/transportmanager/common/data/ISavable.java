package com.smithsgaming.transportmanager.common.data;

import com.smithsgaming.transportmanager.util.data.*;

/**
 * @Author Marc (Created on: 25.04.2016)
 */
public interface ISavable {

    /**
     * Method called when this Savable is written to Disk.
     *
     * @return A IDataStruct filled with the required data that is needed to be stored on Disk.
     */
    IDataStruct onWrite ();

    /**
     * Method called when reading a instance of a implementing type from disk.
     *
     * @param dataStruct The stored DataStruct for this instance.
     */
    void onRead (IDataStruct dataStruct);
}
