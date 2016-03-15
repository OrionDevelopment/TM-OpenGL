

package com.smithsgaming.transportmanager.network.message;

import io.netty.channel.*;

import java.io.*;

/**
 * Created by marcf on 3/13/2016.
 */
public abstract class TMNetworkingMessage implements Serializable {

    public abstract TMNetworkingMessage onReceived(Channel channel, NetworkingSide side);

    public enum NetworkingSide {
        CLIENT,
        SERVER
    }
}
