package com.cymal.protocol.decoder;

import com.cymal.codec.DchCodec;
import com.cymal.model.DchReqProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class DchReqBodyDecoder<T> extends ChannelInboundHandlerAdapter {

    private final DchCodec<T> dchCodec;

    public DchReqBodyDecoder(DchCodec dchCodec) {
        this.dchCodec = dchCodec;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DchReqProtocol protocol = (DchReqProtocol) msg;
        T body = dchCodec.decode(protocol.getBody());
        ctx.channel().writeAndFlush(body);
    }

}
