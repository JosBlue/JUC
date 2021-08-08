## JAVA运行时数据区和常用命令
## 1、指令集分类

1. 基于寄存器的指令集
2. 基于栈的指令集
   Hotspot中的Local Variable Table = JVM中的寄存器

## 2、Runtime Data Area

PC 程序计数器

> 存放指令位置
>
> 虚拟机的运行，类似于这样的循环：
>
> while( not end ) {
>
> ​	取PC中的位置，找到对应位置的指令；
>
> ​	执行该指令；
>
> ​	PC ++;
>
> }

JVM Stack

1. Frame - 每个方法对应一个栈帧
   1. Local Variable Table 局部变量表
   2. Operand Stack 操作数栈
      对于long的处理（store and load），多数虚拟机的实现都是原子的
      jls 17.7，没必要加volatile
   3. Dynamic Linking 动态链接
       https://blog.csdn.net/qq_41813060/article/details/88379473    
       java动态链接：动态链接是一个将符号引用解析为直接引用的过程。java虚拟机执行字节码时，遇到一个操作码，操作码第一次使用一个指向另一类的符号引用，则虚拟机就必须解析这个符号引用。解析时需要执行三个基本的任务：
       1.查找被引用的类(有必要的话就装载它，一般采用延时装载)。
       2.将符号引用替换为直接引用，这样当再次遇到相同的引用时，可以使用这个直接引用，省去再次解析的步骤。
       3.当java虚拟机解析一个符号引用时，class文件检查器的第四趟扫描确保了这个引用时合法的。
       
      jvms 2.6.3
   4. return address 返回值地址
      a() -> b()，方法a调用了方法b, b方法的返回值放在什么地方，以及返回后，继续从哪里开始执行

Heap

Method Area

1. Perm Space (<1.8)
   字符串常量位于PermSpace
   FGC不会清理
   大小启动的时候指定，不能变
2. Meta Space (>=1.8)
   字符串常量位于堆
   会触发FGC清理
   不设定的话，最大就是物理内存

Runtime Constant Pool

Native Method Stack

Direct Memory

> JVM可以直接访问的内核空间的内存 (OS 管理的内存)
>
> NIO ， 提高效率，实现zero copy

思考：

> 如何证明1.7字符串常量位于Perm，而1.8位于Heap？
>
> 提示：结合GC， 一直创建字符串常量，观察堆，和Metaspace


### 3、java运行时数据区  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/java运行时数据区.png)
>
>>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/线程共享区域.png)
> 每个线程都有自己的PC，因为线程切换后，回来之后需要知道自己之前运行到哪一步了。  

>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/栈帧结构.png)
>一个方法对应一个栈帧，并且每个栈桢中，都包含其对应的几个模块。  
>
>example:
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/JVMStacks.png)
>JVMStacks example解释：  
>==> 执行new指令，创建一个对象，放在堆内存中，栈内存中存在堆内存对象的地址，放在Operand Stacks（操作数栈）中，指向堆内存中的对象，此时，堆内存中的对象只有默认值，假设变量名为A      
>==> 执行dup指令，复制一份A的副本，假设为A2，此时栈内存中，两个都指向了堆内存中的h对象   
>==> 执行invokespecial指令，调用init()方法，即对象的构造方法，弹出栈内存中的A2，通过A2指向的地址，找到堆内存中的对象，并赋初始值    
>==> 执行astore1指令，即弹出A,存放在local variables中，命名为 h指向对应堆内存中的对象（此时该对象已经初始化完成了）      
>注：之所以要复制一个h,是因为指令3 invokespecial,会用掉其中一个   
>==> 执行aload1指令，重新将local variables中的h压栈  
>==> 执行invokevirtual指令，弹出h,执行m1方法（执行完m1的相关指令后，会return到当前指令的位置继续执行）   
>
>jvmStacksExample2  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/jvmStacksExample2.png)
>注：静态方法中，Operand Stacks中没有this 
>
>JvmStacks递归调用  
>>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/JvmStacks递归调用.png)
>
## 4、常用指令
>(1)store  

>(2)load  

>(3)pop  

>(4)mul  

>(5)sub  

>(6)invoke  
1. InvokeStatic
    调用静态方法  
2. InvokeVirtual
    理解为自带多态，即栈内压的是谁，出栈调用的就是谁的方法  
    final方法也是使用InvokeVirtual指令调用的  
3. InvokeInterface
4. InvokeSpecial
   可以直接定位，不需要多态的方法
   目前看来就是这两个：private 方法 ， 构造方法
5. InvokeDynamic
    1.7之前是没有的，1.7才开始引入
   JVM最难的指令
   lambda表达式或者反射或者其他动态语言scala kotlin，或者CGLib ASM，动态产生的class，会用到的指令 
   注意：for(;;) {I j = C::n;} //MethodArea <1.8 Perm Space (FGC不回收)