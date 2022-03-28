package com.lfw.ms;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: fuwei.iu
 * @email: liu_fu_wei@163.com
 * @phone: 183_8410_3208
 * @date: 2022/3/21 下午10:09
 * @version: 1.0
 * @description: <P>
 * java互斥锁和自旋锁有什么差别?
 * 答：
 * 自旋锁是互斥锁的一种实现方式，相较于互斥锁会在等待期间放弃CPU，自旋锁则是不断循环并测试锁的状态，这样就一直占着CPU
 * 因此，线程在申请自旋锁的时候，线程不会被挂起，而是处于忙等的状态
 * </P>
 *
 * <p>
 * 用代码实现一个自旋锁
 * 要求：
 * 1.满足自旋锁的基本特性
 * 2.提供测试代码
 * </p>
 */
public class SpinLock2 {


    /**
     * 此处使用exclusiveOwnerThread Thread标识线程状态，比使用简单的boolean flag可以携带更多的信息
     */
    private AtomicReference<Thread> exclusiveOwnerThread;


    /**
     * 锁可重入的标识
     */
    private int state;

    /**
     * 测试递增count
     */
    private int count;

    private SpinLock2() {
        this.count = 0;
        this.state = 0;
        this.exclusiveOwnerThread = new AtomicReference<>();
    }


    /**
     * 上锁
     */
    public void lock() {
        Thread thread = Thread.currentThread();
        if (thread == exclusiveOwnerThread.get()) {
            ++state;
            return;
        }

        // cas自旋
        while (!exclusiveOwnerThread.compareAndSet(null, thread)) {

        }
    }


    /**
     * 解锁
     */
    public void unlock() {
        Thread thread = Thread.currentThread();
        if (thread == exclusiveOwnerThread.get()) {
            if (state > 0) {
                --state;
                if (state == 0) {
                    exclusiveOwnerThread.set(null);
                }
            } else {
                exclusiveOwnerThread.set(null);
            }
        }
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
     * 测试，开启 threadCunt 个线程递增 count，每个线程循环threadLoopCount千次
     *
     * @param threadCunt      线程数量
     * @param threadLoopCount 循环次数
     * @throws Exception
     */
    public static void runTest(int threadCunt, int threadLoopCount) throws Exception {

        SpinLock2 spinLock = new SpinLock2();

        Thread[] ts = new Thread[threadLoopCount];
        long start = System.nanoTime();
        CyclicBarrier barrier = new CyclicBarrier(threadLoopCount + 1);
        for (int i = 0; i < threadLoopCount; i++) {
            ts[i] = new Thread(() -> {
                try {
                    barrier.await();
                    for (int j = 0; j < threadCunt; j++) {
                        spinLock.incr();
                    }
                    barrier.await();
                } catch (Exception e) {

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
        System.out.println("count= " + spinLock.count + ",cost:" + duration + "ms");
    }

    public static void main(String[] args) throws Exception {
        runTest(50, 1000);
    }
}