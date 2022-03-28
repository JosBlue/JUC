> Threadlocal是一个线程内部的存储类，可以在指定线程内存储数据，数据存储以后，只有指定线程可以得到存储数据。  
> Threadlocal通过使用当前线程中的一个map容器，实现只有指定线程可以得到存储的数据  
> 对于Threadlocal的使用，在Spring的声明式事务中，就是通过Threadlocal实现的，通过Threadlocal保证当前线程中获取的所有Connection  
>都是同一个Connection，即在当前线程中，第一次从线程池中获取Connection之后，后续在该线程中所有获取的Connection，都是在Threadlocal  
>内部容器中获取的，以此来保证整个事务  
>Threadlocal的底层实现调用关系：Thread.currentThread.map() 
>
>
Threadlocal t1 = new Threadlocal();  
 >t1.set();  
 >注意：t1指向Threadlocal使用的是强引用，Threadlocal中的map，这个map中的Entry,这个Entry的key指向Threadlocal是一个弱引用  
 >为什么Entry中要使用弱引用？  
 >如果使用的强引用，那么当指向Threadlocal的强引用消失了，即是t1=null,但是key依然是强引用指向ThreadLocal，那么Threadlocal就不会  
 >被垃圾回收器回收，这就会造成内存泄漏，但是使用弱引用就不会了，因为弱引用遭遇GC,就会被回收掉  
 >但是这样还是有问题，虽然Threadlocal被回收了，key=null,就会导致其对应value值再也无法被访问到，因此依旧存在内存泄漏的情况，因此，  
 >在我们使用完ThreadLocal后，一定要记得主动调用其remove方法，来防止内存泄漏!!! 