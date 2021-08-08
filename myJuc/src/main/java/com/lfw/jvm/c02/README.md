### 详解Class加载过程--loading
> Class加载到内存中过程  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/class加载过程.png)
#### 1、类加载-初始化
> 1、加载过程  
> * （1）Loading    
> ** 双亲委派
> * （2）Linking  
>(1)Verification  
>(2)Preparation  
>(3)Resolution  
> * （3）Initializing
>
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/class加载过程02.png)

#### 2、类加载器
> loading过程  
>>>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/类加载器.png)  
>（1）双亲委派机制的过程： （从子到父，再从父到子的过程）   
>整个过程是自底向上查找是否已被加载，均为未找到的情况下，再从上往下告知未被加载，并检查自己是否可以进行加载，若均不可进行加载，则抛出异常  
>
>（2）记忆类加载包含哪些：  
>A-B-C—E  
>A: APP(负责加载classpath指定的内容)    
>B: Bootstrap(负责加载lib/rt.jar等核心类，由C++实现)   
>C: Custom Classloader(自定义ClassLoader)    
>E: Extension(加载扩展jar包：jre/lib/ext/*.jar等内容或由-Djava.ext.dirs指定)  
>
>（3）为什么要是用双亲委派机制？？？   
> 主要是为了安全。（举例使用自定义classLoader重写String库，就可能出现安全问题）  
> 也存在节约资源的考虑  


>注意：  
>（1）父加载器不是继承关系！！！  
>（2）父加载器不是"类加载器的加载器"，也不是"类加载器的父类加载器"！！！ 


>（3）ClassLoader源码解析  
>findInCache -> parent.loadClass -> findClass()  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/classLoader源码解析.png)  
> findClass()，其中使用了钩子函数，模板方法这种设计模式   

> (4)自定义类加载器  
>1. extends ClassLoader  
>2. overwrite findClass() -> defineClass(byte[] -> Class clazz)  
>3. 重写classLoader，使用相关方式，可加密代码  

#### 3、编译器
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/混合模式编译器.png)
>
#### 4、如何打破双亲委派机制
> 1.如何打破双亲委派机制：重写loadClass()  
> 2.合适打破？  
>(1)JDK1.2之前，自定义classLoader都必须重写loadClass()  
>(2)ThreadContextClassLoader可以实现基础类调用实现类代码，通过thread.setContextClassLoader指定（自己设定线程上下文的classLoader）    
>(3)热启动，热部署（osgi tomcat都有自己的模块指定classloader）==》可以加载同一类库不同版本  
>
>// TODO 重写classLoader