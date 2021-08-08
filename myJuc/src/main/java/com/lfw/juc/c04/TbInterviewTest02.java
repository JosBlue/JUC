package com.lfw.juc.c04;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/9 下午9:38
 * @description: 实现
 * 写一个固定容量同步容器，拥有put和get方法，以及getCount方法，能够支持2个生产者线程以及10个消费者线程阻塞调用
 */
public class TbInterviewTest02<T> {

    final private LinkedList<T> linkList = new LinkedList<>();

    final private int MAX_COUNT = 10;

    volatile int count = 0;


    synchronized boolean put(T t) {
        try {
            // 等于最大容量，等待
            while (linkList.size() == MAX_COUNT) {
                this.wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        linkList.add(t);
        System.out.println(Thread.currentThread().getName() + "生产信息..." + getCount());
        // 唤醒消费线程
        this.notifyAll();

        return true;
    }


    synchronized T get() {
        try {
            while (linkList.size() == 0) {
                this.wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        T t = linkList.removeFirst();
        System.out.println(Thread.currentThread().getName() + "消费了信息..." + getCount());
        // 通知生产者进行生产
        this.notifyAll();

        return t;
    }


    synchronized int getCount() {
        count = linkList.size();
        return count;
    }

    public static void main(String[] args) {
        TbInterviewTest02 test = new TbInterviewTest02();

        // 消费者
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    test.get();
                }
            }, ("c" + i)).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 生产者
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) {
                    test.put("sd");
                }
            }, ("p" + i)).start();
        }
    }

}
