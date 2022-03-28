网约车01
一、淘宝十年
技术随着业务的不断变更而演进

二、传统架构到分布式的演进
![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/网约车01.png) 

对于第一种单点架构方式，它的缺点很明显：
可扩展性不强，承载的量也少，存在单点问题等等

对于第二种集群架构方式，它的缺点也很明显：
改一个功能，需要将所有服务重启一遍，扩展性也不好

由于有这些问题，架构就继续演进，有了基于微服务的架构方式
![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/网约车02.png)
这种架构方式，就是根据业务或相应功能进行服务拆分，每个服务独立集群部署，功能独立
这种方式还是有问题：
分布式事务难以控制
数据的一致性维护增加了
服务与服务之间的通信成本增加
运维的维护成功更高了

总结：
微服务的好处：
（1）服务聚焦于业务，松耦合
（2）开发简单，效率更加高效
（3）方便维护和部署

微服务的坏处：
（1）增加了系统之间的通信成本
（2）数据一致性问题，分布式环境下存在的问题
（3）服务数增多，运维的压力大

注意：寻找服务架构图，自己尝试画咱们公司的服务架构图！！！


三、技术选型
![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/网约车03.png)
整体技术选择：SpringCloud

springCloud于dubbo相较而言，有什么优缺点：
![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/网约车04.png)

项目整体架构
![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/网约车05.png)

下面就几个问题进行解释
（1）注册中心能做什么？
可以做服务管理，维护注册表，心跳检测机制

那么没有注册中心会是什么样子呢？
没有注册中心，就会出现各个服务互相调用，比较凌乱，在服务较少时，还好，如果服务比较多，那么整个的维护成本就会变得特别大。
而注册中心则可以对这些服务进行统一进行管理。
在面向对象中，有一个设计原则：叫做迪米特原则，又称为最少知识原则，就是一个对象应当对其他对象尽可能少的了解。 


四、服务搭建
1、服务端：搭建eureka高可用集群
2、初始化预设的9个服务，并注册到eureka集群上




