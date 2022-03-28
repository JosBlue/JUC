package com.lfw.zk;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/11/14 下午4:55
 * @description: 服务于获取zk配置信息时，需要编写的各种匿名内部类
 */
public class WatcherCallBack implements Watcher, AsyncCallback.StatCallback, AsyncCallback.DataCallback {


    private ZooKeeper zk;

    private String address;

    private MyConfig myConfig;

    CountDownLatch countDownLatch = new CountDownLatch(1);

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMyConfig(MyConfig myConfig) {
        this.myConfig = myConfig;
    }


    public void aWait() {
        zk.exists(address, this, this, "aaa");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Watcher：Watch数据的变化,用于监测节点数据的变化
    @Override
    public void process(WatchedEvent event) {

        switch (event.getType()) {
            case None:
                break;
            case NodeCreated:
                zk.getData(address, this, this, "bbb");
                break;
            case NodeDeleted:
                // 节点删除，由你系统的容错性决定，即你的系统是否允许相关节点不存在，但是配置中还是存在相关数据的这种情况存在
                // 如设置清空
                myConfig.setConfig("");
                // 此处需要对门闩锁进行重新赋值，获取的若为空，表示已经减过锁了，那么此处就需要重新定义锁
                countDownLatch = new CountDownLatch(1);
                break;
            case NodeDataChanged:
                System.out.println("节点数据变更...");
                zk.getData(address, this, this, "bbb");
                break;
            case NodeChildrenChanged:
                break;
        }

    }

    // AsyncCallback.StatCallback：节点状态变化的回调，根据节点数据的变化，做出相关回调操作
    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        if (stat != null) {
            // 此处的watcher以及callBack，当前类本身就是这两种类型，同时，也是对数据的监控，所以，直接均放入自己就行
            zk.getData(address, this, this, "bbb");
        }
    }


    // AsyncCallback.DataCallback：数据的变化，最终的数据返回，根据业务对数据进行处理
    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {

        if (Objects.nonNull(data)) {
            myConfig.setConfig(new String(data));
            countDownLatch.countDown();
        }
    }
}
