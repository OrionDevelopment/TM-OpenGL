

package com.smithsgaming.transportmanager.network.server;

import com.smithsgaming.transportmanager.network.message.MessageContext;
import com.smithsgaming.transportmanager.network.message.TMNetworkingMessage;
import com.smithsgaming.transportmanager.util.Side;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by marcf on 3/14/2016.
 */
public class TMNetworkingServerHandler extends SimpleChannelInboundHandler<TMNetworkingMessage> {

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, TMNetworkingMessage tmNetworkingMessage) throws Exception {
        TMNetworkingServer.serverNetworkLogger.info("Networking: Handling: " + tmNetworkingMessage.toString());
        TMNetworkingMessage returnMessage = tmNetworkingMessage.onReceived(channelHandlerContext.channel(), Side.SERVER, new MessageContext(TMNetworkingServer.serverNetworkLogger));

        if (returnMessage != null) {
            channelHandlerContext.write(returnMessage);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        TMNetworkingServer.setActiveComChannel(ctx.channel());
        TMNetworkingServer.serverNetworkLogger.trace("Channel activation completed: " + ctx.name());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        TMNetworkingServer.setInactiveComChannel(ctx.channel());
        TMNetworkingServer.serverNetworkLogger.trace("Channel deactivation completed: " + ctx.name());
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        TMNetworkingServer.setActiveComChannel(ctx.channel());
        TMNetworkingServer.serverNetworkLogger.trace("Channel registration completed: " + ctx.name());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        TMNetworkingServer.setInactiveComChannel(ctx.channel());
        TMNetworkingServer.serverNetworkLogger.trace("Channel unregistration completed: " + ctx.name());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        TMNetworkingServer.serverNetworkLogger.trace("Channel read Completed: " + ctx.name() + ". Flushed Contents");
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
        TMNetworkingServer.serverNetworkLogger.error(cause);
    }
}
