### 一、JVM入门
#### 1、java从编码到执行过程
>>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/java从编码到执行过程.png)
>javac编译过程  
>>>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/javac编译过程.png)
>图解：  
>.java文件，通过javac命令，编译成.class文件，然后执行java命令，会被load到内存,这叫classLoader，在java编码中，一般还会用到java的类库，    
>因此，也会讲java的类库装载到内存里，都装载好后，调用字节码解释器或者JIT即时编译器来进行解释或者编译，解释或编译完成后，再交由执行引擎进行执行，  
>执行引擎下面面对的即时OS硬件了。  
>注：有一些执行次数比较多的特定编码，会被JIT即时编译  

#### 2、JVM特点      
>（1）JVM是一种规范  
>（2）虚构出来的一台计算机  
> * 它有自己的指令集（汇编语言）  
> * 它有自己的内存管理：栈、堆、方法区等  
>>(3) JVM与java语言无关  
>JVM 只有8个指令才是原子性的？？？  

####  3、JDK JRE JVM的关系
>>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/JDK&JRE&JVM关系.png) 
>JVM：只负责执行  
>JRE：JRE是运行是环境，他是JVM+core lib(核心类库)  
>JDK：JRE+dev kit,也就是JRE加上开发使用的环境，工具等等  
>

### 二、Class File Format
#### 1、.java编译为.class文件后，包含的信息
> .java编译为.class文件后，其中包含很多信息，前几位分别为：  
>(1) magic  
>(2) minor version  
>(3) constant_pool_count (version 11 总长度为16位，但是常量只有15，因为是从1开始的)   
>(4) constant_pool[constant_pool_count - 1]  
>等等...