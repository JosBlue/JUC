### 容器
> 容器中的基本划分：
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/容器.png)
>容器下主要分为两大类接口：  
>（1）一个一个元素存储的容器：Collection(它的 下面分为三大类)    
> * 无序且不可重复的列表：Set  
> * 有序且可重复的列表：List  
> * 队列：Queue(专门为高并发准备的)    
>（2）一对一对元素存储的容器：Map  
### 1、从物理上划分，存储数据的容器只有两种结构：
> (1)连续存储的：数组  
> (2)不连续存储的：链表  
>
#### 2、Map
>1、容器的演变  
>2、map中几种容器的对比  
>3、几种map运行效率对比  
>
#### 3、HashTable,HashMap,synchronizedMap,ConcurrentHashMap对比
> HashTable 为线程安全的类，底层的方法用synchronized修饰，新增及查询等效率，在数据量较大时，较低  
>HashMap 为线程不安全的类，并发量大时，存在线程不安全的情况，但新增及查询效率较高  
>synchronizedMap 为线程安全的类，可以看作是对HashMap的线程安全的升级  
>ConcurrentHashMap 线程安全类，查询效率较高，但是新增或删除效率低，底层是通过cas实现的  
>另：
>HashMap允许传入空值的key及value，Hashtable则是不允许的
 HashMap的线程不安全的，Hashtable是线程安全的
 HashMap的默认初始值大小为16，Hashtable的默认初始值大小为11
 Hashtable将默认初始值设置为11，是因为它更加关注值的均匀分布，以减少hash冲突，11是一个素数，简单取模哈希的结果会更加均匀，
 之前也有一本书里面写过这么一句话，大致意思就是素数取余会更加均匀
 HashMap将默认值初始值设置为16，2的N次幂，这么做是因为HashMap可以提升hash计算的速度，其中进行位运算，位运算相较于直接
 取模会更快  
 同时，在HashMap添加数据，扩容的时候，2的N次幂，更加有利于扩容时，新的key值计算，比如我们从16扩容至32，那么就是首位加1，
 这个时候计算元素的新位置时，就直接元素原位置+16就可以得出新的位置，这样可以大大提升效率。当然，这种改动，也会增大hash冲突
 的概率，因此又对取值时的计算进行了一些改动。
 所以总而言之，就是他们的侧重点不太一样，Hashtable更加关注数据的均匀分布，HashMap则更加侧重于计算效率。  
>
#### HashMap的线程不安全原因，及改进方式  
> HashMap在高并发的场景下，可能会出现环形链表的情况  ？？？？
##### HashMap线程不安全的改进方式：
> 1.使用HashTable或Collections.synchronizedMap可以保证线程安全，但是由于这两个容器，无论是读还是写操作，都是对整个容器  
>进行加锁，这样会导致同一时间其他线程被阻塞，因此效率比较低  
>2.使用ConcurrentHashMap  
>JDK1.7中，使用了Segment的方式，实现分段锁，即锁细化，来降低锁冲突的可能    
>JDK1.8中，使用更加快捷的cas+synchronized的方式来实现的  
>为进一步提升性能，ConcurrentHashMap引入了红黑树。  
 引入红黑树是因为链表查询的时间复杂度为O(n)，红黑树查询的时间复杂度为O(log(n))，所以在结点比较多的情况下使用红黑树可以大大提升性能。  
>
#### 4、队列(Queue)
> ConcurrentQueue  
