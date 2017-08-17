package com.github.dubbo.provider.service;

import com.github.api.HelloService;

/**
 * User: 吴海旭
 * Date: 2017-08-17
 * Time: 上午10:53
 */
public class HelloServiceImpl implements HelloService {

	public String sayHi(String msg) {
		return "Hi " + msg;
	}
}
