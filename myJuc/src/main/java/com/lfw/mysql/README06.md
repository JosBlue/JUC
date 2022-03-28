mysql 分区表
分区表的核心思想：将数据分而治之
此处顺便提一下分库分表
分库分表有两种方式：垂直切分与水平切分
垂直切分：将数据按照一定的规则或业务场景进行数据切分，然后存储到不同的服务器中
水平切分：将数据按照0-10000，10000-20000这种方式进行数据切分存储
此处的分区表就类似与水平切分

分区的目的，就是减少整体的IO量，从而提高查询效率

一、分区表的应用场景
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/分区表的应用场景.png)


二、分区表的限制
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/分区表的限制.png)


三、分区类型
（1）范围分区
创建方式：
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/范围分区创建语句.png)
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/创建分区表后的文件信息.png)

根据storeId的值进行插入，查询时，mysql也会自行判断数据是在哪个分区上
如果新增的值，超过的分区的值怎么办？最后一个分区可以写为：
PARTITION p3 VALUES LESS THAN MAXVALUE
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/范围分区超过最大值时.png)

范围分区根据时间分区
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/范围分区根据时间分区.png)

当我们创建的分区，需要修改时，也是可以修改的
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/分区表的维护.png)


（2）列表分区
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/列表分区创建语句.png)
 
（3）列分区
为将范围分区和列分区的整合变种


（4）Hash分区
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/HASH分区.png)

只创建4个分区，根据storeId进行hash运算
hash分区，分区规则就是对数值进行简单的取模运算，hash规则越复杂，计算的代价越高

hash分区，也可以使用年进行hash分区
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/HASH分区年.png)


（5）Key分区
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/key分区.png)

（6）子分区
在分区的基础之上，再建分区
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/子分区.png)

>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/子分区后对应生成的文件信息.png)



三、分区表的原理
分区表由多个相关的底层表实现，这个底层表也是由句柄对象标识，我们可以直接访问各个分区。
存储引擎管理分区的各个底层表和管理普通表一样（所有的底层表都必须使用相同的存储引擎），分区表的索引知识在各个底层表上各自加上一个完全相同的索引。
从存储引擎的角度来看，底层表和普通表没有任何不同，存储引擎也无须知道这是一个普通表还是一个分区表的一部分。

分区表的操作按照以下的操作逻辑进行：

**select查询**

当查询一个分区表的时候，分区层先打开并锁住所有的底层表，优化器先判断是否可以过滤部分分区，然后再调用对应的存储引擎接口访问各个分区的数据

**insert操作**

当写入一条记录的时候，分区层先打开并锁住所有的底层表，然后确定哪个分区接受这条记录，再将记录写入对应底层表

**delete操作**

当删除一条记录时，分区层先打开并锁住所有的底层表，然后确定数据对应的分区，最后对相应底层表进行删除操作

**update操作**

当更新一条记录时，分区层先打开并锁住所有的底层表，mysql先确定需要更新的记录再哪个分区，
然后取出数据并更新，再判断更新后的数据应该再哪个分区，最后对底层表进行写入操作，并对源数据所在的底层表进行删除操作

有些操作时支持过滤的，例如，当删除一条记录时，MySQL需要先找到这条记录，如果where条件恰好和分区表达式匹配，
就可以将所有不包含这条记录的分区都过滤掉，这对update同样有效。如果是insert操作，则本身就是只命中一个分区，其他分区都会被过滤掉。
mysql先确定这条记录属于哪个分区，再将记录写入对应得曾分区表，无须对任何其他分区进行操作

虽然每个操作都会“先打开并锁住所有的底层表”，但这并不是说分区表在处理过程中是锁住全表的，
如果存储引擎能够自己实现行级锁，例如innodb，则会在分区层释放对应表锁。

四、如何使用分区表
全表扫描数据，不需要索引
索引数据，需要区分热点数据

五、使用分区需要注意的问题
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/使用分区需要注意的问题.png)

分区列与索引列尽量保持一致




