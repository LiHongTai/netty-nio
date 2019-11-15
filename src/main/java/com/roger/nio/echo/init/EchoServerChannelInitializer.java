package com.roger.nio.echo.init;

import com.roger.nio.echo.handler.EchoServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class EchoServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    final EchoServerHandler echoServerHandler = new EchoServerHandler();

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(echoServerHandler);
    }
}
