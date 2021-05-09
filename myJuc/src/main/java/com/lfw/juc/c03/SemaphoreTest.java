package com.lfw.juc.c03;

import java.util.concurrent.Semaphore;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/9 下午4:52
 * @description:
 */
public class SemaphoreTest {

    // 允许一个线程获取这把锁
    Semaphore semaphore = new Semaphore(1);

    public void t1Run() {
        new Thread(() -> {
            // 获取当前这把锁
            try {
                semaphore.acquire();
                System.out.println("T1....running start...");
                System.out.println("T1....running end...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放锁
                semaphore.release();
            }
        }).start();
    }

    public void t2Run() {
        new Thread(() -> {
            // 获取当前这把锁
            try {
                semaphore.acquire();
                System.out.println("T2....running start...");
                System.out.println("T2....running end...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放锁
                semaphore.release();
            }
        }).start();
    }

    public static void main(String[] args) {
        SemaphoreTest semaphoreTest = new SemaphoreTest();
        semaphoreTest.t1Run();
        semaphoreTest.t2Run();
    }
}
