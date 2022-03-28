package com.lfw.zk;

import org.apache.zookeeper.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/11/14 下午2:16
 * @description: 分布式配置
 * 源码：
 * https://www.bianchengquan.com/article/610094.html
 * https://blog.51cto.com/u_15127654/4282232
 */
public class ZooKeeperConfigTest {

    private static final String zkAddress = "/test";

    ZooKeeper zk;

    // 01-先链接zk
    @Before
    public void getConn() {
        zk = ZKUtil.getZk();
    }


    // 最终需要关闭zk链接
    @After
    public void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    // 获取zk相关配置信息
    @Test
    public void getZkConfig() {

        //  初始化代码：此处获取数据,使用exists方法替换getData方法，根据是否有相关信息来进行后续操作。getData为直接获取数据
//        try {
//            // 一个watcher,一个回调函数
//            zk.exists("/test", new Watcher() {
//                @Override
//                public void process(WatchedEvent event) {
//
//                }
//            }, new AsyncCallback.StatCallback() {
//                @Override
//                public void processResult(int rc, String path, Object ctx, Stat stat) {
//                    // stat!=null 表示节点已经存在,存在后，则获取相关数据
//                    if(stat != null){
//                        // 我们使用getData还是使用带回调的方法,其中的watcher即为上面定义的watcher
//                        zk.getData("/test", this, new DataCallback() {
//                            @Override
//                            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
//
//                            }
//                        },"123");
//                    }
//
//                }
//            }, "123");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // 写到这里，会发现这个匿名内部类越来越深，越来越复杂，为了精简代码，让获取zk配置信息的这块逻辑更加清晰，只关注获取数据后的操作，为此，我们将这些匿名内部类全部提出来，定义一个新的WatcherCallBack类

        // 定义完对应的WatcherCallBack类后，上面的代码就可以进行精简

        // 重新更改后的代码
        WatcherCallBack watcherCallBack = new WatcherCallBack();
        MyConfig myConfig = new MyConfig();
        watcherCallBack.setZk(zk);
        watcherCallBack.setAddress(zkAddress);
        watcherCallBack.setMyConfig(myConfig);
//        zk.exists(zkAddress, watcherCallBack, watcherCallBack, "aaa");
        watcherCallBack.aWait();

        // 接下来，我们实现对应callBack的逻辑
        // 首先，获取数据时，我们需要先判断节点是否存在，存在时，我们再进行相关操作，节点状态可由AsyncCallback.StatCallback中的stat是否为空进行判断
        // 当stat不为空时，我们就可以获取zk节点上的数据了。所以，在我们的WatcherCallBack我们需要将zk传入

        // 注意，当前的Test与我们定义的WatcherCallBack，其中的所有的回调操作，与我们当前线程都不是同一个，换句话说，就是当我们的callBack操作完成后
        // 当前线程才能继续往下执行，所以就需要一个专门操作数据的类，来建立这种联系
        // 因此，接下里，我们定义数据操作的相关类==MyConfig
        // 定义完MyConfig，就在exists之前，将这个类new出来，并且需要传入WatcherCallBack中
        // 这样，在WatcherCallBack中的DataCallback，在其中拿到数据后，就可以设置到MyConfig中，并进行后续操作
        // 编写WatcherCallBack中的DataCallback逻辑
        // MyConfig放完数据后，在这边就可以看到相关的数据了

//        while (true) {
//            System.out.println(myConfig.getConfig());
//        }

        while (true) {
            if (myConfig.getConfig().equals("")) {
                System.out.println("delete...");
                watcherCallBack.aWait();
            } else {
                System.out.println(myConfig.getConfig());
            }
            System.out.println(myConfig.getConfig());
        }

        // 写到这里，还是存在问题，由于exists是异步的，所以在还没有获取到数据之前，期望整体能够阻塞住
        // 因此，我们考虑，可以将WatcherCallBack整体进行阻塞，即定一个await方法，等待所有流程执行
        // 完成后，再执行exists方法
        // 因此，代码再次产生变化，就成为了watcherCallBack.await();
        // 那么在watcherCallBack内部，就需要对先后顺序进行控制了，我们在执行完对myConfig设置后，
        // 在watcherCallBack中，才算是进行完了所有操作，因此定义CountDownLatch，并在DataCallback中减减

        // 上面，我们完成了对节点中的数据变更的监控，如果有变更，那么就会进入我们的callback方法中
        // 但是除了节点数据的变更，节点本身也是有可能发生变化的
        // 所以接下来，还需要完成对节点被修改后的watch操作，即完善watcherCallBack中的process(WatchedEvent event)方法
        // 换句话说，我们的节点发生变化，会产生相关事件，对这些产生的事件，也需要进行监控，比如节点被创建，被改变等等，
        // 都需要重新获取数据 zk.getData(address, this, this, "bbb");

        // 注意区分节点存在与节点不存在的情况
        // 节点不存在时，线程的countDownLatch.countDown();是由节点被创建时，产生事件，触发回调中的设置数据信息，然后countDownLatch.countDown();
        // 节点存在时，则当获取数据时，触发回调中的设置数据信息，然后countDownLatch.countDown();

        // NodeDeleted可以对取数进行优化

        // 到这里，整体上的大逻辑已经完成了，对于后续的数据处理，就以实际的业务逻辑为准了

        // 延伸，dubbo在zk上的注册与发现


    }


}
