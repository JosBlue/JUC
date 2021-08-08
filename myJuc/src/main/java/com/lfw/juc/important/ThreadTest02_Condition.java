package com.lfw.juc.important;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/11 下午1:31
 * @description: 写一个固定容量同步容器，拥有put和get方法，以及getCount方法，能够支持2个生产者线程以及10个消费者线程阻塞调用
 */
public class ThreadTest02_Condition<T> {

    private LinkedList<T> list = new LinkedList<>();

    private final static Integer MAX_COUNT = 10;

    // 使用Condition的方式单独创建生产者及消费者
    Lock lock = new ReentrantLock();

    // 生产者
    private Condition producer = lock.newCondition();

    // 消费者
    private Condition cousumer = lock.newCondition();

    public void put(T t) {
        try {
            lock.lock();
            while (list.size() == MAX_COUNT) {
                producer.await();
            }
            list.add(t);
            // 唤醒消费者线程
            cousumer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public T get() {
        try {
            lock.lock();
            while (list.size() == 0) {
                cousumer.await();
            }
            // 唤醒生产者线程...
            producer.signalAll();

            return list.removeFirst();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public static void main(String[] args) {

        ThreadTest02_Condition<Integer> condition = new ThreadTest02_Condition();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    System.out.println(Thread.currentThread().getName() + "===" + condition.get());
                }
            }).start();

            for (int j = 0; j < 2; j++) {
                new Thread(() -> {
                    for (int k = 0; k < 25; k++) {
                        condition.put(k);
                    }
                }).start();
            }
        }
    }


}
