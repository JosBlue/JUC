服务器的参数设置

一、character 字符
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/mysql字符.png)


二、connection
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/mysql-connection.png)


三、log
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/mysql-log配置参数.png)

log-bin=master-bin 开启master的binlog日志记录
开启binlog：log-bin:on，默认是关闭的

关于redolog undolog binlog详见 README01-base.md

mysql中的ACID
A:原子性
通过undolog来保证和实现

C:一致性

I:隔离性
通过锁来实现

D:持久性
通过redolog来实现

AID共同保障C,C是我们最终的追求

>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/数据更新流程.png)
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/Redolog两阶段提交的意义.png)

个人理解，两阶段提交的核心就是，保证redolog与binlog日志都写完了，才提交，此时事务才算完成


四、cache
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/mysql-cache.png)
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/mysql-cache2.png)



五、Innodb
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/INNODB-log.png)










