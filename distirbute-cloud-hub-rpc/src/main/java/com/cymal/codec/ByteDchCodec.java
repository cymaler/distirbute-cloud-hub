package com.cymal.codec;

public class ByteDchCodec implements DchCodec<byte []>{


    @Override
    public byte[] encode(byte[] obj) {
        return obj;
    }

    @Override
    public byte[] decode(byte[] bytes) {
        return bytes;
    }
}
