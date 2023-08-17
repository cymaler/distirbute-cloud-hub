package com.cymal.protocol.encoder;

import com.cymal.compress.DchCompress;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DchByteEncoder extends ChannelInboundHandlerAdapter {

    private final DchCompress dchCompress;

    public DchByteEncoder(DchCompress dchCompress) {
        this.dchCompress = dchCompress;
    }

}
