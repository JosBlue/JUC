1、Mysql中的MVCC
MVCC是mysql中用来解决读写冲突的一种并发控制。
在数据库中，有三种并发场景：
<1>读读：这种不存在任何问题，也不需要并发控制
<2>读写：这种会有线程安全问题，可能会造成事务隔离性的问题，可能会出现脏读，幻读，不可重复读等问题
<3>写写：有线程安全问题，可能存在更新丢失的问题

MVCC是一种用来解决读写冲突的并发控制，也就是为事务分配单项增长的时间戳，为每个修改保存一个版本，版本与事务时间戳关联，读操作只读事务开始前数据库的快照，所以MVCC可以为数据库解决以下问题：
1.在并发读写数据库时，可以做到在读操作时不用阻塞写操作，写操作也不用柱塞读操作，提高了数据库的读写效率


2、Class.java
Class 类是在Java语言中定义一个特定类的实现
这个java文件主要在反射当中使用较多，其中常用的方法包括forName,通过class路径，加载类，getFields，获取对应字段信息等等


3、Arrays.java(有什么坑)
这个类是包含各种操作数组的方法，比如排序相关的sort方法，比较相关的equals方法，计算hash值的hashcode方法等等
同时还提供了转换为List的asList方法，这个是有坑的
<1>使用asList尝试对原生数据类型转换为List,获取到的只有一个带有hashCode的对象(详见：ArraysTest)
原因：
asList 方法的参数必须是对象或者对象数组，而原生数据类型不是对象——这也正是包装类出现的一个主要原因。
当传入一个原生数据类型数组时，asList 的真正得到的参数就不是数组中的元素，而是数组对象本身！此时List 的唯一元素就是这个数组。

解决办法：使用包装类数组

<2> 使用asList方法，转换为ArrayList后，如果尝试增减元素，即尝试更改这个List的大小，就会抛出异常java.lang.UnsupportedOperationException
原因：通过Arrays.asList转换为ArrayList，数组信息保存在一个被final修饰的数组中，所以大小是不可更改的，只能实际创建一个真正的ArraysList才行

4、Object.java
Object是所有类祖先，java当中的每个类都由他扩展而来，定义java类没有默认指明父类，那么就默认继承了Object类，Object类中包含许多基础的方法，
比如：equals方法，hashCode()，clone(),线程相关的wait,notify方法等


5、@Transactional失效场景
常见的场景有：
<1>底层数据库不支持事务
<2>@Transactional注解使用的是AOP，在使用动态代理的时候只能针对public方法进行代理，源码依据在
AbstractFallbackTransactionAttributeSource类中的computeTransactionAttribute方法中，如下：
protected TransactionAttribute computeTransactionAttribute(Method method,
   Class<?> targetClass) {
       // Don't allow no-public methods as required.
       if (allowPublicMethodsOnly() && !Modifier.isPublic(method.getModifiers())) {
       return null;
}
此处如果不是标注在public修饰的方法上并不会抛出异常，但是会导致事务失效。
<3>在整个事务的方法中使用try-catch，导致异常无法抛出，自然会导致事务失效
<4>
