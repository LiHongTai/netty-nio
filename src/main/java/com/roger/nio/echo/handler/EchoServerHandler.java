package com.roger.nio.echo.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

//表示一个Channel-Handler可以被多个Channel安全共享

/**
 * 1 针对不同类型的事件来调用 ChannelHandler；
 * 2.应用程序通过实现或者扩展 ChannelHandler 来挂钩到事件的生命周期，并且提供自
 *          定义的应用程序逻辑；
 * 3.在架构上，ChannelHandler 有助于保持业务逻辑与网络处理代码的分离。这简化了开
 *          发过程，因为代码必须不断地演化以响应不断变化的需求。
 */
@Slf4j
@ChannelHandler.Sharable//可以添加ChannelPipeline中的时候，只实例化一个对象即可
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    //对于每个传入消息都会调用该方法
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in  = (ByteBuf) msg;
        log.info("Server Received :" + in.toString(CharsetUtil.UTF_8));
        //将接收到的消息写给发送者，而不冲刷出站消息
        ctx.write(in);
    }

    //—通知ChannelInboundHandler
    // 最后一次对channelRead()的调用是当前批量读取中的最后一条消息；
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)//将未决消息冲刷远程节点
                .addListener(ChannelFutureListener.CLOSE);//并且关闭该Channel
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage());
        ctx.close();//关闭该Channel
    }
}
