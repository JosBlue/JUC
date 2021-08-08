### 内存屏障与JVM指令
#### 1、保证有序性
硬件内存屏障 X86

>  sfence:  store| 在sfence指令前的写操作当必须在sfence指令后的写操作前完成。  
>  lfence：load | 在lfence指令前的读操作当必须在lfence指令后的读操作前完成。  
>  mfence：modify/mix | 在mfence指令前的读写操作当必须在mfence指令后的读写操作前完成。

> 原子指令，如x86上的”lock …” 指令是一个Full Barrier，执行时会锁住内存子系统来确保执行顺序，甚至跨多个CPU。Software Locks通常使用了内存屏障或原子指令来实现变量可见性和保持程序顺序

JVM级别如何规范（JSR133）

> LoadLoad屏障：
>   	对于这样的语句Load1; LoadLoad; Load2， 
>
>  	在Load2及后续读取操作要读取的数据被访问前，保证Load1要读取的数据被读取完毕。
>
> StoreStore屏障：
>
>  	对于这样的语句Store1; StoreStore; Store2，
>	
>  	在Store2及后续写入操作执行前，保证Store1的写入操作对其它处理器可见。
>
> LoadStore屏障：
>
>  	对于这样的语句Load1; LoadStore; Store2，
>	
>  	在Store2及后续写入操作被刷出前，保证Load1要读取的数据被读取完毕。
>
> StoreLoad屏障：
> 	对于这样的语句Store1; StoreLoad; Load2，
>
> ​	 在Load2及后续所有读取操作执行前，保证Store1的写入对所有处理器可见。

volatile的实现细节

1. 字节码层面
   ACC_VOLATILE

2. JVM层面
   volatile内存区的读写 都加屏障

   > StoreStoreBarrier
   >
   > volatile 写操作
   >
   > StoreLoadBarrier

   > LoadLoadBarrier
   >
   > volatile 读操作
   >
   > LoadStoreBarrier

3. OS和硬件层面
   https://blog.csdn.net/qq_26222859/article/details/52235930
   hsdis - HotSpot Dis Assembler
   windows lock 指令实现 | MESI实现

synchronized实现细节

1. 字节码层面  
   单纯加锁 ACC_SYNCHRONIZED  
   独立方法层面 monitorenter monitorexit  
2. JVM层面  
   C C++ 调用了操作系统提供的同步机制  
3. OS和硬件层面  
   X86 : lock cmpxchg / xxx
   [https](https://blog.csdn.net/21aspnet/article/details/88571740)[://blog.csdn.net/21aspnet/article/details/](https://blog.csdn.net/21aspnet/article/details/88571740)[88571740](https://blog.csdn.net/21aspnet/article/details/88571740)

#### 2、对象内存布局  
> 1、关于对象  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/一线互联网的面试题.png)
> (1)针对问题1： 对象的创建过程 
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/对象的创建过程.png)
>
>(2)对象在内存中的存储布局    
>>  1.普通对象
>>>   1.markword 对象头 8字节  
>>>   2.classPoint .class 的指针 -XX:+UseCompressedClassPointers 开启后会被压缩为为4字节 不开启为8字节  
>>>   3.实例数据(instance)   
>           1. 引用类型：-XX:+UseCompressedOops 为4字节 不开启为8字节   
            Oops Ordinary Object Pointers  
>     4.Padding对齐 8的倍数  
>          对齐填充并不是必然存在的，也没有特别的含义，它仅仅起着占位符的作用。  
>          为什么需要有对齐填充呢？由于hotspot VM的自动内存管理系统要求对象起始地址必须是8字节的整数倍。  
>          换句话，就是对象的大小必须是8字节的整数倍。而对象头正好是8字节的倍数。因此，当对象实例数据部分没有对齐时，就需要通过对齐填充来补全。  
>
>   2.数组对象  
>       1.markword 对象头 8字节  
>       2.classPoint 同上
>       3.数组数据（同实例数据）  
>       4.padding 对齐    
>       5.数组长度     
>
>（3）对象头具体包括什么？  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/markword结构.png)
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/markword64位.png)
>
>（4）对象怎么定位？  
>       1. 句柄池（如：T t = new T(); 两个指针，一个指向实际对象，一个指向T.class）  
        2. 直接指针   
        两者的优劣：没有优劣之分，Hotspot使用了第二种，第二种效率更高，但是第一种对于垃圾回收而言，效率更高    
>
>(5) 对象怎么分配？  
> GC相关内容，等待后续解释  
>
>(6) Object o = new Object();在内存中占多少字节？  
> 16个字节  