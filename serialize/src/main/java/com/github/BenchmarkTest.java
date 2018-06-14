package com.github;

import com.github.bean.avro.EnumTypeAvro;
import com.github.bean.avro.InnerMessageAvro;
import com.github.bean.avro.MessageAvro;
import com.github.bean.pojo.EnumTypeObj;
import com.github.bean.pojo.InnerMessageObj;
import com.github.bean.pojo.MessageObj;
import com.github.bean.protobuf.EnumTypeProtos;
import com.github.bean.protobuf.InnerMessageProtos;
import com.github.bean.protobuf.MessageProtos;
import com.github.serializer.*;
import com.google.protobuf.ByteString;
import org.openjdk.jmh.annotations.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * User: benjamin.wuhaixu
 * Date: 2018-06-12
 * Time: 9:19 pm
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@Fork(3)
@Threads(8)
public class BenchmarkTest {

	private ISerializer javaSerializer = new DefaultJavaSerializer();
	private ISerializer xStreamSerializer = new XmlSerializer();
	private ISerializer javaXmlSerializer = new Xml2Serializer();
	private ISerializer fastJsonSerializer = new FastJsonSerializer();
	private ISerializer hessianSerializer = new HessianSerializer();
	private ISerializer protobufSerializer = new ProtobufSerializer();
	private ISerializer protostuffSerializer = new ProtostuffSerializer();
	private ISerializer avroSerializer = new AvroSerializer();
	private ISerializer marshallingSerializer = new MarshallingSerializer();
	private MessageProtos.Message probufBean = getProtobufBean();
	private MessageAvro avroBean = getAvroBean();
	private MessageObj pojoBean = getPojoBean();

	byte[] javaSerializeBytes = javaSerializer.serialize(pojoBean);
	byte[] xStreamSerializeBytes = xStreamSerializer.serialize(pojoBean);
	byte[] javaXmlSerializeBytes = javaXmlSerializer.serialize(pojoBean);
	byte[] fastJsonSerializeBytes = fastJsonSerializer.serialize(pojoBean);
	byte[] hessianSerializeBytes = hessianSerializer.serialize(pojoBean);
	byte[] protobufSerializeBytes = protobufSerializer.serialize(probufBean);
	byte[] protostuffSerializeBytes = protostuffSerializer.serialize(pojoBean);
	byte[] avroSerializeBytes = avroSerializer.serialize(avroBean);
	byte[] marshallingSerializeBytes = marshallingSerializer.serialize(pojoBean);

	private MessageProtos.Message getProtobufBean() {
		MessageProtos.Message.Builder messageBuilder = MessageProtos.Message.newBuilder();

		messageBuilder.setStrObj("message");
		messageBuilder.setFolatObj(1f);
		messageBuilder.addDoubleObj(1d);
		messageBuilder.addDoubleObj(2d);
		messageBuilder.setBoolObj(true);

		messageBuilder.setBytesObj(ByteString.copyFrom(new byte[] { 1, 2, 3 }));
		messageBuilder.setInt32Obj(32);
		messageBuilder.setInt64Obj(64l);

		InnerMessageProtos.InnerMessage.Builder innerMessageBuilder = InnerMessageProtos.InnerMessage.newBuilder();
		innerMessageBuilder.setId(1);
		innerMessageBuilder.setName("inner");
		innerMessageBuilder.setType(EnumTypeProtos.EnumType.PRODUCTS);

		messageBuilder.setInnerMessage(innerMessageBuilder);
		return messageBuilder.build();
	}

	private MessageAvro getAvroBean() {
		MessageAvro messageAvro = new MessageAvro();
		messageAvro.setStrObj("message");
		messageAvro.setFloatObj(1f);
		List<Double> doubleList = new ArrayList<>();
		doubleList.add(1d);
		doubleList.add(2d);
		messageAvro.setDoubleObjList(doubleList);
		messageAvro.setBytesObj(ByteBuffer.wrap(new byte[] {1, 2, 3}));
		messageAvro.setInt32Obj(32);
		messageAvro.setInt64Obj(64L);

		InnerMessageAvro innerMessageAvro = new InnerMessageAvro();
		innerMessageAvro.setId(1);
		innerMessageAvro.setName("inner");
		innerMessageAvro.setEnumTypeObj(EnumTypeAvro.PRODUCTS);
		messageAvro.setInnerMessageObj(innerMessageAvro);
		return messageAvro;
	}

