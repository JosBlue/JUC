package com.lfw.juc.c02.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/8 上午10:19
 * @description: CAS, Synchronized, LongAdder三种加锁方式效率对比
 * 根据执行结果，LongAdder > cas > Synchronized
 */
public class CasVsSyncVsLongAdder {

    int count = 0;

    LongAdder longAdderCount = new LongAdder();

    AtomicInteger atomicCount = new AtomicInteger(0);

    // 100个线程抢占资源进行计算
    List<Thread> threadList = new ArrayList<>();


    /**
     * sync方式叠加
     */
    synchronized void addCount() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    /**
     * LongAdder方式叠加
     */
    void addCountByLongAdder() {
        for (int i = 0; i < 10000; i++) {
            longAdderCount.increment();
        }
    }


    /**
     * Atomic的方式叠加
     */
    void addCountByAtomic() {
        for (int i = 0; i < 10000; i++) {
            atomicCount.incrementAndGet();
        }
    }

    /**
     * 创建100个线程进行抢占资源计算
     */
    List<Thread> getThread() {
        for (int i = 0; i < 100000; i++) {
            threadList.add(new Thread());
        }
        return threadList;
    }


    public static void main(String[] args) {
        CasVsSyncVsLongAdder casVsSyncVsLongAdder = new CasVsSyncVsLongAdder();

        // 100个线程分别执行
        Long syncStartTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            new Thread(casVsSyncVsLongAdder::addCount).start();
        }

        Long casStartTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            new Thread(casVsSyncVsLongAdder::addCountByAtomic).start();
        }

        Long longAdderStartTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            new Thread(casVsSyncVsLongAdder::addCountByLongAdder).start();
        }

        System.out.println("sync计算count值：" + casVsSyncVsLongAdder.count + "；计算时间：" + (System.currentTimeMillis() - syncStartTime));

        System.out.println("Atomic计算count值：" + casVsSyncVsLongAdder.atomicCount + "；计算时间：" + (System.currentTimeMillis() - casStartTime));

        System.out.println("LongAdder计算count值：" + casVsSyncVsLongAdder.longAdderCount + "；计算时间：" + (System.currentTimeMillis() - longAdderStartTime));

    }


}
