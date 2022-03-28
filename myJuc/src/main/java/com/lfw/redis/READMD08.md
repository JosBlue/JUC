Redis常见情况：

1、区分并理解缓存击穿、穿透、雪崩

（1）缓存击穿：
缓存击穿，是指在redis服务中，存在一些缓存信息，某一时刻，某一个缓存信息过期了，此时，又恰好有非常的大的流量过来，此时，这些流量
就全被打到咱们的数据库上了，这种情况，就叫做缓存击穿。

那么如何解决缓存击穿这种场景呢？
setnx解决；
当高并发中的第一个请求过来访问时，发现redis中这个key的信息不存在时，此时客户端重新发起一个请求，setnx，即设置一个信息，其实就是
获得了一把锁，设置成功后，只有这个请求被允许到数据库中获取信息，获取到对应信息后，再更新信息至缓存中，更新成功后，其他请求就可以
在redis中获取到对应信息了

但是，这种方式，涉及到锁，需要考虑1个问题，就是死锁：
（1）setNx的线程挂了，setNx如果没有过期时间，那么这把锁就一直无法释放，这种情况怎么办？
给setNX设置过期时间

（2）setNX的过期时间，如果数据还未更新至redis中，但是setNx已经过期了，怎么办？
开多个线程，一个线程获取数据，并更新至redis中；另一个线程监控数据是否更新完毕，如果第一个线程还没有更新完信息，就延长setNX的过期时间
（但是这么去做，客户端的代码复杂性就很高了；从这里我们也可以看出，要自己实现分布式协调这样很麻烦，可以和后面zk实现的分布式锁的方式做比较，那样是比较方便的）



（2）缓存穿透：
缓存穿透，从业务接收的查询，是你的系统根本不存在数据，那么对应的缓存信息在咱们的redis服务器上也是不存在，但是又有非常大的流量访问过来，此时，这些访问流量就全被打到了咱们的数据库上
了，这种情况，就叫做缓存穿透

解决缓存穿透的方法：
布隆过滤器
布隆过滤器具体做法：
可以将布隆过滤器的所有实现全放在客户端，此时客户端的代码复杂度就比较高，并且对于内存的要求也会比较高，但是对于redis而言，就没有任何压力了
也可以将布隆过滤器的实现算法放在客户端，将计算出来的信息，存放在redis的bitmap中，分摊压力，这个时候，所有的服务是无状态的，你的客户端只包含算法
还可以直接使用redis集成布隆模块，完全处理信息，此时客户端只负责访问

但是布隆过滤器也有自己的缺点/问题：布隆过滤器只能增加，不能删除；
换句话说，如果你的业务中，存在数据的增删情况，那么使用布隆过滤器后，就会存在原本已经被删除的数据，在布隆过滤器中仍旧存在
要解决这个问题，可以有如下几种解决方法：
1. 将删除的数据，修改存成一个空key，就可以继续使用布隆过滤器
2. 直接讲过滤器更换为布谷鸟过滤器(布谷鸟过滤器过期删除信息，是通过key顺序循环遍历查找删除7)



（3）缓存雪崩：
缓存雪崩，是指在某一时刻，某大一批缓存信息同时过期，由于这大一批缓存都过期了，间接导致大量的流量打到了数据库上，这种情况，就叫做缓存雪崩

解决缓存雪崩的方法：
对于缓存雪崩，要分成两种情况来看
（1）如果是这些缓存信息，并不是需要某一时刻集体更新信息的话，也就是说，这批缓存信息过期时间不受限制，时点性无关，那么就可以分散随机 这些缓存的信息过期
时间，避免这种同一时间过期的情况发生

（2）如果这些缓存信息，就需要在某个时刻集体更新信息，否则数据就可能产生一些问题， 那么此时使用分散过期时间就不可行了，这个时候，就可以通过setnx的方式，来处理这种
问题。（强依赖击穿方案）；也可以在业务层中，加判断，做零点延时，很短的时间内，不要将流量放进来，就在这个时间间隙下，更新完所有数据


2、分布式锁
SetNX实现，加上过期时间，并通过多线程控制延长过期时间（守护线程）
但是用setNX做分布式锁，实现起来较为复杂，优势是比较快

一般咱们涉及到分布式锁，就是使用zookeeper来实现，zk实现分布式锁的优势是十分快捷简单且方便，使用zk实现分布式锁的速度上不快，但是对于数据一致性的保障上，是十分可靠的


3、API

RedisTemplate VS StringRedisTemplate
RedisTemplate 存储字符串时，会在字符串前进行序列化
StringRedisTemplate 则不会

其实他们两者之间的区别主要在于他们使用的序列化类。
RedisTemplate使用的是 JdkSerializationRedisSerializer
StringRedisTemplate使用的是 StringRedisSerializer


spring-boot-starter-json
ObjectMapper.toHash(Object); 可以将一个对象转换成Map
ObjectMapper.convertValue(map,Object.class)


