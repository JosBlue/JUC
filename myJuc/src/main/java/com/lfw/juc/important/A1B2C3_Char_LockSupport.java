package com.lfw.juc.important;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/11 下午5:22
 * @description:
 */
public class A1B2C3_Char_LockSupport {

    static Thread t1 = null;

    static Thread t2 = null;

    public static void main(String[] args) {

        char[] numbers = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};

        char[] words = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'D', 'H', 'I'};

        t1 = new Thread(() -> {
            for (char word : words) {
                System.out.print(word);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        });

        t2 = new Thread(() -> {
            for (char number : numbers) {
                LockSupport.park();
                System.out.print(number);
                LockSupport.unpark(t1);
            }
        });

        t1.start();
        t2.start();
    }

}
