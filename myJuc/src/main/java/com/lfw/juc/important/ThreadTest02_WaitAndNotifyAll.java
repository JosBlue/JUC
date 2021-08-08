package com.lfw.juc.important;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/11 上午10:30
 * @description: 写一个固定容量同步容器，拥有put和get方法，以及getCount方法，能够支持2个生产者线程以及10个消费者线程阻塞调用
 * notifyall会唤醒所有线程，不一定就能让指定的消费者或者生产者线程获取到资源。
 * 因此该种方法会造成一定的资源浪费，参见第二种方式
 */
public class ThreadTest02_WaitAndNotifyAll<T> {

    LinkedList<T> list = new LinkedList();

    final static int MAX_COUNT = 10;

    final static Object lock = new Object();

    public synchronized void put(T t) {
        while (list.size() == MAX_COUNT) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(t);
        // 通知消费者线程消费
        this.notifyAll();
    }

    public synchronized T get() {

        while (list.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.notifyAll();
        return list.removeFirst();
    }

    public synchronized int getCount() {
        return list.size();
    }

    public static void main(String[] args) {

        ThreadTest02_WaitAndNotifyAll<Integer> test = new ThreadTest02_WaitAndNotifyAll();

        // 消费线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    System.out.println(Thread.currentThread().getName() + "===" + test.get());
                }
            }).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 生产者线程
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) {
                    test.put(j);
                }
            }).start();
        }
    }
}
