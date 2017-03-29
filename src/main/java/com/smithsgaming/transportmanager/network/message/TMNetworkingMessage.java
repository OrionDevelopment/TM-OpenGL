

package com.smithsgaming.transportmanager.network.message;

import com.smithsgaming.transportmanager.util.network.Side;
import io.netty.channel.Channel;

import java.io.Serializable;

/**
 * Created by marcf on 3/13/2016.
 */
public abstract class TMNetworkingMessage implements Serializable {

    public abstract TMNetworkingMessage onReceived (Channel channel, Side side, MessageContext context);
}
