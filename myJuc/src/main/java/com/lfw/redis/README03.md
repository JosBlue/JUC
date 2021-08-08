一、Set
注意：
list是有序的，可重复，但它的有序是指插入和取出时候顺序的
set是无序的，不可重复，插入和取出的时候是无序

命令：
<1> 添加：sadd key value
如：sadd k1 aa bb cc 

<2>查看key下所有信息:smembers key
如：smembers k1

<3> 移除key中的元素：srem key
如：srem k1 aa

set最重要的作用，就是可以取交集，并集，差集（集合操作在set中使用的是比较多的）
<4> 取交集
取交集并直接输出： sinter  key1 key2 ...
取交集并赋值给另一个key: sinterstore destination key1 key2 ...
注意：这个地方也是redis作者做的更加细腻的地方，如果你取出交集后不需要做其他更多的操作，那么你直接使用sinter命令输出结果，这样相对于
sinterstore这种先取出值赋值给另一个key,然后再从这个key中取出的这种方式，要少一次IO的操作，交互上更加快捷


<5> 取并集
取并集并直接输出： sunion  key1 key2 ...
取并集并赋值给另一个key: sunionstore destination key1 key2 ...

<5> 取差集
取差集并直接输出： sdiff  key1 key2 ...
注意，这里没有方向的概念，如果你将k1放在前面，那么输出的就是k1相对于k2的差集，如果你将k2放在前面，那么输出的就是k2相对于k1的差集


<6>srangmember key count:详细见图
实际模拟其使用场景
考虑：人数小于礼物数的时候，怎么选择count的正负

<7>SPOP：SPOP key
取出一个，并将这个从key的member中移除 


二、SortedSet
有序的set
其中存在索引，分数

操作：
<1>添加：zadd key score member
如：zadd k1 8 aa 4 bb 6 cc
注意：添加元素，默认在物理内存存储的顺序为按照分数左小右大存储，且不随你使用的命令而变化

<2>查看元素信息：（按照索引取）zrange key start stop
如：zrange k1 0 -1(查看所有元素信息)

还可以将元素的对应分值带出来:
zrange k1 0 -1 withscores 

<3> 查看元素信息：（按照分值取）zrangebyscore key start stop(也需要索引位置)

需求练习：
如：按照价格由低到高取出前两名：zrange k1  0 1
如：按照价格由高到低取出后两名：zrevrange k1  0 1;或者直接zrange k1 -2 -1

<4>通过元素取出其对应分值：zscore key value
如：zscore k1 aa

<5>通过元素取出其对应排名：zrange key value
如：zrange k1 aa

<6>sortedset也支持元素的数值的增减：
如:给bb的score增加2：zincrby k1  2 bb(语法：zincrby key value member)
并且，这些元素的数值变更后，其在物理内存中的排序也会根据其对应的数值变化，产生相应的变化

既然sortedset是一个集合，那么它也支持集合操作

<6>取并集：zunionstore destination numkeys key[key...] [WEIGHTS weight] [AGGREGATE SUM|MIN|MAX] 
取多个key的并集
destination：输出的目标key
numkeys：一共有几个key
key[key...]:对应key信息
[WEIGHTS weight]：每个key所占权重，1=100%
[AGGREGATE SUM|MIN|MAX]：将这些key对应的值取加和/最小值/最大值

如：
添加两个key:
zadd k1 tom 60 cat 80 array 30
zdd k2 tom 80 cat 90  zion 90

我们取两个key的并集：
zunionstore unkey 2 k1 k2
后面不加任何参数，表示权重均为1，并且默认做sum操作
最终取出带有分数的结果：
zrange 0 -1 with scores: tom 140 cat 170 array 30 zion 90 

添加权重信息：
zunionstore unkey1 2 k1 k2 weights 1 0.5
添加权重1 0.5表示，k1中的值分数乘以1 ，k2中的分数乘以0.5
zrange 0 -1 with scores: tom 100 cat 125 array 30 zion 45

其他的聚合指令max,min自行实验，注意需要加上 AGGREGATE

<7>两个十分重要的问题： 
sortedset排序是怎么实现的？
增删改查的速度怎么样？
底层是通过：skipList（跳表）实现的
跳表的特点：
（1）随机造层：因为需要分层，但是是随机的
（2）类平衡树：跳表不是一个平衡树，而类似平衡树
关于增删改查的速度：针对于并发较多，增加改查都综合来看比较多的情况，跳表的性能相对是最稳定的，最平均的 
 