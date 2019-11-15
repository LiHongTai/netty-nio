package com.roger.nio.echo.init;

import com.roger.nio.echo.handler.EchoClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class EchoClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    final EchoClientHandler echoClientHandler = new EchoClientHandler();

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(echoClientHandler);
    }
}
