

package com.smithsgaming.transportmanager.network.server;

import com.smithsgaming.transportmanager.network.message.*;
import io.netty.buffer.*;
import io.netty.channel.*;

/**
 * Created by marcf on 3/14/2016.
 */
public class TMNetworkingServerHandler extends SimpleChannelInboundHandler<NBTPayloadMessage> {
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, NBTPayloadMessage tmNetworkingMessage) throws Exception {
        System.out.println("Attempting to handle NBTPayLoad...");
        TMNetworkingMessage returnMessage = tmNetworkingMessage.onReceived(channelHandlerContext.channel(), TMNetworkingMessage.NetworkingSide.SERVER);

        if (returnMessage != null) {
            channelHandlerContext.write(returnMessage);
        }
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
