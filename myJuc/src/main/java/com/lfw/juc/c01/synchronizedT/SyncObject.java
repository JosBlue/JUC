package com.lfw.juc.c01.synchronizedT;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/7 上午9:38
 * @description: JVM对于synchronized的实现，没有加任何限制，可以被它加锁的，可以是一个对象，也可以是方法等
 */
public class SyncObject {


    int count = 10;

    static int staticCount = 10;

    // 对一个对象加锁(Object不可为空，否则将报空指针异常)
    Object object = new Object();

    /**
     * 当前方法不加锁
     */
    public void countAdd() {
        count++;
        System.out.println(Thread.currentThread().getName() + "当前count值" + count);

    }


    /**
     * 只有拿到object这把锁才可以获取资源
     */
    public void testSyncObject() {
        synchronized (object) {
            count--;
            System.out.println(Thread.currentThread().getName() + "==输出count:" + count);
            try {
                // 中间暂停两秒
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 任何线程想要执行信息，都需要获取this的锁
     */
    public void testSyncThisObject() {
        synchronized (this) {
            count--;
            System.out.println(Thread.currentThread().getName() + "==输出count:" + count);
            try {
                // 中间暂停两秒
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 这种加锁方式，等同于在方法的代码执行时要synchronized(this)
     */
    public synchronized void testSyncMethod() {
        count--;
        System.out.println(Thread.currentThread().getName() + "==输出count:" + count);
        try {
            // 中间暂停两秒
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 给静态方法加锁(相当于锁T.class)
     */
    public synchronized static void testSyncStatic() {
        staticCount--;
        System.out.println(Thread.currentThread().getName() + "==输出staticCount:" + staticCount);
        try {
            // 中间暂停两秒
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 锁T.class
     */
    public static void testSyncStaticMethod() {
        synchronized (SyncObject.class) { // 此处不可修改为this，static方法是没有this的方法
            staticCount--;
            System.out.println(Thread.currentThread().getName() + "==输出staticCount:" + staticCount);
            try {
                // 中间暂停两秒
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        SyncObject syncObject = new SyncObject();

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "==start...");
//            syncObject.testSyncObject();
//            syncObject.testSyncThisObject();
//            syncObject.testSyncMethod();
//            syncObject.countAdd();
//            testSyncStatic();
            testSyncStaticMethod();
        });


        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "==start...");
//            syncObject.testSyncObject();
//            syncObject.testSyncThisObject();
//            syncObject.countAdd();
//            syncObject.testSyncMethod();
//            testSyncStatic();
            testSyncStaticMethod();
        });

        t1.start();
        t2.start();
    }
}
