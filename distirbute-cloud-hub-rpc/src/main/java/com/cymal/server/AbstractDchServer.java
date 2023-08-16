package com.cymal.server;

import com.cymal.codec.DchCodec;
import com.cymal.compress.DchCompress;
import com.cymal.properties.DchServerProperties;
import com.cymal.protocol.decoder.DchReqBodyDecoder;
import com.cymal.protocol.decoder.DchReqProtocolDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 服务端
 * 1. 如何做路由
 * 2.
 */
public abstract class AbstractDchServer implements DchServer{

    private final DchCodec dchCodec;
    private final DchCompress dchCompress;
    private final DchServerProperties dchServerProperties;

    protected AbstractDchServer(DchServerProperties dchServerProperties, DchCodec dchCodec) {
        this.dchServerProperties = dchServerProperties;
        this.dchCodec = dchCodec;
        this.dchCompress = null;
    }

    protected AbstractDchServer(DchServerProperties dchServerProperties, DchCodec dchCodec, DchCompress dchCompress) {
        this.dchServerProperties = dchServerProperties;
        this.dchCodec = dchCodec;
        this.dchCompress = dchCompress;
    }

    @Override
    public void run() throws Exception {
        // boss线程池处理连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // work线程池处理读/写操作
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 服务端启动器
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)  // 设置线程池组：boss线程池组， work线程池组
                    .channel(NioServerSocketChannel.class)  // 通道类型 Nio
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 责任链处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new DchReqProtocolDecoder()); // DchReqProtocol
                            pipeline.addLast(new DchReqBodyDecoder());     // List<Object>
                        }
                    });
            ChannelFuture future = bootstrap.bind(dchServerProperties.getPort()).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
