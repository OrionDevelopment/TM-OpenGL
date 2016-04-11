

package com.smithsgaming.transportmanager.network.server;

import com.smithsgaming.transportmanager.network.message.TMNetworkingMessage;
import io.netty.bootstrap.*;
import io.netty.channel.*;
import io.netty.channel.local.*;
import io.netty.channel.nio.*;
import io.netty.channel.socket.nio.*;
import io.netty.handler.logging.*;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by marcf on 3/14/2016.
 */
public class TMNetworkingServer implements Runnable {


    private static CopyOnWriteArrayList<Channel> activeChannels = new CopyOnWriteArrayList<>();

    private int hostPort;

    public TMNetworkingServer(int hostPort) {
        this.hostPort = hostPort;
    }

    public static void setActiveComChannel(Channel channel) {
        activeChannels.add(channel);
    }

    public static void setInactiveComChannel(Channel channel) {
        activeChannels.remove(channel);
    }

    public static void clearActiveComChannel() {
        activeChannels = new CopyOnWriteArrayList<>();
    }

    public static boolean isConnectionEstablished() {
        return !activeChannels.isEmpty();
    }

    public static void sendMessage(TMNetworkingMessage message) {
        if (activeChannels.isEmpty()) {
            return;
        }
        for (Channel c : activeChannels) {
            c.writeAndFlush(message);
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
