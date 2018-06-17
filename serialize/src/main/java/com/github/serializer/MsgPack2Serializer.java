package com.github.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.IOException;

/**
 * User: benjamin.wuhaixu
 * Date: 2018-06-17
 * Time: 6:19 pm
 */
public class MsgPack2Serializer implements ISerializer {

	private ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());

	@Override
	public <T> byte[] serialize(T obj) {
		try {
			return objectMapper.writeValueAsBytes(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> T deserialize(byte[] data, Class<T> clazz) {
		try {
			return objectMapper.readValue(data, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
