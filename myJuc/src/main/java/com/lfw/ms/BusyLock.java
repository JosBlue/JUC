package com.lfw.ms;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2022/3/22 上午9:27
 * @description:
 */
public class BusyLock {

    private int count;

    // 自旋锁状态
    private AtomicInteger state;

    private BusyLock() {
        this.count = 0;
        this.state = new AtomicInteger(0);
    }

    /**
     * 利用 CAS 实现自旋锁
     */
    private void lock() {
        while (!state.compareAndSet(0, 1)) {
//            while (state.get() == 1) {
//
//            }
        }
    }

    /**
     * 解锁
     */
    private void unlock() {
        state.set(0);
    }

    /**
     * 递增 count，使用自旋锁保护 count
     */
    public void incr() {
        lock();
        count = count + 1;
        unlock();
    }

    /**
     * 测试，开启 50 个线程递增 count，每个线程循环 10 万次。
     *
     * @param warnUp
     * @throws Exception
     */
    public static void runTests(boolean warnUp) throws Exception {
        int threads = 50;
        int n = 100000;
        BusyLock bl = new BusyLock();
        Thread[] ts = new Thread[threads];
        long start = System.nanoTime();
        CyclicBarrier barrier = new CyclicBarrier(threads + 1);
        for (int i = 0; i < threads; i++) {
            ts[i] = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        barrier.await();
                        for (int j = 0; j < n; j++)
                            bl.incr();
                        barrier.await();
                    } catch (Exception e) {

                    }
                }
            });
            ts[i].start();
        }
        barrier.await();
        barrier.await();
        for (Thread t : ts) {
            t.join();
        }
        long duration = (System.nanoTime() - start) / 1000000;
        if (!warnUp)
            System.out.println("count= " + bl.count + ",cost:" + duration
                    + "ms");

    }

    public static void main(String[] args) throws Exception {
        // 测试，先 warm up
//        runTests(true);
        // 实际测试
        runTests(false);
    }
}
