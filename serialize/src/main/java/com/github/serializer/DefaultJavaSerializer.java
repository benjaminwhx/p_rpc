package com.github.serializer;

import com.github.exception.SerializationException;
import com.github.util.IOUtils;

import java.io.*;

/**
 * User: benjamin.wuhaixu
 * Date: 2018-06-08
 * Time: 3:57 pm
 */
public class DefaultJavaSerializer implements ISerializer {

    @Override
    public <T> byte[] serialize(T obj) {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new SerializationException("Non-serializable object.", e);
        } finally {
            IOUtils.closeIO(oos, bos);
        }
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        if (data == null) {
            return null;
        }
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(data);
            ois = new ObjectInputStream(bis);
            return (T) ois.readObject();
        } catch (Exception e) {
            throw new IllegalArgumentException("Can't-deserialize object", e);
        } finally {
            IOUtils.closeIO(bis, ois);
        }
    }
}
