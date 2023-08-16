package com.cymal.compress;

public interface DchCompress {

    byte[] compress(byte[] bytes);

    byte[] decompress(byte[] bytes);

}
