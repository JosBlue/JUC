## 操作系统原理
### 1.书籍推荐：
##### 读书原则：观其大略 不求甚解
>《编码：隐匿在计算机软硬件背后的语言》  
>《深入理解计算机系统》  
>语言：C；JAVA  
>数据结构与算法：  
>《java数据结构与算法》 算法  
>《算法导论》《计算机程序设计艺术》// 难    
>操作系统：《Linux内核设计与实现》 《Linux内核源码解析》 《30天自制操作系统》  
>网络：机工《TCP/IP详解》卷一 （翻译一般）  
>编译原理：机工 《编译原理》 《编程语言实现模式》  
>数据库：SQLite源码 Derby/  
>
### 2.汇编语言
> 汇编语言的本质：助记符
 

### 3.计算机的组成
> ![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/计算机的组成.png)  

### 4.CPU的基本组成（CPU是一个协调器）
>（1）PC->Program Counter 程序计数器（记录当前指令地址）  
>（2）Register ->寄存器 暂时存储CPU计算需要用到的数据  
>（3）ALU -> Arithmetic & Login Unit 运算单元  
>（4）CU -> Control Unit 控制单元  
>（5）MMU -> Memory Management Unit 内存管理单元  
>（6）cache -> 缓存  
#### 以下对缓存进行单独说明：
>（1）CPU 缓存分为3级，L1离CPU最近，离CPU越近，速度越快  
> ![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/CPU缓存结构.png)  
<br/>
>（2）缓存一致性协议（MESI）  
> ![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/缓存一致性协议MESI.png)
>   注：CPU中的缓存，为了保证数据的一致性，存在缓存一致性协议（MESI），但有些无法被缓存的数据，或者跨越多个缓存行的数据，就会使用更高级别的总线锁
<br/>
> 缓存行：  
>（1）缓存行越大，局部性空间效率越高，但读取时间慢  
>（2）缓存行越小，局部性空间效率越低，但读取时间快  
>Inter公司经过实验研究以后，取了一个折中值，目前多用：64字节  
<br/>
> 缓存行伪共享问题：对于有些特别敏感的数字，就存在线程竞争的访问，为了保证不发生缓存行伪共享，可以使用缓存行对齐的编程方式  
>JDK7，采用了Long padding提高效率    
>JDK8，加入了@Contended注解来解决这个问题，执行时，必须加上虚拟机参数-XX:-RestrictContended   
>详细信息见：https://www.cnblogs.com/Binhua-Liu/p/5620339.html

### 4.CPU的乱序执行
> cpu的乱序执行的根本目的：提高效率
> ![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/CPU乱序执行.png)
>**问题：DCL单例为什么要加volatile？  
>原因：禁止指令重排序   
>加问：DCL（Double check Lock）到底需不需要volatile？  
>回答：需要，因为不加volatile会发生指令重排序而出现异常  

##### 问题：CPU层面如何禁止重排序？  
>回答：内存屏障。对某部分内存做操作时前后添加的屏障，屏障前后的操作不可以乱序执行。  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/内存屏障.png)  
>也可以使用原语来实现：lfence (load 读屏障)，sfence(save 写屏障),mfence(mix 读写屏障)；当然，也可以使用总线锁来解决    
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/CPU屏障原语.png) 
>上述的几种方式，都是在CPU层面来操作，然后做到禁止指令重排序的  

##### 问题：JVM层面如何实现内存屏障的？
###### 对于禁止指令排序，有8个happens-before原则；
###### 对于内存屏障，有4个内存屏障（LL；LS；SL；SS）类型  
> JVM层面为了实现内存屏障，简单来说，就是规定读与读，写与写，读与写，写与读这四个层面的顺序不可互换，也就是说JVM就这么去规定了，具体怎么去实现，  
>实际底层也就是lock,见下图：  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/JRS内存屏障.png) 
>咱们再看看volatile的实现细节，见图：  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/volatile实现细节.png) 
>对于上图进行简单说明，如果对某一块内存，被volatile修饰，会在其前后加屏障。    
>如果是写操作，那么在volatile前面有一个写写操作的屏障，在volatile后面有一个写加读操作的屏障，  
>有了这两个屏障，就保证了只有在写成功以后，才能进行读操作，这是绝对不可以互换的，也因此，通过volatile保证了可见性以及禁止指令重排序。  
>如果是读操作，那么在volatile前面会有一个读读操作的屏障，在volatile后面有一个读加写操作的屏障。  
>也就是说，在读之前会先读取信息，然后读取完成后，再进行写，且这几条指令不可打乱，通过这些操作，保证了可见性即禁止指令重排序。 
> 注意，这是JVM要求要这么去实现的，也就说这是volatile的实现思路，但是具体怎么去实现的，实际上还是加锁。  

>hanppens-before原则（JVM规定的重排序必须遵守的8条规则）--》不需要记，知道有这8条原则就行。   

### 5.底层中的优化（了解）==》合并写（WC --> Write Combining Buffer）
> 一般是4个字节
> 由于ALU速度太快，所以在写入L1缓存的同时，写入了一个WC Buffer，满了之后，在直接更新到L2  

### 6.底层中的优化==》NUMA（Non Uniform Memory Access)
> ZGC- NUMA aware  
>分配内存会优先分配该线程所在的CPU的最近内存  