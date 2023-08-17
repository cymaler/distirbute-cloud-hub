package com.cymal.codec;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonDchCodec<T> implements DchCodec<T>{

    private final ObjectMapper om;
    private final Class<T> objClass;

    public JacksonDchCodec(Class<T> objClass) {
       this(objClass, new ObjectMapper());
    }

    public JacksonDchCodec(Class<T> objClass, ObjectMapper om) {
        this.om = om;
        this.objClass = objClass;
    }

    @Override
    public byte[] encode(T obj) {
        try {
            return om.writeValueAsBytes(obj);
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public T decode(byte[] bytes) {
        try {
            return om.readValue(bytes, objClass);
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }
    }

}
