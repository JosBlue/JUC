package com.lfw.juc.c01;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/6 下午5:05
 * @description: 线程中三种比较重要的方法
 * Sleep：线程沉睡，等待沉睡时间完成后，再继续运行
 * Yield：线程让步,使当前线程从执行状态（运行状态）变为可执行态（就绪状态）
 * cpu会从众多的可执行态里选择，也就是说，当前也就是刚刚的那个线程还是有可能会被再次执行到的，并不是说一定会执行其他线程而该线程在下一次中不会执行到了。
 * Join： T2线程中，调用了T1线程的join方法，T2线程会阻塞至T1线程执行完毕后再继续执行
 */
public class T03_Sleep_Yield_Join {

    public static void main(String[] args) {
//        testSleep();
//        testYield();
        System.out.println("main...");
        testJoin();
        System.out.println("main...");
    }


    /**
     * 暂停线程执行
     * 测试sleep方法
     */
    public static void testSleep() {
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                System.out.println("输出信息..." + i);

                // 暂停500ms
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * 使当前线程由执行状态，变成为就绪状态，让出cpu时间，
     * 在下一个线程执行时候，此线程有可能被执行，也有可能没有被执行。
     */
    public static void testYield() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("AAA输出信息..." + i);
                if (i % 10 == 0) {
                    System.out.println("AAA调用yield方法..." + i);
                    Thread.yield();
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("BBB输出信息..." + i);
                if (i % 10 == 0) {
                    System.out.println("BBB调用yield方法..." + i);
                    Thread.yield();
                }
            }
        }).start();
    }


    public static void testJoin() {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                System.out.println("AAA正在执行输出操作" + i);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 30; i++) {
                System.out.println("BBB正在执行输出操作" + i);
            }
        });

        t1.start();
        t2.start();
    }
}
