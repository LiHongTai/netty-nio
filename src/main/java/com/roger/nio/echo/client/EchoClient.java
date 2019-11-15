package com.roger.nio.echo.client;

import com.roger.nio.echo.init.EchoClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception{
        //1 创建EventLoopGroup 事件循环处理组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //2 创建 Bootstrap
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)//3 指定需要使用的Nio传输Channel
                    .remoteAddress(host,port)//4 设置服务器的InetSocketAddress
                    .handler(new EchoClientChannelInitializer());//5 添加自定义的ChannelHandler到子Channel的ChannelPipeline

            //6 连接到远程节点 调用sync()方法阻塞等待直到绑定完成
            ChannelFuture channelFuture = bootstrap.connect().sync();
            //7 获取Channel的CloseFuture，并且阻塞当前线程直到完成
            channelFuture.channel().closeFuture().sync();
        }finally {
            //8 关闭EventLoopGroup,释放所有资源
            group.shutdownGracefully().sync();
        }
    }
}
