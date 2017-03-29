package com.smithsgaming.transportmanager.network.message;

import com.smithsgaming.transportmanager.client.TransportManagerClient;
import com.smithsgaming.transportmanager.client.event.EventClientConnected;
import com.smithsgaming.transportmanager.util.network.ActionProcessingResult;
import com.smithsgaming.transportmanager.util.network.Side;
import io.netty.channel.Channel;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class OperationProcessedMessage extends TMNetworkingMessage {

    Operation operation;
    ActionProcessingResult result;

    public OperationProcessedMessage() {
        result = ActionProcessingResult.ACCEPTED;
    }

    public OperationProcessedMessage(Operation operation) {
        this.operation = operation;
        this.result = ActionProcessingResult.ACCEPTED;
    }

    @Override
    public TMNetworkingMessage onReceived (Channel channel, Side side, MessageContext context) {
        context.getLogger().trace("Operation " + result + ": " + side + " - " + operation);

        if (operation == Operation.PLAYERCONNECTED)
            TransportManagerClient.instance.registerEvent(new EventClientConnected());

        return null;
    }

    public enum Operation {
        PLAYERCONNECTED,
        PLAYERDISCONNEDTED
    }
}
