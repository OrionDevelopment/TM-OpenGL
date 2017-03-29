package com.smithsgaming.transportmanager.main.tileentity;

import com.smithsgaming.transportmanager.main.core.TileRegistry;
import com.smithsgaming.transportmanager.main.world.structure.BuildingProperties;
import com.smithsgaming.transportmanager.main.world.tiles.Tile;
import com.smithsgaming.transportmanager.main.world.tiles.TileNames;
import com.smithsgaming.transportmanager.util.nbt.NBTTagCompound;

import java.util.ArrayList;

/**
 * Created by Tim on 04/04/2016.
 */
public class TileEntityBuilding extends TileEntity {

    public boolean isParent;
    public Tile backgroundTile;
    private ArrayList<TileEntity> childTiles;
    private TileEntityBuilding parentTile;

    private BuildingProperties properties;

    @Override
    public Tile getTile() {
        return TileRegistry.instance.getTileForIdentity(TileNames.BUILDING);
    }

    public BuildingProperties getProperties() {
        return properties;
    }

    public void setProperties(BuildingProperties properties)
    {
        this.properties = properties;
    }

    public TileEntity getParentTile() {
        if (isParent) {
            return this;
        }
        return parentTile;
    }

    public void setParentTile(TileEntityBuilding parentTile)
    {
        if (!isParent)
        {
            this.parentTile = parentTile;
        }
    }

    public void addChildTile(TileEntityBuilding child) {
        if (isParent) {
            if (childTiles == null) {
                childTiles = new ArrayList<>();
            }
            childTiles.add(child);
        }
    }

    @Override
    public void update()
    {
    }

    @Override
    public void writeToDisk(NBTTagCompound tag) {
        super.writeToDisk(tag);
        tag.writeBoolean("parent", isParent);
        tag.writeInt("buildingProperties", properties.ordinal());
        tag.writeString("backgroundTile", TileRegistry.instance.getIdentityForTile(backgroundTile));
        if (!isParent) {
            tag.writeInt("parentX", parentTile.xPos);
            tag.writeInt("parentY", parentTile.yPos);
        }
        if (childTiles != null && isParent) {
            NBTTagCompound child = new NBTTagCompound();
            child.writeInt("childSize", childTiles.size());
            for (int i = 0; i < childTiles.size(); i++) {
                child.writeInt("child_" + i + "_x", childTiles.get(i).xPos);
                child.writeInt("child_" + i + "_y", childTiles.get(i).yPos);
            }
            tag.writeCompoundTag("children", child.toCompoundTag("children"));
        }
    }

    @Override
    public void loadFromDisk(NBTTagCompound tag) {
        super.loadFromDisk(tag);
        isParent = tag.getBoolean("parent");
        properties = BuildingProperties.values()[tag.getInt("buildingProperties")];
        backgroundTile = TileRegistry.instance.getTileForIdentity(tag.getString("backgroundTile"));
        if (!isParent) {
            parentTile = (TileEntityBuilding) world.getTileEntityAtPos(tag.getInt("parentX"), tag.getInt("parentY"));
        } else {
            parentTile = this;
        }
        if (isParent && tag.getTagCompound("children") != null) {
            NBTTagCompound children = new NBTTagCompound(tag.getTagCompound("children"));
            int childSize = children.getInt("childSize");
            childTiles = new ArrayList<>();
            for (int i = 0; i < childSize; i++) {
                childTiles.add(world.getTileEntityAtPos(children.getInt("child_" + i + "_x"), children.getInt("child_" + i + "_y")));
            }
        }
    }
}
