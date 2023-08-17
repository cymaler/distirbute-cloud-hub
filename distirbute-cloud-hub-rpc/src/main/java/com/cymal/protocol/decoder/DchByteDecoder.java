package com.cymal.protocol.decoder;

import com.cymal.compress.DchCompress;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DchByteDecoder extends ChannelInboundHandlerAdapter {

    private final DchCompress dchCompress;

    public DchByteDecoder(DchCompress dchCompress) {
        this.dchCompress = dchCompress;
    }

}
