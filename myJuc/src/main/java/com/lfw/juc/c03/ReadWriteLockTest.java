package com.lfw.juc.c03;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/9 下午4:21
 * @description: 读写锁
 */
public class ReadWriteLockTest {

    static int value = 10;

    static Lock lock = new ReentrantLock();
    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Lock readLock = readWriteLock.readLock();
    static Lock writeLock = readWriteLock.writeLock();

    /**
     * 读信息
     *
     * @param lock
     */
    public static void readInfo(Lock lock) {
        try {
            lock.lock();
            Thread.sleep(1000);
            System.out.println("read...info...value=" + value);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            lock.unlock();
        }
    }

    /**
     * 写信息
     *
     * @param lock
     */
    public static void writeInfo(Lock lock) {
        try {
            lock.lock();
            value++;
            Thread.sleep(1000);
            System.out.println("write...info...value=" + value);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {

//        Long startTime = System.currentTimeMillis();
        // 18个读线程
        for (int i = 0; i < 18; i++) {
            // 普通锁，读写均阻塞
//            new Thread(() -> readInfo(lock)).start();

            // 读写锁中的读锁
            new Thread(() -> readInfo(readLock)).start();
        }

        // 2个写线程
        for (int i = 0; i < 2; i++) {
//            new Thread(() -> writeInfo(lock)).start();
            // 读写锁中的写锁
            new Thread(() -> writeInfo(writeLock)).start();
        }

//        System.out.println("总耗时:" + (System.currentTimeMillis() - startTime));
    }
}
