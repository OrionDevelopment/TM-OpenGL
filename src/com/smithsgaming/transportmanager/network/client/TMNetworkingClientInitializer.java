

package com.smithsgaming.transportmanager.network.client;

import io.netty.channel.*;
import io.netty.channel.socket.*;
import io.netty.handler.codec.serialization.*;

/**
 * Created by marcf on 3/14/2016.
 */
public class TMNetworkingClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();

        channelPipeline.addLast(new ObjectEncoder());
        channelPipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())));
        channelPipeline.addLast(new TMNetworkingClientHandler());

        System.out.println("Client channel pipeline initialized.");
    }
}
