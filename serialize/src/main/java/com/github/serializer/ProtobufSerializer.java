package com.github.serializer;

import com.google.protobuf.GeneratedMessageV3;
import org.apache.commons.lang3.reflect.MethodUtils;

/**
 * User: benjamin.wuhaixu
 * Date: 2018-06-08
 * Time: 6:08 pm
 */
public class ProtobufSerializer implements ISerializer {

    @Override
    public <T> byte[] serialize(T obj) {
        try {
            if (!(obj instanceof GeneratedMessageV3)) {
                throw new UnsupportedOperationException("not supported obj type");
            }
            return (byte[]) MethodUtils.invokeMethod(obj, "toByteArray");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        try {
            if (!GeneratedMessageV3.class.isAssignableFrom(clazz)) {
                throw new UnsupportedOperationException("not supported obj type");
            }
            Object o = MethodUtils.invokeStaticMethod(clazz, "getDefaultInstance");
            return (T) MethodUtils.invokeMethod(o, "parseFrom", new Object[]{data});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
