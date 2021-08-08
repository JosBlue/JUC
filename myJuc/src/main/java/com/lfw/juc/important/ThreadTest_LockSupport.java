package com.lfw.juc.important;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/11 上午10:21
 * @description:
 */
public class ThreadTest_LockSupport<T> {

    List<T> list = Collections.synchronizedList(new ArrayList<>());

    public synchronized void add(T t) {
        list.add(t);
    }

    public synchronized Integer size() {
        return list.size();
    }

    public static void main(String[] args) {

        ThreadTest_LockSupport test = new ThreadTest_LockSupport();

        //添加线程
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                if (i == 5) {
                    LockSupport.park();
                }
                System.out.println(i);
                test.add(i);
            }
        });
        t1.start();

        // 监控线程
        new Thread(() -> {
            System.out.println("t2 start...");
            if (test.size() == 5) {
                System.out.println("到5个了...");
                LockSupport.unpark(t1);
            }
        }).start();
    }
}
