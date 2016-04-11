

package com.smithsgaming.transportmanager.network.server;

import io.netty.channel.*;
import io.netty.channel.socket.*;
import io.netty.handler.codec.serialization.*;
import io.netty.handler.logging.*;

/**
 * Created by marcf on 3/14/2016.
 */
public class TMNetworkingServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();

        channelPipeline.addLast(new ObjectEncoder());
        channelPipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())));
        channelPipeline.addLast(new TMNetworkingServerHandler());

        System.out.println("Server channel pipeline initialized.");
    }
}
