package com.lfw.juc.c02;

import java.util.concurrent.TimeUnit;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/7 下午4:54
 * @description: volatile 引用类型（包括数组）只能保证引用本身的可见性，不能保证内部字段的可见性
 */
public class VolatileReference {

    boolean isRunning = true;

    volatile static VolatileReference volatileReference = new VolatileReference();

    void m() {
        System.out.println("m...start");

        while (isRunning) {
//            System.out.println(Thread.currentThread().getName() + "==正在运行==" + isRunning);
        }

        System.out.println("m...end");
    }

    public static void main(String[] args) {

        new Thread(volatileReference::m, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        volatileReference.isRunning = false;
        System.out.println(volatileReference.isRunning);
    }


}
