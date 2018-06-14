package com.github;

import com.github.bean.Person;
import com.github.bean.avro.User;
import com.github.bean.protobuf.AddressBookProtos;
import com.github.serializer.*;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * User: benjamin.wuhaixu
 * Date: 2018-06-06
 * Time: 0:34 pm
 */
public class SerializerTest {

    private void testJavaSerializer() {
        // 需要实现Serializable接口
        Person person = new Person(21, "ben");
        ISerializer serializer = new DefaultJavaSerializer();
        byte[] bytes = serializer.serialize(person);
        Person dePerson = serializer.deserialize(bytes, Person.class);
        System.out.println(dePerson);
    }

    private void testXmlSerializer() {
        Person person = new Person(21, "ben");
        ISerializer serializer = new XmlSerializer();
        byte[] bytes = serializer.serialize(person);
        System.out.println(new String(bytes));
    }

    private void testXml2Serializer() {
        // 需要有无参构造函数
        Person person = new Person(21, "ben");
        ISerializer serializer = new Xml2Serializer();
        byte[] bytes = serializer.serialize(person);
        System.out.println(new String(bytes));
    }

    private void testFastJsonSerializer() {
        Person person = new Person(21, "ben");
        ISerializer serializer = new FastJsonSerializer();
        byte[] bytes = serializer.serialize(person);
        System.out.println(new String(bytes));
    }

    private void testHessianSerializer() {
        // 需要实现Serializable接口
        Person person = new Person(21, "ben");
        ISerializer serializer = new HessianSerializer();
        byte[] bytes = serializer.serialize(person);
        Person dePerson = serializer.deserialize(bytes, Person.class);
        System.out.println(dePerson);
    }

    private void testProtobufSerializer() throws InvalidProtocolBufferException {
        AddressBookProtos.Person person = AddressBookProtos.Person.newBuilder()
                .setEmail("benjaminwhx@sina.com")
                .setId(10000)
                .setName("benjamin")
                .addPhones(AddressBookProtos.Person.PhoneNumber.newBuilder()
                        .setNumber("18888888888")
                        .setType(AddressBookProtos.Person.PhoneType.HOME)
                        .build()).build();

        // 序列化方式1
        System.out.println(person.toByteString());
        // 序列化方式2
        System.out.println(Arrays.toString(person.toByteArray()));

        // 反序列化方式1
        AddressBookProtos.Person newPerson = AddressBookProtos.Person.parseFrom(person.toByteString());
        System.out.println("反序列化1：" + newPerson);
        // 反序列化方式2
        newPerson = AddressBookProtos.Person.parseFrom(person.toByteArray());
        System.out.println("反序列化2：" + newPerson);

        // 使用protobuf序列化工具
        ISerializer serializer = new ProtobufSerializer();
        byte[] data = serializer.serialize(person);
        AddressBookProtos.Person dePerson = serializer.deserialize(data, AddressBookProtos.Person.class);
        System.out.println("反序列化3：" + dePerson);
    }

	private void testProtostuffSerializer() {
		Person person = new Person(21, "ben");
		ISerializer serializer = new ProtostuffSerializer();
		byte[] bytes = serializer.serialize(person);
		Person dePerson = serializer.deserialize(bytes, Person.class);
		System.out.println(dePerson);
	}

	private void testAvroSerializer() throws IOException {
		User userAvro = new User();
		userAvro.setAge(26);
		userAvro.setEmail("benjaminwhx@sina.com");
		userAvro.setName("benjamin");
		// 1、序列化
		DatumWriter<User> writer = new SpecificDatumWriter<>(User.class);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		BinaryEncoder binaryEncoder = EncoderFactory.get().directBinaryEncoder(bos, null);
		writer.write(userAvro, binaryEncoder);
		byte[] data = bos.toByteArray();

		// 2、反序列化
		DatumReader<User> reader = new SpecificDatumReader<>(User.class);
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		BinaryDecoder binaryDecoder = DecoderFactory.get().directBinaryDecoder(bis, null);
		User deUser = reader.read(userAvro, binaryDecoder);
		System.out.println(deUser);
	}

    private void testAvroSerializer2() throws IOException {
        Schema schema = new Schema.Parser().parse(new File("/Users/Benjamin/idea_project/local/src/main/avro/user.avsc"));
        GenericRecord user1 = new GenericData.Record(schema);
		user1.put("age", 18);
		user1.put("name", "benjamin");
		user1.put("email", "benjaminwhx@sina.com");
		// 1、序列化
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BinaryEncoder binaryEncoder = EncoderFactory.get().directBinaryEncoder(bos, null);
        datumWriter.write(user1, binaryEncoder);
        byte[] bytes = bos.toByteArray();

        // 2、反序列化
        DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(schema);
		BinaryDecoder binaryDecoder = DecoderFactory.get().directBinaryDecoder(new ByteArrayInputStream(bytes), null);
		GenericRecord read = datumReader.read(new GenericData.Record(schema), binaryDecoder);
		System.out.println(read);
	}

	private void testAvroSerializer3() {
		User userAvro = new User();
		userAvro.setAge(26);
		userAvro.setEmail("benjaminwhx@sina.com");
		userAvro.setName("benjamin");

    	ISerializer serializer = new AvroSerializer();
		byte[] bytes = serializer.serialize(userAvro);

		User user = serializer.deserialize(bytes, User.class);
		System.out.println(user);
	}

	private void testMarshallingSerializer() {
		// 需要实现Serializable接口
		Person person = new Person(21, "ben");
		ISerializer serializer = new MarshallingSerializer();
		byte[] bytes = serializer.serialize(person);
		Person dePerson = serializer.deserialize(bytes, Person.class);
		System.out.println(dePerson);
	}

    public static void main(String[] args) throws Exception {
        SerializerTest serializerTest = new SerializerTest();
//        personTest.testJavaSerializer();
//        personTest.testXmlSerializer();
//        personTest.testXml2Serializer();
//        personTest.testFastJsonSerializer();
//        personTest.testHessianSerializer();
//        personTest.testProtobufSerializer();
//		  personTest.testProtostuffSerializer();
//		  personTest.testAvroSerializer();
        serializerTest.testAvroSerializer2();
//        personTest.testAvroSerializer3();
//		personTest.testMarshallingSerializer();
	}
}
