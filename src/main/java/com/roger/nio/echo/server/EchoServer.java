package com.roger.nio.echo.server;

import com.roger.nio.echo.init.EchoServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

//服务端
@Slf4j
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws Exception{
        //1 创建EventLoopGroup 事件循环处理组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //2 创建ServerBootstrap
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group)
            .channel(NioServerSocketChannel.class)//3 指定需要使用的Nio传输Channel
            .localAddress(port)//4 使用指定的端口设置套接字地址
            .childHandler(new EchoServerChannelInitializer());//5 添加自定义的ChannelHandler到子Channel的ChannelPipeline

            //6 异步地绑定服务器 调用sync()方法阻塞等待直到绑定完成
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            //7 获取Channel的CloseFuture，并且阻塞当前线程直到完成
            channelFuture.channel().closeFuture().sync();
        }finally {
            //8 关闭EventLoopGroup,释放所有资源
            group.shutdownGracefully().sync();
        }
    }
}
