索引
一、为什么索引存储选择B+Tree，而不是B-Tree？

一个索引就是一棵树（一般树的层级是三层，但有时会达到四层）

orcal会有一个rowID,如果mysql不指定主键，也没有唯一键，就会隐性生成一个rowID



二、索引基础
（1）索引的优点
1）大大减少服务器需要扫描的数据量，提升查询效率
2）帮助服务器避免排序和临时表
使用order by就代表了全排序，使用索引，索引本身就已经排好序了，就可以避免全排序这种效率比较低下的排序方式
3）将随机Io变成顺序IO，随机IO的慢就出在他的随机性上

（2）索引的用处
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/索引的用处.png)


（3）索引的分类
1）主键索引
2）唯一索引

问题：数据库会主动帮助我们建索引吗？
数据库会主动建索引，会给唯一且非空的列建索引，不能直接说是会建主键索引


3）普通索引
普通列的索引就是普通索引

4）全文索引
一般用于VARCHAR,CHAR,TEXT等大字符串文件中，很少使用


5）组合索引
多个字段组合在一起，创建一个索引，就叫组合索引

在此处，会涉及到一些比较专业的名词：
1）回表
在我们的普通索引中，叶子节点上存储的数据不是完整的数据信息，而是我们数据的主键，当我们通过普通索引获取到对应叶子节点上的数据后，拿到的就是主键，
我们再到主键对应的索引树中查找对应主键数据，从而取出整行的数据信息，这个操作就叫做回表。 

2）覆盖索引
当我们的通过普通索引查询数据时，原本需要根据查询的数据，通过回表的方式，才能获取到完整的数据行，但是如果我们直接将主键id查询出来，避免了回表的操作，
那么这个就叫做覆盖索引。 
不能查询出其他不包含索引的列，否则不能覆盖索引
这个在组合索引中还是使用比较广泛的，能用覆盖索引，就尽量用覆盖索引


3）最左匹配
查询的列与索引的顺序保持一致，才能使索引生效
索引也会占用磁盘空间，占用空间越小，IO时间更短，效率更高


4）索引下推
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/索引下推.png)

数据返回至server层时，在存储引擎层就已经帮助做了查询列的过滤，只留下了索引列。 
如果数据的过滤是在存储引擎层就处理了，那么就是索引下推，如果是在server做的过滤，则不是
索引下推在非主键索引上的优化，可以有效减少回表的次数，大大提升了查询的效率。
5.7及以上的版本会自动帮助做索引下推的优化，5.7以下则不会




其他概念：索引合并，页分裂，页合并

注意：有索引，不代表一定会使用索引，要使用索引，会根据其对应的规则才可以使用索引

（4）索引采用的数据结构
1)哈希表（memory这种存储引擎使用的底层数据结构）
2)B+树（Innodb 和myIsam使用的数据结构 ）


（5）索引的匹配方式
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/索引匹配方式.png)

dev.mysql.com/doc/sakila/en/
 

精准匹配某一列并范围匹配另外一列
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/索引匹配方式.png)

在这两个sql语句中，可以看到上下的执行计划中是有差异的
上面的一列是走了索引，先精准匹配了name这一列，然后范围匹配了age这一列，最终的type为rang
下面的查询语句，则是ref，精准匹配了name列，但是ops这一列并没有进行索引匹配，我们建的组合索引是name+age+ops，没有age，那么ops就不会被匹配到,最终的type是ref
sql优化，会重新组合查询条件的顺序




三、哈希索引
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/哈希索引.png)

避免哈希冲突的方法？
编写优秀的hash算法
不要直接通过对数组的长度进行取模的方式计算hash值
扰动函数（让数据hash值的高位参与运算，而不让低位参与运算，因为大多数数值的低位是一样的，参与运算，就容易产生hash冲突）

>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/哈希索引案例.png)

当需要存储大量的URL，并根据URL进行搜索查找时，如果使用B+树，存储的内容就会很大
select id from table where url = ''
可以使用CRC32(循环冗余校验)做哈希，可以使用以下的方式进行查询：
select id from table where url = '' and url_crc = CRC32('')
查询性能比较高，是因为使用了体积很小的索引来完成了查找

为什么要使用hash索引？
当我们需要使用索引时，会占用很多的存储空间时，就可以考虑使用hash索引 


四、组合索引
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/组合索引.png)
>>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/组合索引案例.png)

倒数第二个，b使用了范围查找，后面的所有索引列不会使用索引
最后一个不会使用b索引

五、聚簇索引与非聚簇索引
聚簇索引：数据与索引在一起
非聚簇索引：数据与索引分开存储

>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/聚簇索引的优缺点.png)

插入速度严重依赖于插入顺序，按照主键的顺序插入是最快的方式
涉及到页分裂和页合并的过程（频繁的页分裂和页合并会消耗大量的IO，影响查询效率）

附：如果有大量数据需要迁移至新的mysql中，可以将自动创建索引的开关关闭，在迁移完成数据后，在将该开关打开，这样可以提升效率
因为如果在迁移数据的过程中，打开此开关，那么就会边迁移数据，边创建索引及更新索引，这个效率是极低的
我们关闭自动创建索引，迁移完数据后，再打开开关，mysql也会自动帮助创建相关的索引


