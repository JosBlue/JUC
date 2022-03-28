Spring 概述

一、Spring是什么？
框架、生态

二、Spring概述
区分IOC与DI
IOC 控制反转，说的是一种思想
DI  依赖注入，说的是将属性设置的一种手段

那么思想怎么理解？
我们通常说IOC容器，IOC容器是用来存放对象的，也就是我们所说的bean
那么IOC容器具体是怎么做的呢？

回忆一下我们在初学Spring时，是怎么做的？
第一步：定义我们的xml文件
第二步：new ApplicationContext读取xml文件，然后从application中获取bean就可以了

这个过程很简单，那么实际是怎么做的呢？或者说，为什么application中就会有bean呢？

其实我们可以先结合我们的操作步骤，预想spring创建IOC容器，并初始化、实例化bean的整个过程：
首先，肯定要加载并解析xml文件，或者我们的配置信息
其次，根据解析后的xml文件，初始化我们的bean信息，此时bean只有一些默认值
接着，就是实例化我们的bean,放置在IOC容器中
最后，将Bean开放给客户端进行调用获取

我们设想的整体流程大概就是这个样子，但是实际的spring做的肯定比这个多，要考虑非常多的扩展性、通用性等问题，但是我们可以结合到我们自己
设想的这个思路，去理解spring的整个解析过程

容器，使用Map容器进行Bean的存储，在Map中，整个数据结构是什么样子的呢？  
key:String v:Object  
key:Class v:Object  
key:String v:ObjectFactory(对象工厂)  
key:String v:BeanDefinition(Bean定义对象，因为我们通过key直接拿到字符串是没有意义的,需要拿到这个Bean对应的相关信息才有意义，在
BeanDefinition中存放了非常多的与Bean相关的信息)



三级缓存：解决循环依赖

那么实际spring是怎么做的呢？

图示spring IOC容器创建的整体过程：
图：
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/Bean的生命周期01.png)
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/Bean的生命周期02.png)


解释：
（1）在spring中，我们将beng的定义信息，写在xml文件中；在springBoot中，我们也会将一些bean的信息写在yml或properties中，只是我们通常
不会这么去做，在springBoot中，通常是通过注解的方式。
在这些配置文件中，就是我们的bean的定义信息，也叫beanDefinition
我们只需要加载并解析这些配置文件信息，就可以得到我们的bean定义信息
那么，设想，如果我们后面需要将JSON这种字符串格式的文件，作为我们定义bean对象的文件信息，我们又要如何去进行解析呢？
在spring中，就定义了一套解析文件的规范，即文件解析读取接口：BeanDefinitionReader

（2）有了beanDefinition之后，接下来，我们通常要做的，就是实例化我们的bean。实例化bean的方式有两种，一种是直接new,还有一种是通过反射
在spring中，是通过反射的方式创建对象的，那么为什么不用new的方式呢？
因为反射更加灵活，而不是像new这么死板

spring并没有直接实例化bean，作为一个框架，扩展性，通用性是他必然需要考虑的一个东西
在spring中，BeanFactory是整个容器的根接口，也是容器的入口

（3）从BeanDefinition到Bean实例化这个过程中，在spring中，还有很多步骤需要做  
（1）如果想随时修改Bean的定义信息怎么办？比如在创建过程中，需要替换一些类似于{jdbc.url}这种信息
此时，就会使用到BeanFactoryPostProcessor
在spring容器中，有PostProcessor，叫做后置处理器，也叫做增强器
对应的，有两个大类的接口：BeanFactoryPostProcessor与BeanPostProcessor
BeanFactoryPostProcessor：用于增强BeanDefinition信息
BeanPostProcessor：用于增强Bean的信息
增强，即在原来的基础之上，做一些扩展信息
比如抽象类：PlaceholderConfigurerSupport，就是替换类似于'{}'这种占位符的信息的，这个在动态改变BeanDefinition的信息，这就是一种增强
在实例化之前，如果我们要动态的对BeanDefinition进行修改，或者说增强，直接实现我们的BeanFactoryPostProcessor，然后进行动态设置即可

（2）对BeanDefinition进行增强后，我们就可以进行实例化了，但是实例化实际上是一个非常复杂的操作。同时，我们需要将实例化与初始化给区分开
实例化：在堆中开辟一块空间，对象的属性此时都是默认值
初始化：填充属性初始值，并执行定义的init-method方法
一定是构造器执行完成之后，才会执行init-method方法

实例化与初始化整体构成的，就是创建对象

（3）实例化Bean后，还可以通过BeanPostProcessor对Bean进行增强，并且有两种增强方式
BeanPostProcessor-Before 这叫前置处理器
在对Bean进行增强的中间，就是执行init-method方法
BeanPostProcessor-After 这叫后置处理器
处理完这些后，就开始对Bean进行初始化，即给属性设置相关的初始值

从实例化后，到被初始化完成，中间还包含了一系列的过程：
01-填充属性：就是调用set方法，往其中设置一些属性的值

02-设置Aware接口属性：在AbstractApplicationContext的refresh方法中，
有beanFactory.ignoreDependencyInterface相关的方法;ignoreDependencyInterface与ignoreDependencyType的区别是什么？这个的理解，后续再来补充

03-BeanPostProcessor-Before
04-init-method
05-BeanPostProcessor-After
执行完这些步骤后，才是一个完整的对象，然后被放置到IOC容器中
上面说的这3个步骤，就是Bean的生命周期，只是在完整的生命周期中，最后还有销毁方法


>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/创建对象.png)


接下来，我们需要解决几个问题
（1）Aware接口到底是什么作用？  
当我获取到对象时，我可以获取到当前容器的其他对象  
怎么理解呢？  
换句话说，如果我们在获取对象时，需要获取当前容器的其他对象相关信息，那么就可以实现对应信息的Aware接口，就可以获取到当前容器中的相关对象的信息了；
比如我们需要获取当前对象在IOC容器中的名称，就可以实现BeanNameAware接口，将其set方法中的name,设置给当前对象的某个属性，就可以获取到了。
这种需要设置Aware接口属性的操作，一般是要进行二次开发时会用到。

（2）区分普通对象与容器对象
普通对象：我们自定义的对象信息，通常我们所说的对象，就是指这些对象信息
容器对象：内置对象，或者说是spring需要的对象


理解了这两个问题，下面我们要关注一个非常重要的东西
先抛出问题：
在不同的阶段要处理不同的工作，应该怎么办？
解决这个问题的核心，其实就是设计模式中的观察者模式
观察者模式中，有几个关键的名词：监听器，监听事件，多播器（广播器）
这是我们的一个核心，在refresh方法中，包含了非常多的这方面的东西

到这里，我们需要做一下总结，就是到目前为止，我们看到的，或者说目前了解到的Spring中的接口有哪些
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/spring中的接口.png)

除了上面截图中的，还有很多接口信息，后续也会持续补充
比如，BeanDefinitionRegistry这个接口，就是对BeanDefinition信息进行增删查改操作的接口


最后，理解一个经常被问到的问题：
BeanFactory与FactoryBean的区别?
都是用来创建对象的
但是：
当使用BeanFactory来创建对象时，必须要遵循完整的创建过程，这个过程是由Spring来管理控制的
当使用FactoryBean时，只需要调用getObject就可以返回具体的对象，整个对象的创建过程是由用户自己来控制的，更加灵活





