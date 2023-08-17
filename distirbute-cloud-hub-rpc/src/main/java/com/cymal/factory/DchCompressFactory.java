package com.cymal.factory;

import com.cymal.compress.ByteDchCompress;
import com.cymal.compress.DchCompress;
import com.cymal.compress.GzipDchCompress;

import java.util.HashMap;
import java.util.Map;

public class DchCompressFactory {

    private static DchCompressFactory factory;

    private static final Map<Byte, DchCompress> COMPRESS_MAP = new HashMap<>();

    static {
        COMPRESS_MAP.put((byte)0, new ByteDchCompress());
        COMPRESS_MAP.put((byte)1, new GzipDchCompress());
    }

    private DchCompressFactory() {
    }

    public static DchCompressFactory getInstance() {
        if (factory == null) {
            synchronized (DchCompressFactory.class) {
                if (factory == null) {
                    factory = new DchCompressFactory();
                }
            }
        }
        return factory;
    }

    public DchCompress getCompress(byte ctype) {
        return COMPRESS_MAP.getOrDefault(ctype, COMPRESS_MAP.get((byte) 0));
    }

}
