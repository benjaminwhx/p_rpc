package com.github.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * User: benjamin.wuhaixu
 * Date: 2018-06-06
 * Time: 10:54 am
 */
@Data
@NoArgsConstructor
public class Person implements Serializable {
    private int age;
    private String name;

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }
}
