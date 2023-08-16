package com.cymal.protocol.encoder;

import com.cymal.protocol.DchReqProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class DchReqProtocolEncoder extends MessageToByteEncoder<DchReqProtocol> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, DchReqProtocol protocol, ByteBuf byteBuf) throws Exception {
        byteBuf.writeBytes(protocol.getMagic());
        byteBuf.writeByte(protocol.getVersion());
        byteBuf.writeByte(protocol.getMethod());
        byteBuf.writeByte(protocol.getStype());
        byteBuf.writeByte(protocol.getCtype());
        byteBuf.writeInt(protocol.getHeadLen());
        byteBuf.writeInt(protocol.getLen());
        byteBuf.writeLong(protocol.getReqId());
        byteBuf.writeLong(protocol.getTimestamp());
        byteBuf.writeBytes(protocol.getBody());
    }

}
