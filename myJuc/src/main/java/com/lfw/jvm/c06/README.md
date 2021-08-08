## JVM调优理论基础知识

> JL：  
>熟悉GC常用算法，熟悉常见垃圾回收器，具有实际JVM调优实战经验  

### 1、什么是垃圾？  
>没有任何引用指向一个或多个对象，那么它就变成了垃圾（注意：对象之间循环引用的问题）      
>java对于垃圾会进行自动回收，自动回收在系统上不容易出错，编程上简单；类似于C++/C这样的语言，需要手动回收，手动回收会存在两个方面的问题：  
>（1）忘记回收垃圾  
>（2）多次回收垃圾  


### 2、如何定位垃圾

> 定位垃圾主要有两种算法：  
>(1)引用计数法（ReferenceCount）  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/引用计数无法解决的问题(循环引用).png) 
>任何一个对象引用了A，A的引用计数就加1，当引用失效时，A对象的引用计数就减1，当A对象的引用数量为0时，就表明A对象没有被引用了，就可以进行回收了  
>但是引用计数法有一个致命的缺点，就是循环引用的对象无法被回收    



>(2)根可达算法（RootSearching）   
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/根可达算法.png)
>从根开始找，如果对象可以被找到，那么就不是垃圾，否则就是垃圾可以被回收  
>可以作为GC Root的，在java中有四种：  
>
>(1)JVM Stacks（线程栈变量）
>Java程序从main方法开始执行，main方法会开启一个线程，这个线程里有线程栈，里面有栈帧。 从main开始这个线程栈帧里面的这些个叫做根对象。     
  
>(2)static references in method (静态变量)  
>class被load到内存中时，静态变量会最先被加载并被初始化，静态变量访问到的对象，也是根对象

>(3)run-time constant pool 常量池  

>(4)Clazz JNI指针  


### 3、常见的垃圾回收算法
>(1)标记清除法（mark sweep）   
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/标记清除方法.png)
>标记清除法，简而言之，就是给找到的垃圾打标，然后将标记好的垃圾进行清除即可    

>标记清除法的优/缺点：这种方法实现简单，但是会产生碎片，导致内存空间不连续。 效率偏低（两遍扫描）

>(2)拷贝清除法（copy）  
![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/拷贝算法.png)
>拷贝清除法，将空间区域一分为二，如A区间和B区间，产生的对象先放在A区间，当A区间被占用到一定程度时，就将A区间中不是垃圾对象，拷贝到B区间，然后一次性将A区间中的垃圾给回收掉。  

>拷贝清除法的优缺点：这种方法不会产生碎片，所以不会出现空间不连续的问题，但是由于将空间一分为二了，因此会造成一定的空间浪费。   


>(3)标记压缩清除法（mark compact）  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/标记压缩法.png)
>标记压缩清除法，将找到的垃圾，移动到空间最前面的位置，让垃圾占用的位置变成连续性的，然后再回收掉对应的垃圾。  

>标记压缩清除法优缺点：这种方法不会产生碎片，并且也没有将空间一分为二，所以不会造成空间浪费。  
>但是这种方式，由于定位到垃圾后，可能需要移动对应的垃圾，所以需要调整指针，所以执行效率上相较于前两种，会偏低一些。两遍扫描。    

### 4、JVM内存分代模型（用于分代垃圾回收算法）

#### 1、部分垃圾回收器使用的模型
> 除Epsilon ZGC Shenandoah之外的GC都是使用逻辑分代模型  
> G1是逻辑分代，物理不分代  
> 除此之外不仅逻辑分代，而且物理分代  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/堆内存逻辑分区.png)

#### 2、 新生代 + 老年代 + 永久代（1.7）Perm Generation/ 元数据区(1.8) Metaspace
> 1. 永久代/元数据 - 主要存储如：编译后的Class信息，字节码信息等  
> 2. 1.7永久代必须指定大小限制，启动后无法修改；1.8元数据可以设置，也可以不设置，无上限（受限于物理内存）  
> 3. 字符串常量:1.7存放于永久代，1.8存放于堆  
> 4. MethodArea逻辑概念 - 永久代、元数据  
> 5.新生代+老年代==》堆；永久代/元数据区==》堆之外的区域
   
