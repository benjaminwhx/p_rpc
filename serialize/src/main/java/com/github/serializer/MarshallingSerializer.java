package com.github.serializer;

import org.jboss.marshalling.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * User: benjamin.wuhaixu
 * Date: 2018-06-10
 * Time: 5:45 pm
 */
public class MarshallingSerializer implements ISerializer {
	final static MarshallingConfiguration CONFIGURATION = new MarshallingConfiguration();
	// 获得序列化工厂对象，参数serial是标识创建的是Java序列化工厂对象
	final static MarshallerFactory MARSHALLER_FACTORY = Marshalling.getProvidedMarshallerFactory("serial");

	static {
		CONFIGURATION.setVersion(5);
	}

	@Override
	public <T> byte[] serialize(T obj) {
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			Marshaller marshaller = MARSHALLER_FACTORY.createMarshaller(CONFIGURATION);
			marshaller.start(Marshalling.createByteOutput(bos));
			marshaller.writeObject(obj);
			marshaller.finish();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return bos.toByteArray();
	}

	@Override
	public <T> T deserialize(byte[] data, Class<T> clazz) {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			Unmarshaller unmarshaller = MARSHALLER_FACTORY.createUnmarshaller(CONFIGURATION);
			unmarshaller.start(Marshalling.createByteInput(bis));
			Object o = unmarshaller.readObject();
			unmarshaller.finish();
			return (T) o;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
