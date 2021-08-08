一、List
1、相关操作命令
<1>LPUSH:从左边推入信息；如LPUSH k1 a b c d
此时list中的数据位：d c b a(因为都是从左推入，因此最后推入的在最左边)

<2>LPOP:从左推出信息
如：LPOP k1:推出的值为:d

同理：RPUSH（从右推入）；RPOP(从右推出)

可以找出规律：
a、list使用LPUSH/RPUSH，并使用其对应的LPOP/RPOP，可以将list看作为==》栈（同向命令）
b、list使用LPUSH/RPUSH，使用其反向的命令RPOP/LPOP，可以将list看作为==》队列（反向命令）


<3>LRANG；语法：LRANG key start stop（注意：此时的L就不是表示左边的意思了，而是表示为list的意思）
从左开始截取key,对应start到end的起始位置信息
如：LRANG k1 0 1:输出：a
也可以使用反向索引进行推出所有信息：LRANG k1 0 -1；由于最后一位的反向索引值为-1，因此可直接使用-1进行全部反向推出

<4>根据下标进行添加或取出
LINDEX/LSET
语法：
LINDEX key index：
LINDEX k1 2:取出k1中的第三个值
LINDEX k1 -1:取出k1中的最后一个值

LSET key index value
LSET k1 2 aaa:给k1的第三个值设置为aaa

可以找出规律：
此时使用这样命令的list就相当于是一个:数组

注意：list不去重

<5>LREM/LINSERT(移除/添加)
语法：LREM key count value（移除）
count：为正数，表示从左边开始，按照顺序，移除key中，value的值，一共移除count个
如：LREM k1 2 a:表示从左边开始，按照顺序，移除k1中，2个为a的值

count：为负数，表示从右边开始，按照顺序，移除key中，value的值，一共移除count个

语法：LINSERT key BEFORE|AFTER prvot value
表示在key的某个值前或后，添加信息
如：LINSERT k1 BEFORE 6 a:表示在k1这个key中，按照从左往右，找到第一个为6的值（注意不是下标），在它前面，添加一个值为a
AFTER,则表示在后面添加

附：LLEN key：表示查询key的长度

<6>阻塞拉取：BLPOP(语法： BLPOP key[key...] timeout)
BLPOP k1 0:表示阻塞一直等待k1队列的值返回

由这个命令可以看出，list可为：阻塞的单播队列（FIFO）
单播：即谁第一个阻塞的，谁就先拿走

附：LTRIM(语法：LTRIM key start stop)
LTRIM k1 0 -1 :表示删除0和-1两端外的信息 


二、HASH
HASH的整体结构就是：MAP<key,MAP>
放入：hset k1 name aa
放入多个：hmset k1 age 13 address cd

取出：hget k1 name
取出多个：hmget k1 age address

查询map中的所有keys：hkeys k1
查询map中的所有value: hvals k1

取出map中所有的其他信息：HGETALL;会返回所有的key及value

hash也可以做数值计算：
加法：
hincrbyfloat k1 age 0.5
hget k1 age:取出值为 13.5

减法： 
hincrbyfloat k1 age -1
hget k1 age:取出值为 12.5

