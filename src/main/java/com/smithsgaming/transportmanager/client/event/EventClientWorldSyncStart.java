package com.smithsgaming.transportmanager.client.event;

import com.smithsgaming.transportmanager.client.TransportManagerClient;
import com.smithsgaming.transportmanager.util.network.Side;

/**
 * Created by Tim on 01/04/2016.
 */
public class EventClientWorldSyncStart extends TMClientEvent {

    @Override
    public void processEvent(Side side) {
        TransportManagerClient.instance.startConnection();
    }

}
