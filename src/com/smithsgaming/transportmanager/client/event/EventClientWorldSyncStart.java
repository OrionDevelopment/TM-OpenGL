package com.smithsgaming.transportmanager.client.event;

import com.smithsgaming.transportmanager.client.TransportManagerClient;
import com.smithsgaming.transportmanager.util.Side;
import com.smithsgaming.transportmanager.util.event.TMEvent;

/**
 * Created by Tim on 01/04/2016.
 */
public class EventClientWorldSyncStart extends TMEvent {

    @Override
    public void processEvent(Side side) {
        TransportManagerClient.instance.startConnection();
    }

}
