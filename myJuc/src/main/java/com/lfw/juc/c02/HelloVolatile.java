package com.lfw.juc.c02;

import java.util.concurrent.TimeUnit;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/7 下午4:18
 * @description: A B线程都用到一个变量，java默认是A线程中保留一份copy，这样如果B线程修改了该变量，则A线程未必知道
 * 使用volatile关键字，会让所有线程都会读到变量的修改值
 */
public class HelloVolatile {

    volatile Boolean isRunning = true;

    static int count = 10;

    void m() {
        System.out.println("m start...");

        while (isRunning) {
//            System.out.println(Thread.currentThread().getName() + "正在运行..." + isRunning);
        }

        System.out.println("m end...");
    }

    public static void main(String[] args) {

        HelloVolatile helloVolatile = new HelloVolatile();

        new Thread(helloVolatile::m, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        helloVolatile.isRunning = false;
        System.out.println(helloVolatile.isRunning);

//        new Thread(() -> {
//            while (isRunning) {
//                System.out.println(Thread.currentThread().getName() + "==正在运行...");
//                System.out.println(Thread.currentThread().getName() + "==count值===" + count);
//            }
//            System.out.println(Thread.currentThread().getName() + "跳出循环...");
//        }).start();
//
//        new Thread(() -> {
//            while (isRunning) {
//                count--;
//                System.out.println(Thread.currentThread().getName() + "==正在运行...");
//                System.out.println(Thread.currentThread().getName() + "==count值===" + count);
//
//                if (count == 3) {
//                    isRunning = false;
//                }
//            }
//            System.out.println(Thread.currentThread().getName() + "跳出循环...");
//        }).start();
    }

}
