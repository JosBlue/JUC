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
public class SpinLock {

    /**
     * 当前线程信息
     */
    private Thread exclusiveOwnerThread;

    /**
     * 锁状态（0：无锁，>0：已加锁）
     */
    private AtomicInteger state;

    /**
     * 锁可重入的标识
     */
    private volatile int acquires;

    /**
     * 测试递增count
     */
    private int count;

    private SpinLock() {
        this.count = 0;
        this.state = new AtomicInteger(0);
    }

    public Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }

    public void setExclusiveOwnerThread(Thread exclusiveOwnerThread) {
        this.exclusiveOwnerThread = exclusiveOwnerThread;
    }

    // 加锁
    public void lock() {
        Thread thread = Thread.currentThread();

        // 锁重入
        if (thread == getExclusiveOwnerThread()) {
            ++acquires;
            return;
        }

        // cas自旋
        while (true) {
            if (state.compareAndSet(0, 1)) {
                ++acquires;
                setExclusiveOwnerThread(thread);
                break;
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
                    state.set(0);
                }
            } else {
                setExclusiveOwnerThread(null);
                state.set(0);
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
     * 测试，开启 threadCunt 个线程递增 count，每个线程循环threadLoopCount次
     *
     * @param threadCount     线程数量
     * @param threadLoopCount 循环次数
     * @throws Exception 异常信息
     */
    public static void runTest(int threadCount, int threadLoopCount) throws Exception {
        SpinLock spinLock = new SpinLock();
        Thread[] ts = new Thread[threadLoopCount];
        long start = System.nanoTime();
        CyclicBarrier barrier = new CyclicBarrier(threadLoopCount + 1);
        for (int i = 0; i < threadLoopCount; i++) {
            ts[i] = new Thread(() -> {
                try {
                    barrier.await();
                    for (int j = 0; j < threadCount; j++) {
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
        runTest(20, 1000);
    }

    /**
     * 另：优化思路（注：以下方法与上诉实现不产生直接关系，仅表达优化思想）
     * 简单实现，仅通过state值判断是否获取锁，不考虑锁重入等问题，在lock方法中的自旋锁判断处可做优化
     * 通过增加一个内循环 while (state.get() == 1)，让没有获得锁的线程在 state 状态上等待，
     * 避免原子操作，可以减少总线争用。每个忙等待的 CPU 都将获得 state 状态的共享副本，
     * 以后的循环都将在共享副本上执行，不会再产生总线通信。
     * 测试是否增加内层循环的 lock 方法，在我的机器上有比较明显的差异(mac环境，8G内存；测试样本：50个线程，每个线程循环10万次)，没有内层循环的 lock 版本耗时在 3.5-4.5 秒，而增加了内层循环的 lock 耗时在 1.5 -2.2 秒。
     * 见下方示例代码：
     */
//    private void lock() {
//        while (!state.compareAndSet(0, 1)){
//            //对于未能取得锁所有权的线程，在内层循环上等待
//            //因为获取了 state 一份共享的高速缓存副本，
//            //不会再进一步产生总线通信量
//            while (state.get() == 1){}
//         }
//    }

}
