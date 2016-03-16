

package com.smithsgaming.transportmanager.network.server;

import com.smithsgaming.transportmanager.network.message.*;
import com.smithsgaming.transportmanager.util.*;
import io.netty.channel.*;

/**
 * Created by marcf on 3/14/2016.
 */
public class TMNetworkingServerHandler extends SimpleChannelInboundHandler<TMNetworkingMessage> {
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, TMNetworkingMessage tmNetworkingMessage) throws Exception {
        System.out.println("[Server] Networking: Handling: " + tmNetworkingMessage.toString());
        TMNetworkingMessage returnMessage = tmNetworkingMessage.onReceived(channelHandlerContext.channel(), Side.SERVER);

        if (returnMessage != null) {
            channelHandlerContext.write(returnMessage);
        }
    }

    @Override
    public void channelReadComplete (ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
