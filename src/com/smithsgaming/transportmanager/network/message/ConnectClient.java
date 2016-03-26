package com.smithsgaming.transportmanager.network.message;

import com.smithsgaming.transportmanager.main.player.*;
import com.smithsgaming.transportmanager.util.*;
import io.netty.channel.*;

/**
 * @Author Marc (Created on: 15.03.2016)
 */
public class ConnectClient extends TMNetworkingMessage
{
    GamePlayer connectingPlayer;

    public ConnectClient() {}

    public ConnectClient (GamePlayer connectingPlayer) {
        this.connectingPlayer = connectingPlayer;
    }

    @Override
    public TMNetworkingMessage onReceived (Channel channel, Side side) {
        connectingPlayer = new GamePlayer(connectingPlayer.getDisplayName(), channel);
        PlayerManager.instance.onPlayerConnected(connectingPlayer);
        System.out.println("[Server] PLAYER: " + connectingPlayer.getDisplayName() + " connected!");

        return new OperationAcceptedMessage(OperationAcceptedMessage.Operation.PLAYERCONNECTED);
    }
}
