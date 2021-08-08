###详解Class加载过程--linking
>![javatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/class加载过程02.png)
#### 1、Linking
> linking的整体过程  
>1、Verification ==>验证文件是否符合JVM规定  
>2、Preparation ==> 正式为类变量（static）分配内存，静态成员变量赋默认值  
>3、Resolution ==> 将类、方法、属性等符号引用解析为直接引用（常量池中的各种符号引用解析为指针、偏移量等内存地址的直接引用）

#### 2、Initializing
> 调用类初始化代码，给静态成员变量赋初始值  
>
#### 小总结
 > load-默认值-初始值（类linking到Initializing的过程）  
 > new-申请内存-默认值-初始值（new Object()的过程）  

#### 3、JMM(java Memory Model) java内存模型
>(1)存储器的层次结构（图）     
>![javatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/存储器的层次结构.png) 
>(2)缓存行对齐、伪共享（图） 
>![javatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/缓存行伪对齐共享.png)
 
##### 1、解决CPU的数据一致性的方法  
>(1) 总线锁（图）  
>![javatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/总线锁.png)
> 使用总线锁的方式解决伪共享的问题，老的CPU才会使用，因为效率偏低  
>(2) 各种各样的缓存一致性协议（MESI图）  
>![javatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/MESI.png)
>新的CPU使用各种各样的缓存一致性协议来解决伪共享的问题  
>MESI就是通过四种不同的标记，来解决缓存伪共享的问题   
>M：当前CPU对这个信息进行过更改，这个信息就会被标记为Modify  
>E：当前CPU独占这个信息，没有其他CPU使用这个信息，这个信息就会被标记为Exclusive  
>S：当前信息被多个CPU共享，都在使用，那么这个信息就会被标记为Shared  
>I：当前信息被别的CPU更改过，那么这个信息就是失效了，这个信息就会被标记为Invalid  
>
>#### 注意：现在CPU的数据一致性是通过缓存锁（MESI...）+总线锁实现的  

##### 2、缓存行（cache line）、伪共享
>读取缓存，以缓存行（cache line）为基本单位，通常为64bytes（字节）    
>位于同一缓存行的两个不同数据，被两个CPU锁定，产生相互影响的伪共享问题  
>缓存行伪共享问题：对于有些特别敏感的数字，就存在线程竞争的访问，为了保证不发生缓存行伪共享，可以使用缓存行对齐的编程方式
 JDK7，采用了Long padding提高效率
 JDK8，加入了@Contended注解来解决这个问题，执行时，必须加上虚拟机参数-XX:-RestrictContended
 详细信息见：https://www.cnblogs.com/Binhua-Liu/p/5620339.html

##### 3、乱序问题
> CPU为了提高指令执行效率，会在一条指令执行过程中（比如去内存中读取数据（这比CPU指令执行慢100倍）），去同时执行另一条指令，前提是两条指令没有依赖关系   
>图：CPU的乱序执行  
>合并写  
>一般是4个字节 由于ALU速度太快，所以在写入L1缓存的同时，写入了一个WC Buffer，满了之后，在直接更新到L2


#### 4、如何保证特定情况下不乱序
> 1、volatile 可以禁止指令重排序，保证有序  

