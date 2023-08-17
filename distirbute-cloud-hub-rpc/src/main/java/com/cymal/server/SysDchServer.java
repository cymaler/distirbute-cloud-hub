package com.cymal.server;

import com.cymal.codec.DchCodec;
import com.cymal.compress.DchCompress;
import com.cymal.handler.InvokerMethodHandler;
import com.cymal.properties.DchServerProperties;
import com.cymal.protocol.decoder.DchByteDecoder;
import com.cymal.protocol.decoder.DchReqBodyDecoder;
import com.cymal.protocol.decoder.DchReqProtocolDecoder;
import com.cymal.protocol.encoder.DchByteEncoder;
import com.cymal.protocol.encoder.DchReqBodyEncoder;
import com.cymal.protocol.encoder.DchResProtocolEncoder;
import io.netty.channel.socket.SocketChannel;

public class SysDchServer extends AbstractDchServer{

    private final DchCodec dchCodec;

    private final DchCompress dchCompress;

    private final DchServerProperties dchServerProperties;

    public SysDchServer(DchServerProperties dchServerProperties, DchCodec dchCodec) {
        this(dchServerProperties, dchCodec, null);
    }

    public SysDchServer(DchServerProperties dchServerProperties, DchCodec dchCodec, DchCompress dchCompress) {
        super(dchServerProperties);
        this.dchServerProperties = dchServerProperties;
        this.dchCodec = dchCodec;
        this.dchCompress = dchCompress;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline()                            // ---> Pipeline.
                .addLast(new DchByteDecoder(dchCompress))   // ---> Decompress Byte Array.=
                .addLast(new DchReqProtocolDecoder())       // ---> DchReqProtocol.
                .addLast(new DchReqBodyDecoder(dchCodec))   // ---> DchBody.
                .addLast(new InvokerMethodHandler())        // ---> Result Object.
                .addLast(new DchReqBodyEncoder(dchCodec))   // ---> DchResProtocol.
                .addLast(new DchResProtocolEncoder())       // ---> ByteBuf.
                .addLast(new DchByteEncoder(dchCompress));  // ---> Compress Byte Array.
    }

    @Override
    protected void close() throws Exception {
        // do nothing...
    }

}
