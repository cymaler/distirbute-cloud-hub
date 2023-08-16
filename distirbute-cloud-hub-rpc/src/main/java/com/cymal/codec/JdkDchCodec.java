package com.cymal.codec;

public class JdkDchCodec<T> implements DchCodec<T> {

    @Override
    public byte[] encode(T obj) {
        return new byte[0];
    }

    @Override
    public T decode(byte[] bytes) {
        return null;
    }

}
