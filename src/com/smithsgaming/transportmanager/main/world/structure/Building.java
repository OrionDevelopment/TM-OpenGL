package com.smithsgaming.transportmanager.main.world.structure;

import com.smithsgaming.transportmanager.main.TransportManager;
import com.smithsgaming.transportmanager.main.saveable.ISavable;
import com.smithsgaming.transportmanager.main.tileentity.TileEntity;
import com.smithsgaming.transportmanager.main.tileentity.TileEntityBuilding;
import com.smithsgaming.transportmanager.main.world.World;
import com.smithsgaming.transportmanager.main.world.tiles.Tile;
import com.smithsgaming.transportmanager.util.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Tim on 28/03/2016.
 */
public class Building implements ISavable {

    private int xPos, zPos;
    private World world;
    public TileEntityBuilding parentTile;
    private BuildingProperties properties;
    private Random random = new Random();
    public UUID uuid;

    public Building(World world) {
        this.world = world;
    }

    public Building(World world, int x, int z, int gameLayer, BuildingProperties properties) {
        this.world = world;
        this.xPos = x;
        this.zPos = z;
        this.properties = properties;
        this.uuid = UUID.randomUUID();
    }

    public void createBuilding(TileEntityBuilding parentTile, ArrayList<Tile> backgroundTiles) {
        this.parentTile = parentTile;
        this.parentTile.isParent = true;
        for (int z = 0; z < properties.getHeight(); z++) {
            for (int x = 0; x < properties.getWidth(); x++) {
                TileEntity t = world.getCoreData().getTileEntityAtPos(xPos + x, zPos + z);
                if (t instanceof TileEntityBuilding) {
                    ((TileEntityBuilding) t).backgroundTile = backgroundTiles.get(x + z * properties.getWidth());
                    ((TileEntityBuilding) t).setParentTile(parentTile);
                    ((TileEntityBuilding) t).setProperties(properties);
                    this.parentTile.addChildTile((TileEntityBuilding) t);
                }
            }
        }
    }

    public BuildingProperties.BuildingType getBuildingType() {
        return properties.getType();
    }

    public int getWidth() {
        return properties.getWidth();
    }

    public int getHeight() {
        return properties.getHeight();
    }

    public int getConnectionIndex() {
        return properties.getConnectionIndex();
    }

    public void update() {
        if (random.nextInt(TransportManager.targetUPS * 300) == 0) {
            switch (properties.getType()) {
                case OFFICE:
                    //this.world.spawnMoneyEntity(xPos * 32, yPos * 32, gameLayer);
                    //this.world.decreaseFunds(100);
                    break;
                case RESIDENTIAL:
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void writeToDisk(NBTTagCompound tag) {
        tag.writeString("uuid", uuid.toString());
        tag.writeInt("xPos", xPos);
        tag.writeInt("zPos", zPos);
        tag.writeInt("buildingProperties", properties.ordinal());
        tag.writeInt("parentTileX", parentTile.xPos);
        tag.writeInt("parentTileZ", parentTile.yPos);
    }

    @Override
    public void loadFromDisk(NBTTagCompound tag) {
        this.uuid = UUID.fromString(tag.getString("uuid"));
        this.xPos = tag.getInt("xPos");
        this.zPos = tag.getInt("zPos");
        this.properties = BuildingProperties.values()[tag.getInt("buildingProperties")];
        this.parentTile = (TileEntityBuilding) world.getCoreData().getTileEntityAtPos(tag.getInt("parentTileX"), tag.getInt("parentTileZ"));
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Building)) {
            return false;
        }
        return (uuid.equals(((Building) obj).uuid));
    }
}
