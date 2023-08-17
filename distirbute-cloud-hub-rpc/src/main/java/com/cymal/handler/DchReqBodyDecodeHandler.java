package com.cymal.handler;

import com.cymal.codec.DchCodec;
import com.cymal.factory.DchCodecFactory;
import com.cymal.model.DchBody;
import com.cymal.model.DchContext;
import com.cymal.model.DchReqProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DchReqBodyDecodeHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DchContext context = (DchContext) msg;
        if (context.getReqProtocol() != null) {
            DchReqProtocol reqProtocol = context.getReqProtocol();
            byte stype = reqProtocol.getStype();
            DchCodec codec = DchCodecFactory.getInstance().getCodec(stype);
            DchBody body = (DchBody) codec.decode(reqProtocol.getBody());
            context.setDchBody(body);
            ctx.channel().writeAndFlush(context);
        }
    }


}
