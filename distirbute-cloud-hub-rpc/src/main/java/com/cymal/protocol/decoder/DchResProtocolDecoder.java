package com.cymal.protocol.decoder;

import com.cymal.model.DchResProtocol;
import com.cymal.constant.DchResponseProtocolConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class DchResProtocolDecoder extends LengthFieldBasedFrameDecoder {

    public DchResProtocolDecoder() {
        super(
                DchResponseProtocolConstant.PROTOCOL_MAX_LEN,
                DchResponseProtocolConstant.PROTOCOL_MAGIC_LEN +
                        DchResponseProtocolConstant.PROTOCOL_VERSION_LEN +
                        DchResponseProtocolConstant.PROTOCOL_CODE_LEN +
                        DchResponseProtocolConstant.PROTOCOL_SERIAL_TYPE_LEN +
                        DchResponseProtocolConstant.PROTOCOL_COMPRESS_TYPE_LEN +
                        DchResponseProtocolConstant.PROTOCOL_HEAD_LEN,
                DchResponseProtocolConstant.PROTOCOL_LEN,
                -(DchResponseProtocolConstant.PROTOCOL_MAGIC_LEN +
                        DchResponseProtocolConstant.PROTOCOL_VERSION_LEN +
                        DchResponseProtocolConstant.PROTOCOL_CODE_LEN +
                        DchResponseProtocolConstant.PROTOCOL_SERIAL_TYPE_LEN +
                        DchResponseProtocolConstant.PROTOCOL_COMPRESS_TYPE_LEN +
                        DchResponseProtocolConstant.PROTOCOL_HEAD_LEN +
                        DchResponseProtocolConstant.PROTOCOL_LEN),
                0
        );
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        Object decode = super.decode(ctx, in);
        if (decode instanceof ByteBuf byteBuf) {
            return decode(byteBuf);
        }
        return null;
    }

    private Object decode(ByteBuf byteBuf) {
        DchResProtocol protocol = new DchResProtocol();
        byte[] magicBytes = new byte[DchResponseProtocolConstant.PROTOCOL_MAGIC_LEN];
        byteBuf.readBytes(protocol.getMagic());
        protocol.setMagic(magicBytes);
        protocol.setVersion(byteBuf.readByte());
        protocol.setCode(byteBuf.readByte());
        protocol.setStype(byteBuf.readByte());
        protocol.setCtype(byteBuf.readByte());
        protocol.setHeadLen(byteBuf.readInt());
        protocol.setLen(byteBuf.readInt());
        protocol.setResId(byteBuf.readLong());
        protocol.setTimestamp(byteBuf.readLong());
        byte[] bodyBytes = new byte[protocol.getLen() - protocol.getHeadLen()];
        byteBuf.readBytes(bodyBytes);
        protocol.setMagic(bodyBytes);
        return protocol;
    }

}
