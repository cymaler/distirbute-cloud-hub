package com.cymal.codec;

/**
 * provider two methods to serialize and deserialize.
 * @param <T> any object.
 */
public interface DchCodec<T> {

    /**
     * serialize obj into byte array.
     * @param obj object.
     * @return byte array.
     */
    byte[] encode(T obj);

    /**
     * deserialize byte array into obj.
     * @param bytes byte array.
     * @return object.
     */
    T decode(byte[] bytes);

}
