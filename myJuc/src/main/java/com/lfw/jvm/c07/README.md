## JVM调优实战（一）
### 1、常见垃圾回收器组合参数设定
* -XX:+UseSerialGC = Serial New (DefNew) + Serial Old
  * 小型程序。默认情况下不会是这种选项，HotSpot会根据计算及配置和JDK版本自动选择收集器
* -XX:+UseParNewGC = ParNew + SerialOld
  * 这个组合已经很少用（在某些版本中已经废弃）
  * https://stackoverflow.com/questions/34962257/why-remove-support-for-parnewserialold-anddefnewcms-in-the-future
* -XX:+UseConc<font color=red>(urrent)</font>MarkSweepGC = ParNew + CMS + Serial Old
* -XX:+UseParallelGC = Parallel Scavenge + Parallel Old (1.8默认) 【PS + SerialOld】
* -XX:+UseParallelOldGC = Parallel Scavenge + Parallel Old
* -XX:+UseG1GC = G1
* Linux中没找到默认GC的查看方法，而windows中会打印UseParallelGC 
  * java +XX:+PrintCommandLineFlags -version
  * 通过GC的日志来分辨
 

### 2、JVM调优第一步，了解JVM常用命令行参数
      
* JVM的命令行参数参考：https://docs.oracle.com/javase/8/docs/technotes/tools/unix/java.html

* HotSpot参数分类

> 标准： - 开头，所有的HotSpot都支持
>
> 非标准：-X 开头，特定版本HotSpot支持特定命令
>
> 不稳定：-XX 开头，下个版本可能取消

java -version

java -X

### 3、区分几个概念
1. 区分概念：内存泄漏memory leak，内存溢出out of memory
内存泄漏，在内存空间足够大的情况下，是不会产生内存溢出的。

试验用程序：

  ```java
  import java.util.List;
  import java.util.LinkedList;
  
  public class HelloGC {
    public static void main(String[] args) {
      System.out.println("HelloGC!");
      List list = new LinkedList();
      for(;;) {
        byte[] b = new byte[1024*1024];
        list.add(b);
      }
    }
  }
  ```

2. java -XX:+PrintCommandLineFlags HelloGC

3. java -Xmn10M -Xms40M -Xmx60M -XX:+PrintCommandLineFlags -XX:+PrintGC  HelloGC
PrintGCDetails PrintGCTimeStamps PrintGCCause

4. java -XX:+UseConcMarkSweepGC -XX:+PrintCommandLineFlags HelloGC

5. java -XX:+PrintFlagsInitial 默认参数值

6. java -XX:+PrintFlagsFinal 最终参数值

7. java -XX:+PrintFlagsFinal | grep xxx 找到对应的参数

8. java -XX:+PrintFlagsFinal -version |grep GC

### 4、### PS GC日志详解
      
>每种垃圾回收器的日志格式是不同的！但是基本内容都大同小异，下面看PS日志格式：
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/114-GC-log.png)

>heap dump部分：
```java
eden space 5632K, 94% used [0x00000000ff980000,0x00000000ffeb3e28,0x00000000fff00000)
后面的内存地址指的是，起始地址，使用空间结束地址，整体空间结束地址
```
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/114-Head-log.png)
>reserved 表示虚拟内存的大小  
>committed 表示先占用虚拟内存的大小  
>capacity 表示整个MetaSpace的虚拟内存的大小  
>used 表示已经被使用的虚拟内存的大小  
>注：total = eden + 1个survivor
      
### 5、调优前的基础概念：

* 1. 吞吐量：用户代码时间 /（用户代码执行时间 + 垃圾回收时间）

* 2. 响应时间：STW越短，响应时间越好
      
** 所谓调优，首先确定，追求啥？吞吐量优先，还是响应时间优先？还是在满足一定的响应时间的情况下，要求达到多大的吞吐量...

