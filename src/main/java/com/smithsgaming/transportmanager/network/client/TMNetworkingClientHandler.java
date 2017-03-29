

package com.smithsgaming.transportmanager.network.client;

import com.smithsgaming.transportmanager.main.player.GamePlayer;
import com.smithsgaming.transportmanager.network.message.ConnectClient;
import com.smithsgaming.transportmanager.network.message.MessageContext;
import com.smithsgaming.transportmanager.network.message.TMNetworkingMessage;
import com.smithsgaming.transportmanager.util.network.Side;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by marcf on 3/13/2016.
 */
public class TMNetworkingClientHandler extends SimpleChannelInboundHandler<TMNetworkingMessage> {

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, TMNetworkingMessage tmNetworkingMessage) throws Exception {
        TMNetworkingClient.clientNetworkLogger.info("Networking: Handling: " + tmNetworkingMessage.toString());
        TMNetworkingMessage returnMessage = tmNetworkingMessage.onReceived(channelHandlerContext.channel(), Side.CLIENT, new MessageContext(TMNetworkingClient.clientNetworkLogger));

        if (returnMessage != null) {
            channelHandlerContext.write(returnMessage);
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        TMNetworkingClient.clientNetworkLogger.error(cause);
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        TMNetworkingClient.setActiveComChannel(ctx.channel());
        ctx.writeAndFlush(new ConnectClient(GamePlayer.current));

        TMNetworkingClient.clientNetworkLogger.trace("Channel activation Completed: " + ctx.name() + ". Flushed Contents");
    }

    @Override
    public void channelReadComplete (ChannelHandlerContext ctx) throws Exception {
        ctx.flush();

        TMNetworkingClient.clientNetworkLogger.trace("Channel read Completed: " + ctx.name() + ". Flushed Contents");
    }
}
