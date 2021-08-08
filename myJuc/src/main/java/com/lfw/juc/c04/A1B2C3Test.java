package com.lfw.juc.c04;

import java.util.concurrent.locks.LockSupport;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/10 下午11:00
 * @description: 要求两个线程交替打印A1B2C3...Z26 TODO
 */
public class A1B2C3Test {

    volatile static int startIndex = 1;

    volatile static int stringStartIndex = 27;

    volatile static boolean flag = true;


    static Thread t1 = null;
    static Thread t2 = null;

    public static void main(String[] args) {
        t1 = new Thread(() -> {
            while (flag) {
                System.out.println("t1..." + startIndex);
                startIndex++;
                flag = false;
                break;
            }
            LockSupport.park();
            LockSupport.unpark(t2);
        });

        t2 = new Thread(() -> {
            while (!flag) {
                System.out.println("t2..." + stringStartIndex);
                stringStartIndex++;
                flag = true;
                break;
            }
            LockSupport.park();
            LockSupport.unpark(t1);
        });

        t1.start();
        t2.start();
    }
}
