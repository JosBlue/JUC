package com.lfw.juc.c01;

import java.util.concurrent.TimeUnit;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/6 下午4:46
 * @description: 创建线程池
 * 创建多线程的三种方式：
 * 1.继承Thread类
 * 2.实现Runnable接口
 * 3.通过线程池创建：Executors.newCachedThread
 */
public class T02_HowToCreateThread {

    /**
     * 继承Thread方式创建
     */
    public static class ThreadTest extends Thread {

        @Override
        public void run() {
            try {
                TimeUnit.MICROSECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("This is extends Thread...");
        }
    }

    /**
     * 实现Runnable接口
     */
    public static class RunTest implements Runnable {

        @Override
        public void run() {
            System.out.println("This is implement Runnable...");
        }
    }

    public static void main(String[] args) {
        // Thread
        new ThreadTest().start();

        // Runnable
        new Thread(new RunTest()).start();

        // java8 Lambda方式穿件
        new Thread(() -> System.out.println("This is Lambda...")).start();


        // main
        System.out.println("main");
    }
}
