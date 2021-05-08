package com.lfw.juc.c01;

import java.util.concurrent.TimeUnit;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/6 下午4:27
 * @description:
 */
public class T01_WhatIsThread {

    private static class T1 extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.MICROSECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("T1===" + i);
            }
        }
    }

    public static void main(String[] args) {
        T1 t1 = new T1();
//        t1.run();
        t1.start();

        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.MICROSECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("main===" + i);
        }
    }
}
