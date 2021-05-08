package com.lfw.juc.c03;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/8 上午10:54
 * @description: reentrantLock可以替代synchronized
 */
public class ReentrantLockTest {

    Lock lock = new ReentrantLock();

    // 公平锁
    Lock lock2 = new ReentrantLock(true);

    void m() {
        try {
            lock.lock(); //synchronized(this)
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
//        synchronized (this) {
//            for (int i = 0; i < 10; i++) {
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(i);
//            }
//        }
    }

    void m2() {
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                try {
                    System.out.println("m2...");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("m2未获取到锁...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 公平锁
     */
    void run() {
        for (int i = 0; i < 10; i++) {
            try {
                lock2.lock();
                System.out.println(Thread.currentThread().getName() + "获取锁...");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock2.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLockTest reentrantLock = new ReentrantLockTest();
        Thread t1 = new Thread(() -> {
//            reentrantLock.m();
            reentrantLock.run();
        });

        Thread t2 = new Thread(() -> {
//            reentrantLock.m2();
            reentrantLock.run();
        });

        t1.start();
        t2.start();

    }
}
