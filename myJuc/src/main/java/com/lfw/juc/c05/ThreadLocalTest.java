package com.lfw.juc.c05;

import java.util.concurrent.TimeUnit;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/13 下午7:59
 * @description:
 */
public class ThreadLocalTest {

    static ThreadLocal<Person> personThreadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        new Thread(() -> {

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(personThreadLocal.get());
            personThreadLocal.remove();
        }).start();


        new Thread(() -> {
            personThreadLocal.set(new Person("lisi"));

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(personThreadLocal.get());
            personThreadLocal.remove();
        }).start();
    }
}
