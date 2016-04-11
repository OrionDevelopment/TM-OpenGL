package com.smithsgaming.transportmanager.network.message;

import com.smithsgaming.transportmanager.client.TransportManagerClient;
import com.smithsgaming.transportmanager.client.event.TMClientEvent;
import com.smithsgaming.transportmanager.util.Side;
import io.netty.channel.Channel;

import java.io.Serializable;

/**
 * Created by Tim on 11/04/2016.
 */
public class ClientEventProcessMessage extends TMNetworkingMessage implements Serializable {

    private TMClientEvent event;

    public ClientEventProcessMessage(TMClientEvent event) {
        this.event = event;
    }

    @Override
    public TMNetworkingMessage onReceived(Channel channel, Side side) {
        if (side == Side.CLIENT) {
            TransportManagerClient.instance.registerEvent(event);
        }
        return null;
    }
}
