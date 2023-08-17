package com.cymal.compress;

public class ByteDchCompress implements DchCompress{

    @Override
    public byte[] compress(byte[] bytes) {
        return bytes;
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        return bytes;
    }

}
