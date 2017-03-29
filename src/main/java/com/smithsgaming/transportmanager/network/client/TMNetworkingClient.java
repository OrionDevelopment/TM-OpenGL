

package com.smithsgaming.transportmanager.network.client;

import com.smithsgaming.transportmanager.network.message.TMNetworkingMessage;
import com.smithsgaming.transportmanager.util.Definitions;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by marcf on 3/13/2016.
 */
public class TMNetworkingClient implements Runnable {

    public static final Logger clientNetworkLogger = LogManager.getLogger(Definitions.Loggers.NETWORKCLIENT);
    private static Channel activeComChannel;
    private String host;
    private int hostPort;

    public TMNetworkingClient(String host, int hostPort) {
        this.host = host;
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
        EventLoopGroup nioGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(nioGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new TMNetworkingClientInitializer());

            b.connect(host, hostPort).syncUninterruptibly();
        } catch (Exception e) {
            clientNetworkLogger.error(e);
            nioGroup.shutdownGracefully();
        }
    }
}
