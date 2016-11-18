package com.smithsgaming.transportmanager.client.event;

import com.smithsgaming.transportmanager.client.TransportManagerClient;
import com.smithsgaming.transportmanager.network.client.TMNetworkingClient;
import com.smithsgaming.transportmanager.network.message.CrudeDataRequestMessage;
import com.smithsgaming.transportmanager.util.network.Side;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class EventClientConnected extends TMClientEvent {

    @Override
    public void processEvent (Side side) {
        TransportManagerClient.clientLogger.info("Client successfully connected requesting world data.");
        TMNetworkingClient.sendMessage(new CrudeDataRequestMessage(CrudeDataRequestMessage.DataType.WORLD));
    }
}
