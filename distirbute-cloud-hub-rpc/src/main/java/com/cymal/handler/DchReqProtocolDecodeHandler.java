package com.cymal.handler;

import com.cymal.model.DchContext;
import com.cymal.model.DchReqProtocol;
import com.cymal.constant.DchRequestProtocolConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class DchReqProtocolDecodeHandler extends LengthFieldBasedFrameDecoder {

    public DchReqProtocolDecodeHandler() {
        super(
                DchRequestProtocolConstant.PROTOCOL_MAX_LEN,
                DchRequestProtocolConstant.PROTOCOL_MAGIC_LEN +
                        DchRequestProtocolConstant.PROTOCOL_VERSION_LEN +
                        DchRequestProtocolConstant.PROTOCOL_METHOD_LEN +
                        DchRequestProtocolConstant.PROTOCOL_SERIAL_TYPE_LEN +
                        DchRequestProtocolConstant.PROTOCOL_COMPRESS_TYPE_LEN +
                        DchRequestProtocolConstant.PROTOCOL_HEAD_LEN,
                DchRequestProtocolConstant.PROTOCOL_LEN,
                -(DchRequestProtocolConstant.PROTOCOL_MAGIC_LEN +
                        DchRequestProtocolConstant.PROTOCOL_VERSION_LEN +
                        DchRequestProtocolConstant.PROTOCOL_METHOD_LEN +
                        DchRequestProtocolConstant.PROTOCOL_SERIAL_TYPE_LEN +
                        DchRequestProtocolConstant.PROTOCOL_COMPRESS_TYPE_LEN +
                        DchRequestProtocolConstant.PROTOCOL_HEAD_LEN +
                        DchRequestProtocolConstant.PROTOCOL_LEN),
                0
        );
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        Object decode = super.decode(ctx, in);
        DchContext context = null;
        if (decode instanceof ByteBuf byteBuf) {
            DchReqProtocol reqProtocol = decode(byteBuf);
            context = new DchContext();
            context.setReqProtocol(reqProtocol);
        }
        return context;
    }

    private DchReqProtocol decode(ByteBuf byteBuf) {
        DchReqProtocol protocol = new DchReqProtocol();
        byte[] magicBytes = new byte[DchRequestProtocolConstant.PROTOCOL_MAGIC_LEN];
        byteBuf.readBytes(protocol.getMagic());
        protocol.setMagic(magicBytes);
        protocol.setVersion(byteBuf.readByte());
        protocol.setMethod(byteBuf.readByte());
        protocol.setStype(byteBuf.readByte());
        protocol.setCtype(byteBuf.readByte());
        protocol.setHeadLen(byteBuf.readInt());
        protocol.setLen(byteBuf.readInt());
        protocol.setReqId(byteBuf.readLong());
        protocol.setTimestamp(byteBuf.readLong());
        byte[] bodyBytes = new byte[protocol.getLen() - protocol.getHeadLen()];
        byteBuf.readBytes(bodyBytes);
        protocol.setMagic(bodyBytes);
        return protocol;
    }
}
