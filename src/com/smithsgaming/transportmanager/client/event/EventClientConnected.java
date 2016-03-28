package com.smithsgaming.transportmanager.client.event;

import com.smithsgaming.transportmanager.network.client.*;
import com.smithsgaming.transportmanager.network.message.*;
import com.smithsgaming.transportmanager.util.*;
import com.smithsgaming.transportmanager.util.event.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class EventClientConnected extends TMEvent {

    @Override
    public void processEvent (Side side) {
        System.out.println("Client successfully connected requesting world data.");
        TMNetworkingClient.sendMessage(new CrudeDataRequestMessage(CrudeDataRequestMessage.DataType.WORLD));
    }
}
