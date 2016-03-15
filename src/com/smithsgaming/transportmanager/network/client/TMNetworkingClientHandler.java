

package com.smithsgaming.transportmanager.network.client;


import com.smithsgaming.transportmanager.main.player.*;
import com.smithsgaming.transportmanager.network.message.*;
import io.netty.buffer.*;
import io.netty.channel.*;

/**
 * Created by marcf on 3/13/2016.
 */
public class TMNetworkingClientHandler extends SimpleChannelInboundHandler<TMNetworkingMessage> {

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, TMNetworkingMessage tmNetworkingMessage) throws Exception {
        TMNetworkingMessage returnMessage = tmNetworkingMessage.onReceived(channelHandlerContext.channel(), TMNetworkingMessage.NetworkingSide.CLIENT);

        if (returnMessage != null) {
            channelHandlerContext.write(returnMessage);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        TMNetworkingClient.setActiveComChannel(ctx.channel());
        ctx.writeAndFlush(new ConnectClient(GamePlayer.current));
    }

    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
