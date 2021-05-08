### CAS 无锁优化 自旋 也被称为乐观锁
#### 1. Compare And Set  
>核心方法：compareAndSet(ExpectedValue,NewValue) ==》Unsafe.compareAndSwap(this,valueOffSet,expect,update)   
>前一个方法是java中各种原子类调用的方法，底层都是走的Unsafe类的compareAndSwap方法。  
>对于后面的这个方法参数：传入当前对象，当前值在内存中的偏移量（可以通过它，获取到对应的V），当前值的期望值，当前值的更新值，四个参数。JDK11变为了weakCompareAndSwap。
> V，表示当前传入的值；比如为3    
>ExpectedValue，表示期望值，这个期望值应该是要和V相等的，也应该为3。  
>所谓的期望值，也就指我在修改前，期望你的实际值就是3，如果不是，那么我就会重试。  
>因为如果不是我们期望的值，就说明有线程将这个值进行了修改，那么我们对这个值进行修改后，这个值也不是正确的。  
>NewValue，表示修改后的值，只有当期望值与实际传入的值相等时，才会真正将这个修改后的值赋给我们需要修改的值  
##### 注意：CAS的操作是CPU原语支持的，指令不可被打断，因此不会出现线程安全问题

#### 2. CAS实现原子操作的三大问题。
> CAS虽然采用自旋的方式高效的解决了原子操作，但任然存在三个问题.  
#### 2.1 ABA问题。  
>   ABA问题是cas这种处理方式存在的一种问题。  
>具体而言就是，A=1，会增加至3，中间来了一个C，现将A变为了2，也就是B,然后又变回了A。  
>此时对于A而言，用CAS来进行判断是无法判断的，因为他的实际值与期望值是相等的。  
><br/>
>要处理这个问题，可以通过加版本号（version）的方式处理  
>JDK1.5 开始，引入了 AtomicStampedReference这个类，内部维护了一个 Pair 对象, 存储了 value 值和一个版本号, 每次更新除了 value 值还会更新版本号
>对于ABA问题，如果是基础类型，不存在问题，如果是引用类型，就可能会存在问题  
#### 2.2 循环时间长开销大。  
#### 2.3 只能保证一个共享变量的原子操作。 

##### 注意：
> 所有的原子类，底层都是通过Unsafe这个类的compareAndSwap来实现的， unsafe.compareAndSwapInt(this, valueOffset, expect, update);  
>传入当前对象，当前值在内存中的偏移量，当前值的期望值，当前值的更新值，四个参数。JDK11变为了weakCompareAndSwap。
>Unsafe 等同于C/C++中的指针  
>
#### 3. LongAdder
> 在线程数比较多的时候。他的效率比AtomicXX\原子类的操作还高，它的底层思想就是分段锁，分段锁也是CAS操作  
>线程数少的时候，就没有优势了  
>
#### 在数量递增的这种操作中，如果线程数比较多，那么执行效率上
> LongAdder > AtomicXXX > Synchronized