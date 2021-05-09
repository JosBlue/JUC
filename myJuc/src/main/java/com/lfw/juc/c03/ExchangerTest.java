package com.lfw.juc.c03;

import java.util.concurrent.Exchanger;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/9 下午5:33
 * @description:
 */
public class ExchangerTest {

    static Exchanger exchanger = new Exchanger();

    static class User {
        private String name;

        private Integer age;

        public User(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }


    public static void main(String[] args) {
        new Thread(() -> {
            User user = new User("t1", 20);
//            String T = "t1";
            try {
                user = (User) exchanger.exchange(user);
                System.out.println(Thread.currentThread().getName() + "交换信息..." + user);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();


        new Thread(() -> {
            User user = new User("t2", 40);
            try {
                user = (User) exchanger.exchange(user);
                System.out.println(Thread.currentThread().getName() + "交换信息..." + user);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}
