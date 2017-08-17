package com.github.dubbo.consumer;

import com.github.api.DemoService;
import com.github.api.model.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        DemoService demoService = (DemoService) context.getBean("demoService");
        String hello = demoService.sayHello("benjamin");
        System.out.println(hello);

        User user = demoService.findUserById(110);
        System.out.println(user);
    }
}
