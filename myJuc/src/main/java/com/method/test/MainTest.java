package com.method.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/1/20 上午9:46
 * @description:
 */
public class MainTest {

    static class User {
        int age;
        String name;

        public User() {

        }
    }

    public static void main(String[] args) {

        User userOne = new User();
        userOne.age = 10;
        userOne.name = "test";

        User userTwo = new User();
        userTwo.age = 10;
        userTwo.name = "test";

        HashMap hashOne = new HashMap();
        hashOne.put("123", "456");

        HashMap hashTwo = new HashMap();
        hashTwo.put("123", "456");


        hashOne.keySet().forEach(item -> {
            System.out.println(item);
            System.out.println(item.hashCode());
        });

        hashTwo.keySet().forEach(item -> {
            System.out.println(item);
            System.out.println(item.hashCode());
            System.out.println(item.equals("123"));
        });


//        System.out.println(userOne == userTwo);
//        System.out.println(userOne.equals(userTwo));
//        System.out.println(userOne.hashCode());
//        System.out.println(userTwo.hashCode());
    }
}
