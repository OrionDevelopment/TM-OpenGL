package com.smithsgaming.transportmanager.network.message;

import com.smithsgaming.transportmanager.util.*;
import io.netty.channel.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class OperationAcceptedMessage extends TMNetworkingMessage {

    Operation operation;

    public OperationAcceptedMessage() {}

    public OperationAcceptedMessage(Operation operation) {
        this.operation = operation;
    }

    @Override
    public TMNetworkingMessage onReceived (Channel channel, NetworkingSide side) {
        System.out.println("Serversided operation completed: " + operation.toString());

        return null;
    }

    public enum Operation
    {
        PLAYERCONNECTED,
        PLAYERDISCONNEDTED
    }
}
