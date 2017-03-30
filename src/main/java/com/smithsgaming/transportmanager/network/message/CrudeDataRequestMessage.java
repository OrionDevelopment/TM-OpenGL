/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.network.message;

import com.smithsgaming.transportmanager.main.core.WorldManager;
import com.smithsgaming.transportmanager.util.network.Side;
import io.netty.channel.Channel;

import java.io.Serializable;

/**
 *  ------ Class not Documented ------
 */
public class CrudeDataRequestMessage extends TMNetworkingMessage {
    DataType type;

    public CrudeDataRequestMessage (DataType type) {
        this.type = type;
    }

    public CrudeDataRequestMessage () {
    }

    @Override
    public TMNetworkingMessage onReceived (Channel channel, Side side, MessageContext context) {
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
