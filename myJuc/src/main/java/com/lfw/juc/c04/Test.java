package com.lfw.juc.c04;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/10 下午7:52
 * @description:
 */
public class Test {

    List<Integer> list = new ArrayList<>();

    public synchronized boolean add(int value) {
        return list.add(value);
    }

    public synchronized int size() {
        return list.size();
    }

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        Test test = new Test();
        Thread t1 = null;
        Thread t2 = null;

        new Thread(() -> {
            while (true) {
                try {
                    if (test.size() == 5) {
                        semaphore.acquire();
                        System.out.printf("111");
                        semaphore.release();
                        break;
                    } else {
                        semaphore.release();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    if (test.size() != 5) {
                        semaphore.acquire();
                        test.add(i);
                        System.out.println(i);
                    } else {
                        semaphore.release();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
