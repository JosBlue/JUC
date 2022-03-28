package com.lfw.ms;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

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
public class SpinLock3 {


    private Thread exclusiveOwnerThread;

    private AtomicInteger state;


    /**
     * 锁可重入的标识
     */
    private  int acquires;

    /**
     * 测试递增count
     */
    private int count;

    private SpinLock3() {
        this.count = 0;
        this.state = new AtomicInteger(0);
    }

    public Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }

    public void setExclusiveOwnerThread(Thread exclusiveOwnerThread) {
        this.exclusiveOwnerThread = exclusiveOwnerThread;
    }

    // 上锁
    public void lock() {

        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "lock...");
        System.out.println(thread.getName() + "----" + state.get());

        if (state.compareAndSet(0, 1)) {
//            ++acquires;
            System.out.println(thread.getName() + "getLock...");
            setExclusiveOwnerThread(thread);
        } else {

            if (thread == getExclusiveOwnerThread()) {
                ++acquires;
                return;
            }

            // cas自旋
            // 其他线程加锁成功也需要计数和设置线程id
            while (!state.compareAndSet(0, 1)) {
//            System.out.println(thread.getName() + "cas...");
            }
        }
    }

    // 解锁
    public void unlock() {
        Thread thread = Thread.currentThread();
        if (thread == getExclusiveOwnerThread()) {
            if (acquires > 0) {
                --acquires;
                if (acquires == 0) {
                    setExclusiveOwnerThread(null);
                    System.out.println(thread.getName() + "unlock");
                    state.set(0);
                    System.out.println(state.get());
                }
            } else {
                System.out.println(thread.getName() + "unlock");
                setExclusiveOwnerThread(null);
                state.set(0);
                System.out.println(thread.getName() + "unlock" + "---" + state.get());
            }
        }
    }

    /**
     * 递增 count，使用自旋锁保护 count
     */
    public void incr() {
        lock();
        count = count + 1;
        System.out.println(Thread.currentThread().getName() + "==" + count);
        unlock();
    }

    // 测试 开启 30 个线程递增 count，每个线程循环1000
    public static void runTest(boolean warnUp) throws Exception {

        SpinLock3 spinLock = new SpinLock3();

        int threads = 5;
        int n = 3;
        Thread[] ts = new Thread[threads];
        long start = System.nanoTime();
        CyclicBarrier barrier = new CyclicBarrier(threads + 1);
        for (int i = 0; i < threads; i++) {
            ts[i] = new Thread(() -> {
                try {
                    barrier.await();
                    for (int j = 0; j < n; j++) {
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
        if (!warnUp) {
            System.out.println("count= " + spinLock.count + ",cost:" + duration
                    + "ms");
        }
    }

    public static void main(String[] args) throws Exception {
        runTest(false);
    }
}
