package com.cymal.factory;

import com.cymal.codec.ByteDchCodec;
import com.cymal.codec.DchCodec;
import com.cymal.codec.JacksonDchCodec;
import com.cymal.codec.ProtobufDchCodec;
import com.cymal.model.DchBody;

import java.util.HashMap;
import java.util.Map;

public class DchCodecFactory {

    private static DchCodecFactory factory;

    private static final Map<Byte, DchCodec> CODEC_MAP = new HashMap<>();
    private DchCodecFactory() {
    }

    static {
        CODEC_MAP.put((byte) 0, new ByteDchCodec());
        CODEC_MAP.put((byte) 1, new JacksonDchCodec(DchBody.class));
        CODEC_MAP.put((byte) 2, new ProtobufDchCodec());
    }

    public static DchCodecFactory getInstance() {
        if (factory == null) {
            synchronized (DchCodecFactory.class) {
                if (factory == null) {
                    factory = new DchCodecFactory();
                }
            }
        }
        return factory;
    }

    public DchCodec getCodec(byte stype) {
        return CODEC_MAP.getOrDefault(stype, CODEC_MAP.get((byte)0));
    }

}
