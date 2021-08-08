package com.lfw.juc.important;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/11 上午9:43
 * @description: 实现一个容器，提供两个方法，add和size。
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5时，线程2给出提示
 */
public class ThreadTest_WaitAndNotify<T> {

    List<T> list = Collections.synchronizedList(new ArrayList<>());
    final static Object lock = new Object();

    public void add(T t) {
        list.add(t);
    }

    public Integer size() {
        return list.size();
    }

    public static void main(String[] args) {

        ThreadTest_WaitAndNotify test = new ThreadTest_WaitAndNotify();


        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t1 start...");
                for (int i = 0; i < 10; i++) {
                    if (i == 5) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(i);
                    test.add(i);
                }
            }
        }).start();


        // 监控线程
        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t2 start...");
                if (test.size() == 5) {
                    System.out.println("元素到5个了");
                    lock.notify();
                }
            }
        }).start();
    }
}
