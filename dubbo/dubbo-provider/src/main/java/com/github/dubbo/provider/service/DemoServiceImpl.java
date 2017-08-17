package com.github.dubbo.provider.service;

import com.github.api.DemoService;
import com.github.api.model.User;

/**
 * User: 吴海旭
 * Date: 2017-08-17
 * Time: 上午10:50
 */
public class DemoServiceImpl implements DemoService {

	public String sayHello(String name) {
		return "Hello " + name;
	}

	public User findUserById(long id) {
		User user = new User();
		user.setId(id);
		user.setName("Benjamin");
		user.setAge(25);
		return user;
	}
}
