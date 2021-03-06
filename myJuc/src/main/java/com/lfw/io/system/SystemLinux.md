## 进程 线程 纤程（协程） 中断
> 高频面试题：进程和线程有什么区别？   
>70分左右的普通回答：进程就是一个程序运行起来的状态。线程是进程中不同的执行路径  
> 更加专业的回答：进程是操作系统（OS）用来分配资源的基本单位。线程是操作系统（OS）执行调度的基本单位。   
> 分配资源最重要的是：分配独立的内存空间，线程调度执行  
>线程与进程最大的区别：线程共享进程的内存空间，没有自己独立的内存空间。    
>在Linux中，线程就是一个普通的进程，只不过是共享内存空间，全局数据等    
>纤程：用户态的线程，线程中的线程，切换和调度不需要经过OS  

>### 1. 纤程（协程）的优势：  
>纤程用一句话说：就是用户空间级的线程  
>（1）占用资源很少：操作系统启动一个线程，需要1M的内存空间；但是启动一个纤程（Fiber），则只需要4K的内存空间  
>（2）切换比较简单：纤程的调度不需要经过OS  
>（3）可以启动很多个纤程：10W+  
>截止至2020年3月，系统内置支持纤程的语言有：Scala Go Python(lib) Kotlin...java在类库级别可以支持  

### 2. java中对纤程（协程）支持的现状
>java中对于纤程的支持：没有内置，盼望内置  
>java利用Quaser库进行支持纤程，但是不够成熟，如果用于生产环境，会存在许多问题。   


>### 3. 纤程（协程）的应用场景
> 纤程 vs 线程池：纤程适合很短的计算任务，不需要和内核打交道，且纤程的并发量高！


### 4. 进程
> 1.进程的基本概念  
> 注意进程描述符：PCB  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/进程的基本概念.png) 
<br/>
> 2.进程的创建和启动  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/进程的创建和启动.png) 
<br/>
>3.僵尸进程&孤儿进程  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/僵尸进程&孤儿进程.png) 
>对于僵尸进程：简单而言，就是当当前进程执行完自己的操作后，会释放相关资源，但是其父进程会保留其PCB的相关信息，pid还会被保留，这个时候这个进程就被称为僵尸进程  
<br/>
>对于孤儿进程：简单而言，就是当前进程的父进程已经死了，那么当前进程就被称为孤儿进程  
<br/>
>通常情况下，僵尸进程不会存在什么问题，但是如果僵尸进程太多，就会出现一种状况，那就是你发现你的内存够，CPU也够，但是就是没办法创建进程了，这个时候就是因为僵尸进程太多，需要完全释放这些僵尸进程才行  
>如何避免僵尸进程：  
>一共有三种方式：  
>（1）让僵尸进程的父进程来回收，父进程每隔一段时间来查询子进程是否结束并回收，调用wait()或者waitpid(),通知内核释放僵尸进程  
>（2）采用信号SIGCHLD通知处理，并在信号处理程序中调用wait函数  
>（3）让僵尸进程变成孤儿进程，由init回收，就是让父亲先死  
<br/>

### 5. 进程（任务）调度
>任务执行多久，怎么执行，是由其对应的调度方案决定的！  
>进程调度策略：  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/进程调度策略.png) 
>即按优先级，分配时间片的比例。记录每个进程的执行时间，如果有一个进程执行时间不到他应该执行的时间，将被优先执行。  
>
#### 5.1 默认调度策略：  
>（1）实时 （急诊） 优先级分高低 - FIFO (First In First Out)，优先级一样 - RR（Round Robin）  
>（2）普通： CFS  
>

### 6. 中断
>基础概念：硬件跟操作系统内核打交道的一种机制，中断是个信号！    
>软中断（80中断） ==  系统调用  
>系统调用：int 0x80 或者 sysenter原语  
>通过ax寄存器填入调用号  
>参数通过bx cx dx si di传入内核  
>返回值通过ax返回  
>java读网络 – jvm read() – c库read() - > 内核空间 -> system_call() （系统调用处理程序）-> sys_read()
>
>
### 7. 从汇编角度理解软中断
> 搭建汇编环境: yum install nasm  
```` C
  ;hello.asm
  ;write(int fd, const void *buffer, size_t nbytes)
  ;fd 文件描述符 file descriptor - linux下一切皆文件
  
  section data
      msg db "Hello", 0xA
      len equ $ - msg
  ​
  section .text
  global _start
  _start:
  ​
      mov edx, len
      mov ecx, msg
      mov ebx, 1 ;文件描述符1 std_out
      mov eax, 4 ;write函数系统调用号 4
      int 0x80
  ​
      mov ebx, 0
      mov eax, 1 ;exit函数系统调用号
      int 0x80
````
> 执行过程：   
>(1)编译：nasm -f elf  hello.asm -o hello.o  
>(2)链接：ld -m elf_i386 -o hello hello.o  
> 可以知道：一个程序的执行过程，要么处于用户态，要么处于内核态  

