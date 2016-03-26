package com.smithsgaming.transportmanager.main.player;

import com.google.common.collect.*;

import java.nio.channels.*;
import java.util.*;

/**
 * Created by marcf on 3/13/2016.
 */
public class PlayerManager {
    public static PlayerManager instance = new PlayerManager();

    public ArrayList<GamePlayer> connectedPlayers = new ArrayList<>();

    protected PlayerManager() {
    }

    /**
     * Method to register a new PLAYER.
     *
     * @param player The new PLAYER.
     */
    public void onPlayerConnected(GamePlayer player) {
        connectedPlayers.add(player);
    }

    /**
     * Method to unregister a connected PLAYER.
     *
     * @param player The player to unregister.
     */
    public void onPlayerDisconnteced(GamePlayer player) {
        connectedPlayers.remove(player);
    }

    /**
     * Method to get a PLAYER for a given ComChannel.
     *
     * @param playerChannel The channel you want the PLAYER for.
     * @return The player with that channel, or null if none is registered.
     */
    public GamePlayer getPlayerForChannel(Channel playerChannel) {
        for (GamePlayer player : connectedPlayers) {
            if (player.getConnectionChannel() == playerChannel)
                return player;
        }

        return null;
    }

    public ImmutableList<GamePlayer> getConnectedPlayers() {
        ImmutableList.Builder<GamePlayer> builder = new ImmutableList.Builder<GamePlayer>();
        builder.addAll(connectedPlayers);

        return builder.build();
    }
}
