package com.cymal.codec;

public class ProtobufDchCodec implements DchCodec{
    @Override
    public byte[] encode(Object obj) {
        return new byte[0];
    }

    @Override
    public Object decode(byte[] bytes) {
        return null;
    }
}
