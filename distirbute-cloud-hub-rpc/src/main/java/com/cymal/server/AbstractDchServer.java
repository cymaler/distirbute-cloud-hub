package com.cymal.server;

import com.cymal.properties.DchServerProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ThreadFactory;

/**
 *
 */
public abstract class AbstractDchServer implements DchServer {

    private static volatile boolean runned;
    private volatile NioEventLoopGroup boss;
    private volatile NioEventLoopGroup work;
    private final DchServerProperties dchServerProperties;
    private ThreadFactory bossTf = new DefaultThreadFactory("dch_rpc_" + this.getClass().getSimpleName().toLowerCase() + "_boss");
    private ThreadFactory workTf = new DefaultThreadFactory("dch_rpc_" + this.getClass().getSimpleName().toLowerCase() + "_work");

    protected AbstractDchServer(DchServerProperties dchServerProperties) {
        this.dchServerProperties = dchServerProperties;
    }

    @Override
    public void run() throws Exception {
        boss = new NioEventLoopGroup(dchServerProperties.getBossThreadCount(), bossTf);
        work = new NioEventLoopGroup(dchServerProperties.getWorkThreadCount(), workTf);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.SO_KEEPALIVE, false)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.SO_LINGER, 0)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            AbstractDchServer.this.initChannel(socketChannel);
                        }
                    });
            ChannelFuture future = bootstrap.bind(dchServerProperties.getPort()).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e){
            stop0();
        }
    }

    private void stop0() {

    }

    protected abstract void initChannel(SocketChannel socketChannel);

    protected abstract void close() throws Exception;

}
