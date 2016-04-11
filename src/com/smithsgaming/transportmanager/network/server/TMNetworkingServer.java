

package com.smithsgaming.transportmanager.network.server;

import com.smithsgaming.transportmanager.network.message.TMNetworkingMessage;
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

    private static Channel activeComChannel;
    private int hostPort;

    public TMNetworkingServer(int hostPort) {
        this.hostPort = hostPort;
    }

    public static void setActiveComChannel(Channel channel) {
        if (activeComChannel == null) {
            activeComChannel = channel;
            return;
        }

        synchronized (activeComChannel) {
            activeComChannel = channel;
        }
    }

    public static void clearActiveComChannel() {
        synchronized (activeComChannel) {
            activeComChannel = null;
        }
    }

    public static boolean isConnectionEstablished() {
        return activeComChannel != null;
    }

    public static void sendMessage(TMNetworkingMessage message) {
        if (activeComChannel == null)
            return;

        synchronized (activeComChannel) {
            if (activeComChannel != null) {
                activeComChannel.writeAndFlush(message);
            }
        }
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
                    .childHandler(new TMNetworkingServerInitializer());

            b.localAddress(LocalAddress.ANY).bind(hostPort).syncUninterruptibly();
        } catch (Exception e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
        }
    }
}
