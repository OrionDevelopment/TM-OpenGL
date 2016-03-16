

package com.smithsgaming.transportmanager.network.client;


import com.smithsgaming.transportmanager.main.player.*;
import com.smithsgaming.transportmanager.network.message.*;
import com.smithsgaming.transportmanager.util.*;
import io.netty.channel.*;

/**
 * Created by marcf on 3/13/2016.
 */
public class TMNetworkingClientHandler extends SimpleChannelInboundHandler<TMNetworkingMessage> {

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, TMNetworkingMessage tmNetworkingMessage) throws Exception {
        System.out.println("[Client] Networking: Handling: " + tmNetworkingMessage.toString());
        TMNetworkingMessage returnMessage = tmNetworkingMessage.onReceived(channelHandlerContext.channel(), Side.CLIENT);

        if (returnMessage != null) {
            channelHandlerContext.write(returnMessage);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        TMNetworkingClient.setActiveComChannel(ctx.channel());
        ctx.writeAndFlush(new ConnectClient(GamePlayer.current));
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