六、覆盖索引
注意：覆盖索引并不是一种索引类型，而是一种现象
索引类型只有5种：主键索引、唯一索引、组合索引、普通索引以及全文索引
当发起一个被覆盖索引覆盖的查询时，在explain中的extra列可以看到using index的信息，此时就使用了覆盖索引。 
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/覆盖索引基本介绍.png)
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/覆盖索引优势.png)

IO密集型：需要频繁进行IO，即为IO密集型，数据库通常为IO密集型

二级索引？？？


七、优化小细节
（1）当使用索引列进行数据查找时，尽量不要使用表达式，把计算放到业务层而不是数据层
例如：
select aid from a where aid + 1 =5；type为index
select aid from a where aid = 4; type 为const
下面的效率远远高于上面的效率

（2）尽量使用主键索引进行查询，避免回表操作

（3）使用前缀索引
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/前缀索引实例说明.png)

截取字符串前某一段作为前缀索引
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/前缀索引判断依据.png)

添加方式：
alter table tablename add key(字段(7))

注意：前缀索引是一种能使索引更小更快的有效方法，但是也包含缺点，mysql无法使用前缀索引做order by 和group by

附：Cardinality（基数）
通过show index from tableName；
可以看到对应索引列中的Cardinality
Cardinality为表中，某一个单列中，数据的唯一值大概有多少个
对于OLAP系统（需要对大量数据进行数据分析，以做出相关决策，这种系统就叫做OLAP系统）是非常重要的，可以在多个表进行关联时，选择基数值小的那个字段进行关联
基数值越小，对于数据关联时，效率越好


（4）使用索引扫描来排序
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/使用索引扫描来排序.png)

排序，通常使用order by，效率较低
索引本身就是有序的
总结为一句话：如果你需要排序时，where查询语句与order by 中的字段可以组成组合索引，那么就可以使用索引排序
但是，如果你的查询条件是一个范围查找，那么就不能使用组合索引
如果，order by中的顺序与索引中的升降序不一致，则也不能使用
 

（5）union all、in、or都能够使用索引，但是推荐使用in
查看执行计划：
union all 会分为两个步骤执行
如果非要使用union,推荐使用union all ,而不是使用union，因为union存在去重的操作，比较消耗性能  
因此如果不需要去重，就尽量使用union all  

or与in在执行计划上看不出区别
但是，在数据量较大时，in的效率是远远高于or的
因为使用or相当于是两个查询条件
in则表示在固定的这个范围内查找

另：exists
这个表示是否存在，通常会与子查询联合使用
exists对全量数据做循环查询，判断子查询是否满足条件，满足就直接返回
where查询语句中，and的优先级要高于or


（6）范围列可以使用索引
范围的条件是：< = > <= >= between
范围列可以使用索引，但是范围列后面的列无法使用索引，索引最多用于一个范围列


（7）强制类型转换会导致全表扫描
原本是字符串类型的字段，使用整型进行查询，虽然不会报错，但是会影响查询效率，因为强制类型转换会导致全表扫描
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/强制类型转换影响效率.png)


（8）更新十分频繁，数据区分度不高的字段上不宜建索引
频繁变更数据，变更数据会变更B+树，更新频繁的字段建索引会大大降低数据库的性能，因为频繁的变更数据，意味着索引信息也需要跟着进行变更，
索引的频繁变更，就可能发生页分裂和页合并，占用大量的IO,从而影响数据库的性能
类似于性别这类区分度不大的属性，建立索引没有实际的意义，因为对数据不能进行有效的过滤
一般区分度在你80%以上，就可以建立索引，区分度可以使用count(distinct(列名)/count(*))来计算 


（9）创建索引的列，不允许为null，否则可能会得到不符合预期的结果
以实际业务需求出发

（10）当需要进行表连接时，最好不要超过3张表，因为需要join的字段，数据类型必须一致
 join的原理：嵌套循环算法
 join的方式有三种：
 （1）最简单的，就是A join B，将A表中的数据，一行一行遍历出来，与B表中的每一行数据进行比对，取出满足条件的信息，这种方式的效率比较低
 >![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/simple-nested-loop.png)
 
 （2）第二种，基于索引进行join，根据索引列进行join，如果是主键，由于不需要回表，因此效率非常快，其他比如普通索引，需要回表，效率相对较低
 >![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/Index-nested-loop.png)
 
 （3）第三种，在没有索引的情况下，基于join buffer进行操作，将A表中需要查询的字段提出到join  buffer，在内存中进行数据对比，避免频繁查询A表，以此来提升效率
 >![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/Block-nested-loop.png)

不存在索引时，使用第三种join方式，join buffer的大小有限，join表过多，数据量过大，又没有索引的情况下，会变成第一种join方式，导致查询速度变慢
 

（11）如果明确知道只有一条值返回，加上limit 1 可以提高查询效率，即能使用limit时，尽量使用limit

注意区分一个概念，limit是用于限制输出的，分页只是它的一种应用而已
limit 1 找到第一行数据后，不会再做指针下找判断的操作
如果没有limit 1,则会继续往后续找，然后返回符合条件的信息


（12）单表索引建议控制在5个以内
建的索引越多，磁盘空间占用越大，B+树越大，IO量越大

字段name 定义为varchar(10)，即使为null,也会占用10个空间 

（13）单索引字段数不允许超过5个
最左匹配原则，组合索引对应包含的字段越多，越靠后的字段，越难匹配，反而容易造成存储空间的浪费


（14）创建索引的时候，避免以下错误概念
（1）索引越多越好
（2）过早优化，在不了解x系统的情况下进行优化

























 