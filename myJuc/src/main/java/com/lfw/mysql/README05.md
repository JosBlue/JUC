一、索引监控
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/索引监控信息.png)


二、查询优化
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/查询优化.png)


（1）查询慢的原因  
查询慢的原因有很多，大概会是这些原因造成的
1、网络  
网络IO
2、CPU  
数据运行需要CPU时间片的计算
3、IO  
4、上下切换  
5、系统调用  
6、生成统计信息  
7、锁等待时间  
说到锁，这里简单对锁做个总结
锁是与底层的存储引擎相关联的，不同的存储引擎，所能支持的锁也是不一样的
mysql中常用的两个存储引擎，MyIsam与Innodb
在MyISAm中，锁分为两种：共享读锁与独占写锁，MyISam仅支持表锁
在Innodb中，锁也分为两种：共享锁与排他锁，Innodb中，支持表锁和行锁，InnoDB锁的对象是索引，如果是索引列被加锁，那么就是行锁，否者就是表锁
锁，还有间隙锁、自增锁，这些后面都会说到，可以先了解

（2）优化数据访问  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/优化数据访问.png)

在高性能mysql这本书中写到，如果查询的数据量整体数据量的超过30%时，就可能不会用索引（这个待验证，可以了解）


（3）执行过程的优化  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/执行过程的优化.png)
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/查询优化器.png)
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/优化详情01.png)
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/统计信息不准确.png)
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/预估值不等于实际值.png)
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/基于成本优化.png)
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/不考虑不受控的成本.png)

>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/优化器的优化策略.png)

（4）优化特定类型的查询  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/优化器的优化类型.png) 

（5）关联查询
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/关联查询.png) 

小表Join大表的说法，不一定正确，优化器会进行sql优化，所以你所认为的小表JOIN大表，并不一定就是这样的顺序


三、排序优化
排序算法：
（1）两次传输排序
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/两次传输排序.png) 

（2）单次传序排序
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/单次排序.png) 

两种排序算法的区别就是，两次排序会先将需要排序的字段读取出来，排好序后，再进行第二次数据的读取，读取实际需要的那些字段，此时的读取就是随机IO了，因为是按照排好序的数据方式去读取的。
这种好处就是排序缓冲区可以容纳更多的信息
单次排序，就是一次性将所有需要的信息列都读出来，然后再进行排序，占用空间大，但是只读取一次数据，并且是顺序IO，效率上会更高
当需要排序的列的总大小加上order by 的列的大小超过max_length_sort_data定义的字节（默认1024字节），mysql会选择双次排序，反之使用单次排序，当然，用户
可以选择设置此参数值来选择排序方式



四、优化特定类型的查询
（1）count(1) ,count(*),count(id)在查询效率上没有任何区别，写1只是因为简单
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/优化count查询.png) 
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/count更加复杂的优化.png) 


（2）优化关联查询
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/优化关联查询.png) 

（3）优化子查询
尽可能使用关联查询来代替子查询，因为子查询会很创建临时表，涉及到IO，效率较低

（4）优化group by和distinct
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/优化groupbydistinct.png)

标识列：主键列

（5）limit
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/limit语句优化.png)

下面的查询语句比上面的查询语句相对快一点

（6）union 与 union all
union 会做去重的操作
union all 则不会
去重的操作是十分损耗性能的，因此除非业务需要，否则尽量使用union all
 
 
 五、推荐使用用户自定义变量
 （1）自定义变量的使用及限制
 >![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/自定义变量的使用及限制.png)

（2）自定义变量的使用案例
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/自定义变量的使用案例.png)

自定义变量的优先级很低，因此如果要按照预想的执行顺序执行，记得添加括号

附：时间类型：timestamp 
我们知道，这个时间类型只到2038年，为什么呢？
是因为timestamp的底层使用的占4个字节的int类型存储的，int类型的范围是正负21亿，时间戳换算过来，刚好是1970-2038年这样的一个时间范围

 






















