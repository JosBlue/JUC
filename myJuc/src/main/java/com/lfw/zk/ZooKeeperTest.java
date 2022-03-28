package com.lfw.zk;

import lombok.SneakyThrows;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/11/10 下午8:39
 * @description:
 */
public class ZooKeeperTest {
    public static void main(String[] args) throws Exception {

        // 如果不做异步的控制，由于new zookeeper返回zk的对象与实际的连接是一个异步的操作，也就是说，zk还没连接上，就已经把这个zk返回去了
        // 要处理这种问题，就要做异步的控制，让连接成功后，再返回zk对象

        CountDownLatch countDownLatch = new CountDownLatch(1);

        // watch的注册只发生在读方法，比如get,exeits
        ZooKeeper zooKeeper = new ZooKeeper("rdzk1.dmall.com:2181,rdzk2.dmall.com:2181,rdzk3.dmall.com:2181", 3000, new Watcher() {

            // watch的回调方法
            @Override
            public void process(WatchedEvent watchedEvent) {
                Event.KeeperState state = watchedEvent.getState();
                Event.EventType type = watchedEvent.getType();
                String path = watchedEvent.getPath();
                System.out.println(watchedEvent.toString());

                switch (state) {
                    case Unknown:
                        break;
                    case Disconnected:
                        System.out.println("Disconnected");
                        break;
                    case NoSyncConnected:
                        break;
                    // 同步连接
                    case SyncConnected:
                        System.out.println("connected");
                        countDownLatch.countDown();
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

                switch (type) {
                    case None:
                        break;
                    case NodeCreated:
                        break;
                    case NodeDeleted:
                        break;
                    case NodeDataChanged:
                        break;
                    case NodeChildrenChanged:
                        break;
                }
            }
        });

        countDownLatch.await();
        ZooKeeper.States state = zooKeeper.getState();
        switch (state) {
            case CONNECTING:
                System.out.println("ing...");
                break;
            case ASSOCIATING:
                break;
            case CONNECTED:
                System.out.println("ed....");
                break;
            case CONNECTEDREADONLY:
                break;
            case CLOSED:
                break;
            case AUTH_FAILED:
                break;
            case NOT_CONNECTED:
                break;
        }

        String pathName = zooKeeper.create("/sss", "testInifo".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println(pathName);


        // 节点信息变更后，触发回调
        byte[] nodeData = zooKeeper.getData(pathName, (Watcher) event -> System.out.println("节点触发了回调..." + event), new Stat());
        System.out.println("获取到数据信息..." + new String(nodeData));

        // 更改信息，触发回调
        Stat stat = zooKeeper.setData(pathName, "test123".getBytes(), 0);
        byte[] nodeData2 = zooKeeper.getData(pathName, new Watcher() {
            @SneakyThrows
            @Override
            public void process(WatchedEvent event) {
                System.out.println("节点触发了回调..." + event);
                // 如果watch穿true，表示直接使用new zk的watch，传入this，则使用当前对象的watch
                zooKeeper.getData(pathName, this, stat);
            }
        },new Stat());
        System.out.println("获取到数据信息2..." + new String(nodeData2));
        System.out.println(stat);

        // 不更改数据版本，看是否会触发回调
        zooKeeper.setData(pathName, "test1234".getBytes(), stat.getVersion());
        byte[] nodeData3 = zooKeeper.getData(pathName, (Watcher) event -> System.out.println("节点触发了回调..." + event), new Stat());
        System.out.println("获取到数据信息3..." + new String(nodeData3));

        // 回调方法(异步)
        // 异步方法，可以避免CPU空转，避免一直等待阻塞
        System.out.println("ansync....start...");
        zooKeeper.getData(pathName, false, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                System.out.println(stat );
                System.out.println(data.toString());
            }
        },"abc");
        System.out.println("ansync....over...");

        System.out.println("end....");

        Thread.sleep(33333333);
    }
}
