package com.lfw.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/11/14 下午4:21
 * @description:
 */
public class DefaultWatcher implements Watcher {

    CountDownLatch countDownLatch;

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void process(WatchedEvent event) {

        switch (event.getState()) {
            case Unknown:
                break;
            case Disconnected:
                break;
            case NoSyncConnected:
                break;
            case SyncConnected:
                // 当zk链接成功后，才返回zk信息，通过加锁的方式，进行控制
                countDownLatch.countDown();
                System.out.println("zk链接成功...");
                break;
            case AuthFailed:
                break;
            case ConnectedReadOnly:
                break;
            case SaslAuthenticated:
                break;
            case Expired:
                break;
        }

    }
}
