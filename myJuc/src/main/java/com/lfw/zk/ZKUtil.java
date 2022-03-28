package com.lfw.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/11/14 下午2:30
 * @description: ZK工具类
 */
public class ZKUtil {

    private static ZooKeeper zooKeeper;

    // zk链接地址
    private static String zkAddress = "";

    private static CountDownLatch countDownLatch = new CountDownLatch(1);


    // zk链接
    public static ZooKeeper getZk() {
        try {
            DefaultWatcher defaultWatcher = new DefaultWatcher();
            zooKeeper = new ZooKeeper(zkAddress, 3000, defaultWatcher);
            defaultWatcher.setCountDownLatch(countDownLatch);
            // 等待zk链接成功
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zooKeeper;
    }


}
