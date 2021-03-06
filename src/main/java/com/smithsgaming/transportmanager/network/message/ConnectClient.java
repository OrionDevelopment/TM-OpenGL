/*
 * Copyright (c)  2015-2017 SmithsGaming Inc.
 */

package com.smithsgaming.transportmanager.network.message;

import com.smithsgaming.transportmanager.main.player.GamePlayer;
import com.smithsgaming.transportmanager.main.player.PlayerManager;
import com.smithsgaming.transportmanager.util.network.Side;
import io.netty.channel.Channel;

/**
 *  ------ Class not Documented ------
 */
public class ConnectClient extends TMNetworkingMessage {

    GamePlayer connectingPlayer;

    public ConnectClient() {
    }

    public ConnectClient(GamePlayer connectingPlayer) {
        this.connectingPlayer = connectingPlayer;
    }

    @Override
    public TMNetworkingMessage onReceived(Channel channel, Side side, MessageContext context) {
        connectingPlayer = new GamePlayer(connectingPlayer.getDisplayName(), channel);
        PlayerManager.instance.onPlayerConnected(connectingPlayer);
        context.getLogger().trace("Player: " + connectingPlayer.getDisplayName() + " connected!");

        return new OperationProcessedMessage(OperationProcessedMessage.Operation.PLAYERCONNECTED);
    }
}
