package com.github.api;

import com.github.api.model.User;

/**
 * User: 吴海旭
 * Date: 2017-08-16
 * Time: 下午9:21
 */
public interface DemoService {

    public String sayHello(String name);

    public User findUserById(long id);
}
