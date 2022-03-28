类加载器：

Bootstrap类加载器，加载咱们的rt包，但是在源码实现上不存在的，所以是空，只是告诉咱们的coder存在这么一个加载器

Extension类加载器，加载扩展包中的信息，有三层继承关系，父类为UrlClassLoader,UrlClassLoader继承至SecureClassLoader，SecureClassLoader继承至ClassLoader抽象类，
ClassLoader是一个抽象类，也是这些类加载器的父类，定义了一堆加载的模板，采用了模板方法模式，核心类方法在于loadClass，另一个是defineClass，
这两个方法是ClassLoader自己的，再往下赋给子类进行复写的，是findClass

Application类加载器，这个是我们通常用于加载类的加载器

自定义类加载器，用户自己的定义的类加载器

有了这些类加载器之后，我们就可以通过这些类加载机制对我们定义的类进行加载了。
加载后，需要对这个类加载的信息进行一系列的验证，准备和解析，做完这一系列工作之后，就解析成运行时常量池，叫constant pool，然后这个时候，
就把这些信息放到方法区,或者是在JDK1.8之后的，被称为MetaSpace元数据区中，然后就对其进行初始化。

到这里，会有一个常问的面试题：
写出一个：
static{
 final String XX
}
问，我另外一个类点这个，这个会不会被初始化？
这是不会的！因为它在编译的时候，就已经被扔到常量池里面去了，所以不需要初始化

如果再写 static int a = 1;这个存不存在呢？
是存在的！写是有顺序的



为什么要划分新生代和老年代
类比两个孩子住在一起
两代的垃圾回收算法不一样，对两边分别进行治理

为什么在JDK1.8的时候要把permGan干掉，开辟为metaspace？
因为在最初的时候，将堆开辟了一块空间，叫permGen，但这块空间具体应该开辟为多大呢，开辟大了，会造成浪费，开辟小了，又可能会造成oom
那么我们干脆就将这块空间给分出去，通过max metaspace控制大小 防止代码有问题，把内存全部占完


产生Full GC
分清是full gc还是yong gc
dump下当时的堆内存文件，使用jmap -histo命令等方式，查看这个堆内存信息，也可以用mat工具，来查找大对象，查找对应的引用，再顺着引用找到
对应的线程，最终定位到具体的方法，排查问题


G1中的并行与并发
并行：是指并行处理，垃圾回收
并发：是指可以和应用线程交替执行


ArrayBlockingQueue与LinkBlockingQueue的区别：
他们的区别并不是ArrayBlockingQueue是一个有界的阻塞队列，而LinkBlockingQueue则是一个无界的阻塞队列，不是这个
而是他们的内部锁个数不同
ArrayBlockingQueue内部是一把锁 

LinkBlockingQueue内部是两把锁，take锁和put锁
那么锁的增加，带来的就是锁粒度的减小，带来的效果就是并发上去了

那么这两个怎么选择呢？
实际想问的，就是缓存行，因为ArrayBlockingQueue就涉及到缓存行
ArrayBlockingQueue 地址是连续的，那么就可以加载到cpu的缓存行中
LinkBlockingQueue  地址是不连续的，不可以直接加载到cpu的缓存行中


PriorityBlockingQueue 支持优先级排序的无界阻塞队列，那么排序的基础是什么？
需要两个：
一个数据结构，小顶堆或大顶堆
第二个，实现comparator接口，或者传入comparator

SynchronousQueue:容量为0的队列

提前避免内存泄漏？
 



