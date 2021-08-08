package com.lfw.juc.c04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/9 下午9:35
 * @description: 实现
 * 实现一个容器，提供两个方法，add和size。
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5时，线程2给出提示
 * 实际考察线程间的通信
 */
public class TbInterviewTest {

    List<Integer> list = new ArrayList<>();

    // 同步容器
//    List list = Collections.synchronizedList(new ArrayList<>());

    /**
     * 添加元素
     *
     * @param value
     * @return
     */
    public synchronized boolean add(int value) {
        return list.add(value);
    }

    public synchronized int size() {
        return list.size();
    }

    public static void main(String[] args) {
        TbInterviewTest tb = new TbInterviewTest();

        // 添加线程
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                if (i == 5) {
                    LockSupport.park();
                }
                tb.add(i);
                System.out.println("添加元素信息..." + tb.list.get(i));
            }
        });

        // 监控线程
        Thread t2 = new Thread(() -> {
            while (true) {
                int size = tb.size();
                if (size == 5) {
                    System.out.println("元素中的个数到5个啦...");
                    LockSupport.unpark(t1);
                    break;
                }
            }
        });

        t2.start();
        t1.start();
    }
}
