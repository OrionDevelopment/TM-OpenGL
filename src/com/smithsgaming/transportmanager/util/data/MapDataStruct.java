package com.smithsgaming.transportmanager.util.data;

import com.google.common.collect.*;

import java.util.*;

/**
 * @Author Marc (Created on: 25.04.2016)
 */
public class MapDataStruct implements IDataStruct {

    private String key;
    private HashMap<String, IDataStruct> mappedChilds = new HashMap<>();

    public MapDataStruct (String key) {
        this.key = key;
    }

    @Override
    public String getKey () {
        return key;
    }

    @Override
    public IDataStruct getValue () {
        return this;
    }

    /**
     * Method to add a new piece of data to this DataStruct
     *
     * @param newData The DataStruct to add.
     */
    public void storeData (IDataStruct newData) {
        mappedChilds.put(newData.getKey(), newData);
    }

    /**
     * Method to remove a stored DataStruct if it exists.
     *
     * @param key The key of the DataStruct to remove.
     */
    public void removeData (String key) {
        mappedChilds.remove(key);
    }

    /**
     * Method to remove a stored DataStruct if it exists.
     *
     * @param dataStruct The struct you want to remove.
     */
    public void removeData (IDataStruct dataStruct) {
        removeData(dataStruct.getKey());
    }

    /**
     * Method to get a ReadOnly version of the ChildrenMap.
     *
     * @return A Immutable version of the ChildrenMap.
     */
    public ImmutableMap<String, IDataStruct> getData () {
        return ImmutableMap.copyOf(mappedChilds);
    }


}