* 3.针对不同业务需求的系统，选择不同的垃圾回收器组合：
** (1)科学计算，吞吐量。数据挖掘，thrput。吞吐量优先的一般：（PS + PO）

** (2)响应时间：网站 GUI API （1.8 G1）
      
### 6、什么是调优？

1. 根据需求进行JVM规划和预调优
2. 优化运行JVM运行环境（慢，卡顿）
3. 解决JVM运行过程中出现的各种问题(但不等同于OOM)

### 7、调优，从规划开始

* 调优，从业务场景开始，没有业务场景的调优都是耍流氓

* 无监控（压力测试，能看到结果），不调优

* 步骤：
1. 熟悉业务场景（没有最好的垃圾回收器，只有最合适的垃圾回收器）
   1. 响应时间、停顿时间 [CMS G1 ZGC] （需要给用户作响应）
   2. 吞吐量 = 用户时间 /( 用户时间 + GC时间) [PS]
2. 选择回收器组合
3. 计算内存需求（经验值 1.5G 16G）
4. 选定CPU（越高越好）
5. 设定年代大小、升级年龄
6. 设定日志参数
   1. -Xloggc:/opt/xxx/logs/xxx-xxx-gc-%t.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=20M -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCCause
   2. 或者每天产生一个日志文件
7. 观察日志情况

* 案例1：垂直电商，最高每日百万订单，处理订单系统需要什么样的服务器配置？

> 这个问题比较业余，因为很多不同的服务器配置都能支撑(1.5G 16G)
>
> 1小时360000集中时间段， 100个订单/秒，（找一小时内的高峰期，1000订单/秒）
>
> 经验值，
>
> 非要计算：一个订单产生需要多少内存？512K * 1000 500M内存
>
> 专业一点儿问法：要求响应时间100ms
>
> 压测！

* 案例2：12306遭遇春节大规模抢票应该如何支撑？

> 12306应该是中国并发量最大的秒杀网站：
>
> 号称并发量100W最高
>
> CDN -> LVS -> NGINX -> 业务系统 -> 每台机器1W并发（10K问题） 100台机器
>
> 普通电商订单 -> 下单 ->订单系统（IO）减库存 ->等待用户付款
>
> 12306的一种可能的模型： 下单 -> 减库存 和 订单(redis kafka) 同时异步进行 ->等付款
>
> 减库存最后还会把压力压到一台服务器
>
> 可以做分布式本地库存 + 单独服务器做库存均衡
>
> 大流量的处理方法：分而治之

* 怎么得到一个事务会消耗多少内存？

> 1. 弄台机器，看能承受多少TPS？是不是达到目标？扩容或调优，让它达到
>
> 2. 用压测来确定

### 优化环境

1. 有一个50万PV的资料类网站（从磁盘提取文档到内存）原服务器32位，1.5G
 的堆，用户反馈网站比较缓慢，因此公司决定升级，新的服务器为64位，16G
 的堆内存，结果用户反馈卡顿十分严重，反而比以前效率更低了
 1. 为什么原网站慢?
    很多用户浏览数据，很多数据load到内存，内存不足，频繁GC，STW长，响应时间变慢
 2. 为什么会更卡顿？
    内存越大，FGC时间越长
 3. 咋办？
    PS -> PN + CMS 或者 G1
 
2. 系统CPU经常100%，如何调优？(面试高频)
 CPU100%那么一定有线程在占用系统资源，
 1. 找出哪个进程cpu高（top）
 2. 该进程中的哪个线程cpu高（top -Hp）
 3. 导出该线程的堆栈 (jstack)
 4. 查找哪个方法（栈帧）消耗时间 (jstack)
 5. 工作线程占比高 | 垃圾回收线程占比高

3. 系统内存飙高，如何查找问题？（面试高频）
 1. 导出堆内存 (jmap)
 2. 分析 (jhat jvisualvm mat jprofiler ... )
 
4. 如何监控JVM
 1. jstat jvisualvm jprofiler arthas top...