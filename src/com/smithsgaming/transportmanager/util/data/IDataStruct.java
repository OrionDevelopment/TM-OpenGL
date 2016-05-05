package com.smithsgaming.transportmanager.util.data;

/**
 * @Author Marc (Created on: 25.04.2016)
 */
public interface IDataStruct {
    /**
     * Method to get the Key of the DataStruct
     *
     * @return The key of this DataStruct
     */
    String getKey ();

    /**
     * Method to get the Chained DataStruct of this DataStruct
     *
     * @return The Chained Value of this DataStruct.
     */
    IDataStruct getValue ();
}
