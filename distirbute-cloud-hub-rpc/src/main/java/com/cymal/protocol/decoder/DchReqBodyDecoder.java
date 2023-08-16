package com.cymal.protocol.decoder;

import com.cymal.protocol.DchReqProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class DchReqBodyDecoder extends MessageToMessageDecoder<DchReqProtocol> {

    @Override
    protected void decode(ChannelHandlerContext ctx, DchReqProtocol msg, List<Object> out) throws Exception {

    }
}
