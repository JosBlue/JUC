### 总结注意：
> 1. notify()；不释放锁 因此唤醒wait();前提也是需要被唤醒的线程拿到对应的锁，才能执行，否则会等待当前线程执行完毕之后，才能继续执行。
>
### Reentrantlock 源码解析
>  Reentrantlock的lock();方法，底层就是通过AQS实现的，AQS底层结构就是一个使用volatile修饰的state，保证线程间的可见，  
>再加上一个双向链表队列，链表中每个节点上存储的是等待执行的线程（Thread），当有一个线程进行lock的时候，就会去拿state的值，  
>如果为0，就认为是获取到了当前这把锁，如果不为0，那么就说明已经被其他线程占用这把锁了，那么没有获取到这把锁的这个线程，就进入  
>等待队列中，获取到就继续执行，如果是同一个线程再次来获取这把锁，那么他就会将state这个值加1，并继续执行，这个地方也就是线程的  
>可重入  
>对于AQS，底层主要是volatile+CAS，而CAS主要就体现在往链表上添加节点的这个操作上，需要自己下来详细去看看  
>
### AQS
> JDK1.9中，使用了VarHandle，操作Node，使用VarHandle的原因：
>1. 普通属性原子操作  
>2. 相对于JDK1.8用反射而言，它更快，直接操作二进制码  
>>>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/AQS源码.png) 