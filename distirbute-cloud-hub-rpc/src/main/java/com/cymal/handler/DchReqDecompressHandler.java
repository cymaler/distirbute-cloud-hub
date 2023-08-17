package com.cymal.handler;

import com.cymal.compress.DchCompress;
import com.cymal.factory.DchCompressFactory;
import com.cymal.model.DchContext;
import com.cymal.model.DchReqProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DchReqDecompressHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DchContext context = (DchContext) msg;
        if (context.getReqProtocol() != null) {
            DchReqProtocol reqProtocol = context.getReqProtocol();
            DchCompress compress = DchCompressFactory.getInstance().getCompress(reqProtocol.getCtype());
            byte[] decompressBody = compress.decompress(reqProtocol.getBody());
            reqProtocol.setBody(decompressBody);
            ctx.channel().writeAndFlush(context);
        }
    }

}
