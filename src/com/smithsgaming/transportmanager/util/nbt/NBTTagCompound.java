package com.smithsgaming.transportmanager.util.nbt;

import org.jnbt.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tim on 27/03/2016.
 */
public class NBTTagCompound {

    private Map<String, Tag> dataMap;

    public NBTTagCompound() {
        dataMap = new HashMap<>();
    }

    public NBTTagCompound(CompoundTag tag) {
        dataMap = tag.getValue();
    }

    public void writeByte(String tagName, byte value) {
        if (dataMap.containsKey(tagName)) {
            throw new NBTError("The TagCompound already has that tag specified");
        }
        dataMap.put(tagName, new ByteTag(tagName, value));
    }

    public void writeByteArray(String tagName, byte[] value) {
        if (dataMap.containsKey(tagName)) {
            throw new NBTError("The TagCompound already has that tag specified");
        }
        dataMap.put(tagName, new ByteArrayTag(tagName, value));
    }

    public void writeInt(String tagName, int value) {
        if (dataMap.containsKey(tagName)) {
            throw new NBTError("The TagCompound already has that tag specified");
        }
        dataMap.put(tagName, new IntTag(tagName, value));
    }

    public void writeFloat(String tagName, float value) {
        if (dataMap.containsKey(tagName)) {
            throw new NBTError("The TagCompound already has that tag specified");
        }
        dataMap.put(tagName, new FloatTag(tagName, value));
    }

    public void writeDouble(String tagName, double value) {
        if (dataMap.containsKey(tagName)) {
            throw new NBTError("The TagCompound already has that tag specified");
        }
        dataMap.put(tagName, new DoubleTag(tagName, value));
    }

    public void writeLong(String tagName, long value) {
        if (dataMap.containsKey(tagName)) {
            throw new NBTError("The TagCompound already has that tag specified");
        }
        dataMap.put(tagName, new LongTag(tagName, value));
    }

    public void writeShort(String tagName, short value) {
        if (dataMap.containsKey(tagName)) {
            throw new NBTError("The TagCompound already has that tag specified");
        }
        dataMap.put(tagName, new ShortTag(tagName, value));
    }

    public void writeBoolean(String tagName, boolean value) {
        if (dataMap.containsKey(tagName)) {
            throw new NBTError("The TagCompound already has that tag specified");
        }
        writeByte(tagName, (byte) (value == true ? 1 : 0));
    }

    public void writeString(String tagName, String value) {
        if (dataMap.containsKey(tagName)) {
            throw new NBTError("The TagCompound already has that tag specified");
        }
        dataMap.put(tagName, new StringTag(tagName, value));
    }

    public void writeCompoundTag(String tagName, CompoundTag value) {
        if (dataMap.containsKey(tagName)) {
            throw new NBTError("The TagCompound already has that tag specified");
        }
        dataMap.put(tagName, value);
    }

    public byte getByte(String tagName) {
        if (!dataMap.containsKey(tagName)) {
            throw new NBTError("The TagCompound does not contain the specified key");
        }
        Tag tag = dataMap.get(tagName);
        if (tag instanceof ByteTag) {
            return ((ByteTag) tag).getValue();
        } else {
            throw new NBTError("The data at the specified key was of an incorrect type");
        }
    }

    public byte[] getByteArray(String tagName) {
        if (!dataMap.containsKey(tagName)) {
            throw new NBTError("The TagCompound does not contain the specified key");
        }
        Tag tag = dataMap.get(tagName);
        if (tag instanceof ByteArrayTag) {
            return ((ByteArrayTag) tag).getValue();
        } else {
            throw new NBTError("The data at the specified key was of an incorrect type");
        }
    }

    public int getInt(String tagName) {
        if (!dataMap.containsKey(tagName)) {
            throw new NBTError("The TagCompound does not contain the specified key");
        }
        Tag tag = dataMap.get(tagName);
        if (tag instanceof IntTag) {
            return ((IntTag) tag).getValue();
        } else {
            throw new NBTError("The data at the specified key was of an incorrect type");
        }
    }

    public float getFloat(String tagName) {
        if (!dataMap.containsKey(tagName)) {
            throw new NBTError("The TagCompound does not contain the specified key");
        }
        Tag tag = dataMap.get(tagName);
        if (tag instanceof FloatTag) {
            return ((FloatTag) tag).getValue();
        } else {
            throw new NBTError("The data at the specified key was of an incorrect type");
        }
    }

    public double getDouble(String tagName) {
        if (!dataMap.containsKey(tagName)) {
            throw new NBTError("The TagCompound does not contain the specified key");
        }
        Tag tag = dataMap.get(tagName);
        if (tag instanceof DoubleTag) {
            return ((DoubleTag) tag).getValue();
        } else {
            throw new NBTError("The data at the specified key was of an incorrect type");
        }
    }

    public long getLong(String tagName) {
        if (!dataMap.containsKey(tagName)) {
            throw new NBTError("The TagCompound does not contain the specified key");
        }
        Tag tag = dataMap.get(tagName);
        if (tag instanceof LongTag) {
            return ((LongTag) tag).getValue();
        } else {
            throw new NBTError("The data at the specified key was of an incorrect type");
        }
    }

    public short getShort(String tagName) {
        if (!dataMap.containsKey(tagName)) {
            throw new NBTError("The TagCompound does not contain the specified key");
        }
        Tag tag = dataMap.get(tagName);
        if (tag instanceof ShortTag) {
            return ((ShortTag) tag).getValue();
        } else {
            throw new NBTError("The data at the specified key was of an incorrect type");
        }
    }

    public boolean getBoolean(String tagName) {
        if (!dataMap.containsKey(tagName)) {
            throw new NBTError("The TagCompound does not contain the specified key");
        }
        Tag tag = dataMap.get(tagName);
        if (tag instanceof ByteTag) {
            return ((ByteTag) tag).getValue() == 0 ? false : true;
        } else {
            throw new NBTError("The data at the specified key was of an incorrect type");
        }
    }

    public String getString(String tagName) {
        if (!dataMap.containsKey(tagName)) {
            throw new NBTError("The TagCompound does not contain the specified key");
        }
        Tag tag = dataMap.get(tagName);
        if (tag instanceof StringTag) {
            return ((StringTag) tag).getValue();
        } else {
            throw new NBTError("The data at the specified key was of an incorrect type");
        }
    }

    public CompoundTag getTagCompound(String tagName) {
        if (!dataMap.containsKey(tagName)) {
            throw new NBTError("The TagCompound does not contain the specified key");
        }
        Tag tag = dataMap.get(tagName);
        if (tag instanceof CompoundTag) {
            return (CompoundTag) tag;
        } else {
            throw new NBTError("The data at the specified key was of an incorrect type");
        }
    }

    public CompoundTag toCompoundTag(String identifier) {
        return new CompoundTag("COMPOUND_TAG_" + identifier, dataMap);
    }
}