	private MessageObj getPojoBean() {
		InnerMessageObj innerMessageObj = new InnerMessageObj();
		innerMessageObj.setId(1);
		innerMessageObj.setName("inner");
		innerMessageObj.setEnumTypeObj(EnumTypeObj.PRODUCTS);

		MessageObj msg = new MessageObj();
		msg.setStrObj("message");
		msg.setFloatObj(1f);
		List<Double> doubleList = new ArrayList<>();
		doubleList.add(1d);
		doubleList.add(2d);
		msg.setDoubleObjList(doubleList);
		msg.setBoolObj(true);
		msg.setBytesObj(new byte[] {1, 2, 3});
		msg.setInt32Obj(32);
		msg.setInt64Obj(64L);
		msg.setInnerMessageObj(innerMessageObj);
		return msg;
	}

	@Benchmark
	public byte[] testDefaultJavaSerialize() {
		return javaSerializer.serialize(pojoBean);
	}
	@Benchmark
	public MessageObj testDefaultJavaDeSerialize() {
		return javaSerializer.deserialize(javaSerializeBytes, MessageObj.class);
	}

	@Benchmark
	public byte[] testXStreamSerialize() {
		return xStreamSerializer.serialize(pojoBean);
	}
	@Benchmark
	public MessageObj testXStreamDeSerialize() {
		return xStreamSerializer.deserialize(xStreamSerializeBytes, MessageObj.class);
	}

	@Benchmark
	public byte[] testJavaXmlSerialize() {
		return javaXmlSerializer.serialize(pojoBean);
	}
	@Benchmark
	public MessageObj testJavaXmlDeSerialize() {
		return javaXmlSerializer.deserialize(javaXmlSerializeBytes, MessageObj.class);
	}

	@Benchmark
	public byte[] testFastJsonSerialize() {
		return fastJsonSerializer.serialize(pojoBean);
	}
	@Benchmark
	public MessageObj testFastJsonDeSerialize() {
		return fastJsonSerializer.deserialize(fastJsonSerializeBytes, MessageObj.class);
	}

	@Benchmark
	public byte[] testHessianSerialize() {
		return hessianSerializer.serialize(pojoBean);
	}
	@Benchmark
	public MessageObj testHessianDeSerialize() {
		return hessianSerializer.deserialize(hessianSerializeBytes, MessageObj.class);
	}

	@Benchmark
	public byte[] testProtobufSerialize() {
		return protobufSerializer.serialize(probufBean);
	}
	@Benchmark
	public MessageProtos.Message testProtobufDeSerialize() {
		return protobufSerializer.deserialize(protobufSerializeBytes, MessageProtos.Message.class);
	}

	@Benchmark
	public byte[] testProtostuffSerialize() {
		return protostuffSerializer.serialize(pojoBean);
	}
	@Benchmark
	public MessageObj testProtostuffDeSerialize() {
		return protostuffSerializer.deserialize(protostuffSerializeBytes, MessageObj.class);
	}

	@Benchmark
	public byte[] testAvroSerialize() {
		return avroSerializer.serialize(avroBean);
	}
	@Benchmark
	public MessageAvro testAvroDeSerialize() {
		return avroSerializer.deserialize(avroSerializeBytes, MessageAvro.class);
	}

	@Benchmark
	public byte[] testMarshallingSerialize() {
		return marshallingSerializer.serialize(pojoBean);
	}
	@Benchmark
	public MessageObj testMarshallingDeSerialize() {
		return marshallingSerializer.deserialize(marshallingSerializeBytes, MessageObj.class);
	}
}
