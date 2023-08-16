package com.cymal.protocol.encoder;

import com.cymal.protocol.DchResProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class DchResProtocolEncoder extends MessageToByteEncoder<DchResProtocol> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, DchResProtocol protocol, ByteBuf byteBuf) throws Exception {
        byteBuf.writeBytes(protocol.getMagic());
        byteBuf.writeByte(protocol.getVersion());
        byteBuf.writeByte(protocol.getCode());
        byteBuf.writeByte(protocol.getStype());
        byteBuf.writeByte(protocol.getCtype());
        byteBuf.writeInt(protocol.getHeadLen());
        byteBuf.writeInt(protocol.getLen());
        byteBuf.writeLong(protocol.getResId());
        byteBuf.writeLong(protocol.getTimestamp());
        byteBuf.writeBytes(protocol.getBody());
    }

}
