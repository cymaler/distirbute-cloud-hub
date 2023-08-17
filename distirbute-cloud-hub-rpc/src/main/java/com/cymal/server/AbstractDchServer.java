package com.cymal.server;

import com.cymal.properties.DchServerProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Nio model is consisted by <b>selector channel buffer.</b> you should ackownledge <b>'Reactor model'</b>.<br/>
 * Tranditional Blocking IO thread model allocate thread based on every connection. if your connection don't
 * possess data, it will be blocked and cause waste of resource.<br/>
 * Reactor model improve for the disadvantage of Blocking IO：<br/>
 * <ol>
 *     <li>Base on IO multiplexing: multiple client use a thread to monitor and allocate events.</li>
 *     <li>Base on thread pool: using thread pool to allocate the resource of thread.</li>
 * </ol>
 * Reactor model consist by the following compoents：<br/>
 * <ul>
 *     <li>Reactor: the responsibity of reactor is monitoring connection and dispatching to corresponding event.</li>
 *     <li>Hanlder：handle corresponding events.</li>
 * </ul>
 */
public abstract class AbstractDchServer implements DchServer {

    /**
     * The channel is subordinated to serverbootstrap which are designed to bind port.<br/>
     */
    private volatile Channel channel;

    /**
     * Monitoing <b>Dch Server</b> whether is instantiated. because It is designed as singleton model.<br/>
     */
    private static volatile boolean instantiated;

    /**
     * Boss NioventLoopGroup is managing a group of thread, Every thread is corresponding a <b>NioEventLoop</b>
     * which contains a <b>selector and taskQueue</b> and handle the event in channel.Every channel is corresponding
     * a client thread <br/>
     * Every Boss NioEventLoop is <b>Asynchronous non blocking IO</b>. It work as follow：<br/>
     * <ol>
     *     <li>Polling the event of accept.</li>
     *     <li>ProcessSelectedKeys will establish connection with client and generate a <b>NioSocketChannel</b> and registry into a selector which the NioEventLoop of work group.</li>
     *     <li>Handle tasks in queue.</li>
     * </ol>
     */
    private volatile NioEventLoopGroup boss;

    /**
     * Work NioEventLoopGroup is managing a group of thread, Every thread is corresponding a <b>NioEventLoop</b>
     * which contains a <b>select and taskQueue</b> and handle the event in channel. Every channel is corresponding
     * a client thread.<br/>
     * Every Work thread is <b>Asynchronous non blocking IO</b>. It work as follow:<br/>
     * <ol>
     *     <li>Polling the event of read and write.</li>
     *     <li>Handle the event of read and write in corresponding <b>NioSocketChannel</b>.</li>
     *     <li>Handle tasks in queue.</li>
     * </ol>
     */
    private volatile NioEventLoopGroup work;

    /**
     * The properties will be inject into <b>Dch Server</b> when invoked the run method.<br/>
     */
    private final DchServerProperties dchServerProperties;

    /**
     * Dch Server sys log interface.
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Produce NioEventLoop prefix which name is <b>'dch_rpc_'</b> and suffix which name is <b>'_boss'</b>.
     */
    private ThreadFactory bossTf = new DefaultThreadFactory("dch_rpc_" + this.getClass().getSimpleName().toLowerCase() + "_boss");

    /**
     * Produce NioEventLoop prefix which name is <b>'dch_rpc_'</b> and suffix which name is <b>'_work'</b>.
     */
    private ThreadFactory workTf = new DefaultThreadFactory("dch_rpc_" + this.getClass().getSimpleName().toLowerCase() + "_work");

    /**
     * Constructor method insure singleton is a process and it will inject properties into <b>Dch Server</b>.
     * @param dchServerProperties properties will be injected into <b>Dch Server</b>.
     */
    protected AbstractDchServer(DchServerProperties dchServerProperties) {
        if (instantiated) {
            throw new RuntimeException("the Dch Server is instantiated.");
        }
        this.dchServerProperties = dchServerProperties;
        this.instantiated = true;
    }

    @Override
    public void start() throws Exception {
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
            if (future.isCancelled()) {
                throw new RuntimeException("bind event is cancelled.");
            }
            if (!future.isSuccess()) {
                throw new RuntimeException("bind event is unsuccess.");
            }
            channel = future.channel();
            logger.info("{} start success, port: {}", getClass().getSimpleName().toLowerCase(), dchServerProperties.getPort());
        } catch (Exception e) {
            logger.error("{} start failed.", getClass().getSimpleName().toLowerCase());
            stop0();
            throw e;
        }
    }

    private void stop0() {
        if (channel != null) {
            try {
                channel.close();
            } catch (Exception e) {
                logger.error("channel close exception", e);
            }
        }
        if (work != null) {
            try {
                work.shutdownGracefully(0, 30, TimeUnit.SECONDS);
            } catch (Exception e) {
                logger.error("work group close exception", e);
            }
        }
        if (boss != null) {
            try {
                boss.shutdownGracefully(0, 30, TimeUnit.SECONDS);
            } catch (Exception e) {
                logger.error("boss group close exception", e);
            }
        }
        channel = null;
        work = null;
        boss = null;
    }

    @Override
    public boolean running() {
        if (channel == null) {
            return false;
        }
        return channel.isOpen();
    }

    protected abstract void initChannel(SocketChannel socketChannel);

    protected abstract void close() throws Exception;

}
