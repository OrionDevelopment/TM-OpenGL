

package com.smithsgaming.transportmanager.network.message;

import io.netty.channel.*;
import org.jnbt.*;

/**
 * Created by marcf on 3/14/2016.
 */
public class NBTPayloadMessage extends TMNetworkingMessage {
    private Tag nbtTag;

    public NBTPayloadMessage() {
    }

    public NBTPayloadMessage(Tag payload) {
        nbtTag = payload;
    }

    public Tag getPayLoad() {
        return nbtTag;
    }

    @Override
    public TMNetworkingMessage onReceived(Channel channel, NetworkingSide side) {
        System.out.println("Message Handled!");

        System.err.println(nbtTag.toString());

        return null;
    }
}
