

package com.smithsgaming.transportmanager.main.core;

import com.smithsgaming.transportmanager.main.TransportManager;
import com.smithsgaming.transportmanager.main.tileentity.TileEntity;

import java.util.*;

/**
 * Created by marcf on 3/13/2016.
 */
public class TileEntityRegistry {

    public static final String NULLTILEIDENTITY = "tile.null";
    public static TileEntityRegistry instance = new TileEntityRegistry();

    private HashMap<String, Class<? extends TileEntity>> tileHashMap = new HashMap<>();

    protected TileEntityRegistry() {
        tileHashMap.put(NULLTILEIDENTITY, null);
    }

    /**
     * Method to initialize the TileRegistry.
     */
    public static void init() {

    }

    /**
     * Method to get a Tile for a given Identity name;
     *
     * @param identity The identity you are trying to get a lookup for.
     * @return The tile registered with the TileRegistry or null if nothing is registered with that name.
     */
    public TileEntity getTileEntityForIdentity(String identity) {
        Class<? extends TileEntity> clazz = tileHashMap.get(identity);

        if (clazz == null)
            return null;

        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Method to register a Tile. If two tiles with the same identity are being registered the last one is being kept.
     *
     * @param tileEntityClass The tile to register.
     */
    public void registerTile(Class<? extends TileEntity> tileEntityClass) {
        TileEntity tileEntity = null;

        try {
            tileEntity = tileEntityClass.newInstance();
        } catch (InstantiationException e) {
            TransportManager.serverLogger.error("Failed to register: " + tileEntityClass + " as TileEntity!\n" + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            TransportManager.serverLogger.error("Failed to register: " + tileEntityClass + " as TileEntity!\n" + e.getLocalizedMessage());
            e.printStackTrace();
        }

        tileHashMap.put(tileEntity.getClass().getTypeName(), tileEntityClass);
    }

    /**
     * Class holds the identities of all tileentity. used in the Game.
     */
    public static class TileEntityNames {

    }


}


