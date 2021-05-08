### 1. 线程状态
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/线程状态.png) 
#### 1.1 关于线程的相关说明：
>关闭线程：现在没有这样的说法了，Stop已经不建议是使用了
>
>#### 1.2 synchronized关键字： 也被称为悲观锁
>JVM对于synchronized的实现没有任何限制和要求，因此synchronized可以锁住许多信息：
>从语法上讲，Synchronized可以把任何一个非null对象作为"锁"
>（1）使用在静态方法上，synchronized锁住的是类对象    
>（2）使用在实例方法上，synchronized锁住的是实例对象。   
>（3）synchronized的同步代码块用在类实例的对象上，锁住的是当前的类的实例。  
>
>在HotSpot JVM实现中，锁有个专门的名字：对象监视器（Object Monitor）。  
>在HotSpot源码中，对于synchronized的底层实现，是锁住了对象的头两位，makeword，根据头两位的信息不同，表示不同的锁。  
>
>
>Synchronized的作用主要有三个：    
（1）原子性：确保线程互斥的访问同步代码；  
（2）可见性：保证共享变量的修改能够及时可见。
>   其实是通过Java内存模型中的 “对一个变量unlock操作之前，必须要同步到主内存中；
>   如果对一个变量进行lock操作，则将会清空工作内存中此变量的值，在执行引擎使用此变量前，需要重新从主内存中load操作或assign操作初始化变量值” 来保证的；  
（3）有序性：有效解决重排序问题，即 “一个unlock操作先行发生(happen-before)于后面对同一个锁的lock操作”；  
>
>根据synchronizedT中包含的信息，总结相关信息  
>
>
#### 1.3 synchronized锁升级的底层实现过程：
> synchronized在被线程第一次被用到时，为了保证效率，并没有被加锁，只是记录当前线程的id，此时被称为偏向锁  
>后面随着访问线程的增多，越来越多的线程来竞争这把锁，没有获取到锁的，就升级为了自旋锁，在JDK1.6中默认时自旋10次，  
>JDK目前规定为自旋线程超过CPU内核数的一半时，就升级为重量级锁，这个时候就有操作系统进行管理。  
>注意，锁可以升级，但是不能降级  
>
> 对于synchronized与cas加锁怎么取舍：  
> 执行时间短（加锁代码），线程数少，用自旋  
>执行时间长，线程数多，用系统锁  