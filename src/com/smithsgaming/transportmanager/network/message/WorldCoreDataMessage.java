package com.smithsgaming.transportmanager.network.message;

import com.smithsgaming.transportmanager.client.world.*;
import com.smithsgaming.transportmanager.main.world.*;
import com.smithsgaming.transportmanager.util.*;
import io.netty.channel.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class WorldCoreDataMessage extends TMNetworkingMessage {

    WorldCoreData coreData;

    public WorldCoreDataMessage (WorldCoreData coreData) {
        this.coreData = coreData;
    }

    public WorldCoreDataMessage () {
    }

    @Override
    public TMNetworkingMessage onReceived (Channel channel, Side side) {
        if (side == Side.CLIENT) {
            WorldClientManager.instance.initializeWorld(coreData);

        }

        return null;
    }
}
