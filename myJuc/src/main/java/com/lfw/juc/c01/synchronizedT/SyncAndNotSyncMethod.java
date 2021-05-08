package com.lfw.juc.c01.synchronizedT;

/**
 * 同步和非同步方法是否可以同时调用？
 *
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/7 上午10:59
 * @description:
 */
public class SyncAndNotSyncMethod {

    public static void notSyncMethod() {
        System.out.println(Thread.currentThread().getName() + "非同步方法调用....");
    }

    public synchronized static void syncMethod() {
        System.out.println(Thread.currentThread().getName() + "同步方法调用....");
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("t1 start...");
            try {
                Thread.sleep(5000);
                syncMethod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });


        Thread t2 = new Thread(() -> {
            System.out.println("t2 start...");
            notSyncMethod();
        });

        t1.start();
        t2.start();
    }


}
