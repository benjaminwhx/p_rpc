package com.github.serializer;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: benjamin.wuhaixu
 * Date: 2018-06-08
 * Time: 6:15 pm
 */
public class ProtostuffSerializer implements ISerializer {

    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();
    private static Objenesis objenesis = new ObjenesisStd();

    private <T> Schema<T> getSchema(Class<T> clz) {
        Schema<T> schema = (Schema<T>) cachedSchema.get(clz);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(clz);
            cachedSchema.put(clz, schema);
        }
        return schema;
    }

    @Override
    public <T> byte[] serialize(T obj) {
        Class<T> clz = (Class<T>) obj.getClass();
        Schema<T> schema = getSchema(clz);
        return ProtostuffIOUtil.toByteArray(obj, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        T obj = objenesis.newInstance(clazz);
        ProtostuffIOUtil.mergeFrom(data, obj, getSchema(clazz));
        return obj;
    }
}
