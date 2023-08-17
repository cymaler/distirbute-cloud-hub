package com.cymal.protocol.encoder;

import com.cymal.codec.DchCodec;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DchReqBodyEncoder<T> extends ChannelInboundHandlerAdapter {

    private final DchCodec<T> dchCodec;

    public DchReqBodyEncoder(DchCodec<T> dchCodec) {
        this.dchCodec = dchCodec;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

}
