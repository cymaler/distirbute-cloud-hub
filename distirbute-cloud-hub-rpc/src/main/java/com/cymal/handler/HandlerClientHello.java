package com.cymal.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * 客户端一般继承SimpleChannelInBoundHandler，该类有丰富的方法，心跳，超时监测，连接状态等。
 */
@ChannelHandler.Sharable  // 线程安全
public class HandlerClientHello extends SimpleChannelInboundHandler<ByteBuf> {

    /**
     * 读取消息
     * @param channelHandlerContext 通道上线问，代指channel
     * @param byteBuf 字节序列，通过ByteBuf操作基础的字节数组和缓冲区，因为JDK原生操作字节麻烦，效率低，所以Netty对其进行了封装实现了指数级的效率增长
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("收到消息：" + byteBuf.toString(StandardCharsets.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