>
### 8. 内存管理
>对于内存，早期，在DOS时代，同一时间只能有一个进程运行，当然，一些特殊的算法可以支持多进程操作  
>但是大多数还是只能在同一时间只能有一个进行运行，因此就经常会出现A进程在运行时，将B进程的信息给干掉，甚至把操作系统给干掉。  
>随着时代的发展，操作系统实现了将多个进程装入内存中，但是需要解决两个问题：  
>1.内存不够用  
>2.进程间的互相打扰    
><br/>
>为了解决这两个问题,诞生了现在的内存管理系统，总结而言就是：虚拟地址 分页装入 软硬件结合寻址  
><br/>
>下面将对解决这两个问题进行说明：

#### 8.1 解决内存不够用的方法：
>分页：将内存分成固定大小的页框，每个页框大小为默认为4K,把程序在硬盘上也默认分为4K大小的块，用到哪一块，就加载哪一块，这样，就不会每次把整个信息加载至内存，就增大了内存的可用性。  
>当内存已满时，就将最近最不常用的一块信息放入swap分区，再把最新的一块信息加载进来，这就是著名的LRU算法。  
><br/>
>对于LRU算法，下面简单说明，非常重要！  
>（1）英文释意：Last Recently Used 最近最不常用  
>（2）LeetCode 146题（TT,AL都要求手撕）  
>（3）LRU算法实现：
>   哈希表（保证查找信息时间复杂度O(1)）+ 单向链表（保证新增操作和排序操作时间复杂度O(1)，但中间修改操作时间复杂度O(n)）  
>   +双向链表（保证中间节点被修改后，链表的变动时间复杂度O(1)）  
>因此，LRU算法的内部实现就是：哈希表+双向链表（LinkHashMap就是这样的数据结构）  

#### 8.2 解决进程相互不打扰的方法：
> 为了保证进程不相互影响，因此让进程工作在虚拟空间中，程序中用到的空间地址也不再是直接的物理地址，而是虚拟的地址，站在进程的角度看，他拥有所有的内存空间，这样，A进程永远不可能访问到B进程的空间  
>虚拟空间多大呢？寻址空间 - 64位系统 2 ^ 64，比物理空间大很多 ，单位是byte  
>站在虚拟的角度，进程是独享整个系统 + CPU  
>有虚拟内存地址，找到真实的物理地址的过程，就叫做内存映射  
>内存映射：偏移量 + 段的基地址 = 线性地址 （虚拟空间）；线性地址通过 OS + MMU（硬件 Memory Management Unit）
<br/>
>缺页中断（不是很重要）：需要用到页面内存中没有，产生缺页异常（中断），由内核处理并加载

### 9. ZGC
>使用的算法叫做：Colored Pointer  
>GC信息记录在指针上，不是记录在头部， immediate memory use   
>42位指针 寻址空间4T JDK13 -> 16T 目前为止最大16T 2^44  

### 10. CPU如何区分一个立即数 和 一条指令
>总线内部分为：数据总线 地址总线 控制总线  
>从不同的总线过来的信息，就自动区分了，是一个立即数还是一条指令了  
>地址总线目前：48位  
>颜色指针本质上包含了地址映射的概念  
>
### 11. 内核同步机制
>关于同步理论的一些基本概念  
>•临界区（critical area）: 访问或操作共享数据的代码段,简单理解：synchronized大括号中部分（原子性）  
>•竞争条件（race conditions）两个线程同时拥有临界区的执行权  
>•数据不一致：data unconsistency 由竞争条件引起的数据破坏  
>•同步（synchronization）避免race conditions   
>•锁：完成同步的手段（门锁，门后是临界区，只允许一个线程存在）上锁解锁必须具备原子性  
>•原子性（象原子一样不可分割的操作）  
>•有序性（禁止指令重排）  
>•可见性（一个线程内的修改，另一个线程可见）  
>互斥锁 排他锁 共享锁 分段锁
 
>内核同步常用方法  
>1.原子操作 – 内核中类似于AtomicXXX，位于<linux/types.h>    
>2.自旋锁 – 内核中通过汇编支持的cas，位于<asm/spinlock.h>  
>3.读-写自旋 – 类似于ReadWriteLock，可同时读，只能一个写读的时候是共享锁，写的时候是排他锁  
>4.信号量 – 类似于Semaphore(PV操作 down up操作 占有和释放）重量级锁，线程会进入wait，适合长时间持有的锁情况     
>5.读-写信号量 – downread upread downwrite upwrite（多个写，可以分段写，比较少用）(分段锁）  
>6.互斥体(mutex) – 特殊的信号量（二值信号量）  
>7.完成变量 – 特殊的信号量（A发出信号给B，B等待在完成变量上）vfork() 在子进程结束时通过完成变量叫醒父进程 类似于(Latch)  
>8.BKL：大内核锁（早期，现在已经不用）  
>9.顺序锁（2.6）： – 线程可以挂起的读写自旋锁序列计数器（从0开始，写时增加(+1)，写完释放(+1)，读前发现单数，说明有写线程，等待，读前读后序列一样，说明没有写线程打断）  
>10.禁止抢占 – preempt_disable()  
>11.内存屏障 – 见volatile



