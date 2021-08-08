### ThreadLlocal
> Threadlocal是一个线程内部的存储类，可以在指定线程内存储数据，数据存储以后，只有指定线程可以得到存储数据。  
> Threadlocal通过使用当前线程中的一个map容器，实现只有指定线程可以得到存储的数据  
> 对于Threadlocal的使用，在Spring的声明式事务中，就是通过Threadlocal实现的，通过Threadlocal保证当前线程中获取的所有Connection  
>都是同一个Connection，即在当前线程中，第一次从线程池中获取Connection之后，后续在该线程中所有获取的Connection，都是在Threadlocal  
>内部容器中获取的，以此来保证整个事务  
>Threadlocal的底层实现调用关系：Thread.currentThread.map()  
>
### 强软弱虚四种引用 
#### 强引用
> 直接new 一个对象，这就是好强引用  
>
#### 软引用 SoftReference
> 一个对象被软引用指向时，当堆内存不够用了，垃圾回收器就会回收被软引用指向的对象信息  
>软引用可以做缓存用  
>
#### 弱引用 WeakReference
> 只要遭遇GC，就会被回收空间    
> 一般用在容器里  
>
>Threadlocal中对弱引用的使用：  
>>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/Threadlocal中的弱引用.png) 
>Threadlocal t1 = new Threadlocal();  
>t1.set();  
>注意：t1指向Threadlocal使用的是强引用，Threadlocal中的map，这个map中的Entry,这个Entry的key指向Threadlocal是一个弱引用  
>为什么Entry中要使用弱引用？  
>如果使用的强引用，那么当指向Threadlocal的强引用消失了，即是t1=null,但是key依然是强引用指向ThreadLocal，那么Threadlocal就不会  
>被垃圾回收器回收，这就会造成内存泄漏，但是使用弱引用就不会了，因为弱引用遭遇GC,就会被回收掉  
>但是这样还是有问题，虽然Threadlocal被回收了，key=null,就会导致其对应value值再也无法被访问到，因此依旧存在内存泄漏的情况，因此，  
>在我们使用完ThreadLocal后，一定要记得主动调用其remove方法，来防止内存泄漏!!!  

#### 虚引用  PhantomReference
> 虚引用被创建的时候，就需要添加进一个队列，当这个虚引用被回收的，就会往这个队列中放，通知它被回收了  
> 虚引用与弱引用的区别，就是虚引用获取不到其中对应的值  
> Netty中的DirectByteBuffer使用的堆外内存，这是GC无法回收的部分，这个时候就可以用虚引用来操作进行回收  