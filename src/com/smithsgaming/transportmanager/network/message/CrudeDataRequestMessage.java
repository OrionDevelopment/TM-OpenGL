package com.smithsgaming.transportmanager.network.message;

import com.smithsgaming.transportmanager.main.core.WorldManager;
import com.smithsgaming.transportmanager.util.*;
import io.netty.channel.*;

import java.io.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class CrudeDataRequestMessage extends TMNetworkingMessage {
    DataType type;

    public CrudeDataRequestMessage (DataType type) {
        this.type = type;
    }

    public CrudeDataRequestMessage () {
    }

    @Override
    public TMNetworkingMessage onReceived (Channel channel, Side side) {
        if (side == Side.SERVER) {
            if (type == DataType.WORLD) {
                return new WorldCoreDataMessage(WorldManager.instance.getOvergroundWorld().getCoreData());
            }
        }

        return null;
    }

    public enum DataType implements Serializable {
        WORLD
    }
}
