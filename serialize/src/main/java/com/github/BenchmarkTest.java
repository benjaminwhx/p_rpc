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

import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * User: benjamin.wuhaixu
 * Date: 2018-06-11
 * Time: 5:42 pm
 */
public class BenchmarkTest {

	private static void testTemplate(ISerializer serializer, Object obj, int count, String name) {
		// 先进行预热，加载一些类，避免影响测试
		for (int i = 0; i < 10; i++) {
			byte[] bytes = serializer.serialize(obj);
			serializer.deserialize(bytes, obj.getClass());
		}
		restoreJvm(); // 进行GC回收
		// 进行测试
		long start = System.nanoTime();
		long size = 0l;
		for (int i = 0; i < count; i++) {
			byte[] bytes = serializer.serialize(obj);
			size = size + bytes.length;
			serializer.deserialize(bytes, obj.getClass());
			bytes = null;
		}
		long nsCost = (System.nanoTime() - start);
		System.out.println(name + " total cost=" + nsCost + "ns(" + nsCost/1000000 + "ms)" + " , each cost="
				+ nsCost / count + "ns , and byte sizes = " + size / count);
		restoreJvm();// 每次测试模板调用完成后system.gc保证下一轮的功能测试
	}

	private static void restoreJvm() {
		int maxRestoreJvmLoops = 10;
		long memUsedPrev = memoryUsed();
		for (int i = 0; i < maxRestoreJvmLoops; i++) {
			System.runFinalization();
			System.gc();

			long memUsedNow = memoryUsed();
			// break early if have no more finalization and get constant mem used
			if ((ManagementFactory.getMemoryMXBean().getObjectPendingFinalizationCount() == 0)
					&& (memUsedNow >= memUsedPrev)) {
				break;
			} else {
				memUsedPrev = memUsedNow;
			}
		}
	}

	private static long memoryUsed() {
		Runtime rt = Runtime.getRuntime();
		return rt.totalMemory() - rt.freeMemory();
	}

	private static MessageProtos.Message getProtobufBean() {
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

	private static MessageAvro getAvroBean() {
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

	private static MessageObj getPojoBean() {
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

	public static void main(String[] args) {
		MessageProtos.Message protobufBean = getProtobufBean();
		MessageAvro avroBean = getAvroBean();
		MessageObj pojoBean = getPojoBean();
		final int testCount = 1000 * 500;
		ISerializer defaultJavaSerializer = new DefaultJavaSerializer();
		ISerializer xmlSerializer = new XmlSerializer();
		ISerializer jsonSerializer = new FastJsonSerializer();
		ISerializer hessianSerializer = new HessianSerializer();
		ISerializer protobufSerializer = new ProtobufSerializer();
		ISerializer protostuffSerializer = new ProtostuffSerializer();
		ISerializer avroSerializer = new AvroSerializer();
		ISerializer marshallingSerializer = new MarshallingSerializer();

		testTemplate(defaultJavaSerializer, pojoBean, testCount, "JDK-Serializer");
		testTemplate(xmlSerializer, pojoBean, testCount, "XStream");
		testTemplate(jsonSerializer, pojoBean, testCount, "FastJson");
		testTemplate(hessianSerializer, pojoBean, testCount, "Hessian");
		testTemplate(protobufSerializer, protobufBean, testCount, "Protobuf");
		testTemplate(protostuffSerializer, pojoBean, testCount, "Protostuff");
		testTemplate(avroSerializer, avroBean, testCount, "Avro");
		testTemplate(marshallingSerializer, pojoBean, testCount, "JBoss-Marshalling");
	}
}
