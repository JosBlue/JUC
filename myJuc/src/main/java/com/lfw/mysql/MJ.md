幻读：
快照读与当前读都存在的时候，就会产生幻读，可以通过加间隙锁的方式来避免幻读
在RR的隔离级别下，一般select * from ... where... 是快照读，不加锁，而for update,lock in share model,update,delete都属于当前读，
如果事务中都用快照读，那么不会产生幻读问题，如果快照读和当前读一起使用，就会产生幻读
产生幻读的原因就是update,delete,insert对数据区间内的数据做了操作后，更新了read view导致的

解决幻读的方式：
select * from XX for update；这个可以加间隙锁


锁：
隔离级别为：RR时
（1）RR+表无显示主键且无索引  
select * from table for update 表锁 临键锁


（2）RR+表有显示主键且无索引  
select * from table for update 表锁 临键锁
主键会默认创建索引
select * from table where id = 10 for update 行锁  记录锁

select * from table where id = 10 and name = 10 for update 行锁  记录锁
and不影响索引，仍然是行锁 记录锁

select * from table where id = 10 or name = 10 for update 表锁 临键锁 
or 导致索引失效


（3）RR+表无显示主键且有索引
数据：10，20，30
id 不是主键，但是时候普通索引
select * from table for update 表锁 临键锁

select * from table where id = 10 for update 
这次的锁有点多
在id 上加一个临键锁
聚簇索引，rowId，行锁
idx 索引上 加间隙锁 因为这个索引列不是唯一的，因此是间隙锁

锁的范围：
负无穷  10 左开右闭 临键锁 负无穷，是因为10前面没有数据了
rowId 行锁
idx 10,20 开区间 间隙锁（10，20）是因为后面数据是20

注意：以上这些加锁操作，都是为了避免幻读

（4）RR+表无显示主键且有唯一索引
select * from table where id = 10 for update  行锁 
此时，id唯一列，不会产生幻读，只需加行锁


（4）RR+表有显示主键且有索引
id 主键
name 普通索引

select * from table for update
表锁 临键锁
id 行锁
name 间隙锁


select * from table where name='10' for update
id 行锁
name 间隙锁


select * from table where id=10 for update
id 行锁


select * from table where id=10 and name = '10' for update
id  行锁
没有间隙锁
因为id查询已经能唯一确定一条记录了


（5）RR+表有显示主键且有唯一索引
select * from table where  name = '10' for update
name 行锁

select * from table where  id = '10' for update
id 行锁


select * from table where id=10 and name = '10' for update
id 行锁
只给主键加索引，是因为通过执行计划发现索引走的是主键，所以才只给id加行锁

注意，在说一遍：通过加临键锁或间隙锁的方式，来避免产生幻读


隔离级别为：RC
注意，由于RC隔离级别是不需要解决幻读的问题的，因此，不会存在需要临键锁、间隙锁来解决幻读的问题
这是，所有的临键锁，间隙锁，都会退化成对应的行锁

（1）RR+表无显示主键且无索引
select * from table  for update
行锁，分别锁住所有行记录

select * from table where id = 10  for update
行锁，给10这行记录加行锁


（2）RR+表有显示主键且无索引
select * from table  for update
行锁，分别锁住所有行记录

select * from table where id = 10  for update
行锁，主键，给10这行记录加行锁

select * from table where id = 10 and name = 10  for update
行锁，主键，给10这行记录加行锁

（3）RR+表无显示主键且有索引
select * from table  for update
行锁，分别锁住所有行记录

select * from table where id = 10  for update
行锁，给id和自增索引都加行锁


（4）RR+表无显示主键但有唯一索引
select * from table  for update
行锁，唯一索引加行锁，分别给每一行加行锁

select * from table where id = 10  for update
行锁，给id和自增索引都加行锁

实际上，RC中，解决脏读问题，加锁，只有行锁
在RR中，为了解决幻读的问题，就涉及到来临键锁，间隙锁这些

RC与RR解决的问题，在于read view 的创建时机不一样


关于锁：
1、mysql中的锁有哪些
2、锁主要应用于事务的隔离级别中，用于提高数据库的并发能力
3、在不同的隔离级别下，不同的索引情况下分别添加的是什么锁

关于mysql的优化
之前也做过一些优化mysql的点，但优化并不是在出现问题后再进行优化，而需要在优化之前，就要提前做一些预防，比如在表设计阶段，schema的设计等
相关问题，肯定是要提前准备，并且提前想清楚的。但是在实际的应用过程中，很多的问题，可能需要等到实际场景下才会出现，我们一般是通过profile,profermance_schema等相关方式来监控sql,
在我们公司的内部，也有sql执行的监控平台，可以自己配置sql预估的执行时间，当出现一些问题时，需要优化sql,我们就会通过执行计划、索引、sql优化、参数调整等方式来进行调优，像我之前做的一个
品牌活动项目，需要查询活动列表页，因为需要查询构造的数据比较多，关联的表也比较多，所以查询的速度很慢，我们通过对关键关联字段添加索引，拆分相关表查询等方式，最终提升了整个sql执行效率。
其实，sql优化最主要的就是思路，不能一上来就开始跑sql，开始优化，需要全链路的分析，找到关键的问题所在，然后一刀毙命的方式去解决。







 








