

package com.smithsgaming.transportmanager.network.server;

import io.netty.bootstrap.*;
import io.netty.channel.*;
import io.netty.channel.local.*;
import io.netty.channel.nio.*;
import io.netty.channel.socket.nio.*;
import io.netty.handler.logging.*;

/**
 * Created by marcf on 3/14/2016.
 */
public class TMNetworkingServer implements Runnable {

    private int hostPort;

    public TMNetworkingServer(int hostPort) {
        this.hostPort = hostPort;
    }

    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new TMNetworkingServerHandler());

            b.localAddress(LocalAddress.ANY).bind(hostPort).syncUninterruptibly();
        } catch (Exception e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
        }
    }
}
