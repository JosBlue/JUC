### 线程池
#### 1、Runnable 没有返回值
#### 2、Callable 存在返回值，返回值可以用Future存储
> Callable ==> Runnable + 返回值


#### 3、FutureTask 即是一个任务，又有可以存储返回值的Future  
> Future ==> 存储执行的将来才会产生的结果  
> FutureTask ==> Future + Runnable   
>
#### 4、CompletableFuture 管理多个任务Future

#### 5、线程池核心参数
>（1）核心线程数  
>（2）最大线程数  
>（3）线程空闲时间    
>（4）线程工作队列   
>（5）线程创建工厂  
>（6）拒绝策略（JVM提供了4种方式，但是我们也可以自定义线程拒绝策略）  
> * (1) Abort：抛异常
> * (2）Discard：直接丢弃，不抛异常  
> * (3）DiscardOldest：扔掉排队时间最久的    
> * (4）CallerRuns：调用者处理任务（一般为主线程）    

#### 6、ForkJoinPool
> 特点：
>* 分解汇总的任务  
>* 用很少的线程可以执行很多的任务（子任务），TPE做不到先执行子任务  
>* CPU密集型;  