#### 3、 新生代 = Eden + 2个suvivor区 
> 1. YGC回收之后，大多数的对象会被回收，活着的进入s0  
> 2. 再次YGC，活着的对象eden + s0 -> s1  
> 3. 再次YGC，eden + s1 -> s0  
> 4. 年龄足够 -> 老年代 （15 CMS 6）  
> 5. s区装不下 -> 老年代  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/对象什么时候进入老年代.png)


#### 4、 老年代
> 1. 顽固分子  
> 2. 老年代满了FGC Full GC  

   
#### 5、 GC Tuning (Generation)
> 1. 尽量减少FGC    
> 2. MinorGC = YGC  
> 3. MajorGC = FGC  
>
>
#### 6、 对象分配过程图
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/对象分配过程详解.png)
>注：TLAB（Thread Local Allocation Buffer）  
>TLAB是虚拟机在堆内存的eden划分出来的一块专用空间，是线程专属的。默认分配1%。  
>在虚拟机的TLAB功能启动的情况下，在线程初始化时，虚拟机会为每个线程分配一块TLAB空间，只给当前线程使用。  
>这样每个线程都单独拥有一个空间，如果需要分配内存，就在自己的空间上分配，这样就不存在竞争的情况，可以大大提升分配效率。  

#### 7、动态年龄
>如果在Survivor空间中相同年龄所有对象大小的总和大于Survivor空间的一半，年龄大于或等于该年龄的对象就可以直接进入老年代.  
>无须等到MaxTenuringThreshold中要求的年龄。    
>注意：动态对象年龄判断，主要是被TargetSurvivorRatio这个参数来控制。  
>而且算的是年龄从小到大的累加和，而不是某个年龄段对象的大小。   
>https://www.jianshu.com/p/989d3b06a49d 
>
#### 8、分配担保
>YGC期间 survivor区空间不够了 空间担保直接进入老年代    
>参考：https://cloud.tencent.com/developer/article/1082730
>

### 5、常见的垃圾回收器
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/常见垃圾回收器.png)

>从JDK诞生开始，Serial追随，为了提高效率，诞生了PS（Parallel Scavenge），为了配合CMS，诞生了PN。  
>CMS是1.4版本后期引入，CMS是里程碑式的GC，它开启了并发回收的过程，但是CMS毛病较多，因此目前任何一个JDK版本默认都不是CMS  
> 并发垃圾回收是因为无法忍受STW 

>#### 1、Serial 用于年轻代垃圾回收，串行回收
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/Serial.png)
>

>#### 2、Parallel Scavenge(PS) 用于年轻代垃圾回收，并行回收
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/ParallelScavnge.png)
>

>#### 3、Par New(PN) 用于年轻代垃圾回收，配合CMS的并行回收
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/ParNew.png)
>

>#### 4、Serial Old 用于老年代垃圾回收，串行回收
>通常与Serial组合，但是在CMS达到一定阀值时，会使用该垃圾回收器进行垃圾回收
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/SerialOld.png)


>#### 5、Parallel Old（PO） 用于老年代垃圾回收，并行行回收
>通常与PS组合
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/Paralle Old.png)


>#### 6、ConcurrentMarkSweep(CMS) 用于老年代垃圾回收，并发回收
>>通常与PN组合  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/CMS.png)
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/CMS从线程角度理解.png)
>垃圾回收和应用程序同时运行，降低STW的时间(200ms)  
>CMS问题比较多，所以现在没有一个版本默认是CMS，只能手工指定  
>CMS既然是MarkSweep，就一定会有碎片化的问题，碎片到达一定程度，CMS的老年代分配对象分配不下的时候，使用SerialOld 进行老年代回收  
>想象一下：  
>PS + PO -> 加内存 换垃圾回收器 -> PN + CMS + SerialOld（几个小时 - 几天的STW）  
>几十个G的内存，单线程回收 -> G1 + FGC 几十个G -> 上T内存的服务器 ZGC  
>

