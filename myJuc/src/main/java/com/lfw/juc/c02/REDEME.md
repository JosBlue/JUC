### Volatile 关键字 使一个变量在多线程间可见
> volatile的两大作用：  
>（1）保证线程可见性（本质上是使用了底层MESI，既缓存一致性协议来保证的）  
>（2）禁止指令重排序(DCL单例)  
><br/> 
>A B线程都用到一个变量，java默认是A线程中保留一份copy，这样如果B线程修改了该变量，则A线程未必知道  
>使用volatile关键字，会让所有线程都会读到变量的修改值  
>注意：  
>(1)volatile 并不能保证多个线程共同修改running变量时所带来的不一致问题，  
>也就是说volatile不能替代synchronized，无法保证原子性（ex：HelloVolatile）  
>(2)volatile 引用类型（包括数组）只能保证引用本身的可见性，不能保证内部字段的可见性（ex：VolatileReference） 
>注意：synchronized不可阻止指令重排序 
