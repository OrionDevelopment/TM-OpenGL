/*
 * Copyright (c) 2015-2017 SmithsGaming Inc.
 */

/*
 * Copyright (c) 2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.client.event;

import com.smithsgaming.transportmanager.client.TransportManagerClient;
import com.smithsgaming.transportmanager.network.client.TMNetworkingClient;
import com.smithsgaming.transportmanager.network.message.CrudeDataRequestMessage;
import com.smithsgaming.transportmanager.util.network.Side;

/**
 *  Event triggered when the client successfully connects to the Server.
 *  Starts the world synchronization chain.
 */
public class EventClientConnected extends TMClientEvent {

    /**
     * Processing method of this event.
     * Triggers the world synchronisation from the server to the client.
     *
     * @param side The side this event was triggered on.
     *
     * @throws IllegalArgumentException Thrown when this method is called on the
     *                                  server side.
     */
    @Override
    public void processEvent(Side side) throws IllegalArgumentException {
        if (side != Side.CLIENT) {
            throw new IllegalArgumentException("side has to be Client to trigger this event properly");
        }

        TransportManagerClient.clientLogger.info("Client successfully connected requesting world data.");
        TMNetworkingClient.sendMessage(new CrudeDataRequestMessage(CrudeDataRequestMessage.DataType.WORLD));
    }
}
