

package com.smithsgaming.transportmanager.network.message;

import org.jnbt.*;

import java.io.*;

/**
 * Created by marcf on 3/14/2016.
 */
public abstract class NBTPayloadMessage extends TMNetworkingMessage {
    private Tag nbtTag;

    public NBTPayloadMessage() {
    }

    public NBTPayloadMessage(Tag payload) {
        nbtTag = payload;
    }

    public Tag getPayLoad() {
        return nbtTag;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        NBTOutputStream stream = new NBTOutputStream(out);
        stream.writeTag(nbtTag);
        stream.close();
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        NBTInputStream stream = new NBTInputStream(in);
        nbtTag = stream.readTag();
        stream.close();
    }
}
