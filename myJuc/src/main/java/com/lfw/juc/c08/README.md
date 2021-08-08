### 线程池
#### 主要分为两类：
>(1)ThreadPoolExecutor  
>(2)ForkJoinPool  
>ForkJoinPool特点：  
>* 分解汇总的任务  
>* 用很少的线程可以执行很多的任务（子任务），TPE做不到先执行子任务   
>* CPU密集型  

### Executors 线程池工厂
#### 1. SingleThreadExecutor 单线程线程池
> 核心线程数=最大线程数=1；存活时间=0  
> 存储队列：LinkedBlockingQueue  
> 为什么要有单线程的线程池？  
> 他拥有完整的生命周期管理，并且有自己的默认队列  
>
#### 2.CacheThreadPool 弹性线程池
> 0个活跃线程，最大线程数=Integer.MAX_VALUE  
> 存储队列： SynchronousQueue（来一个任务，就需要消费这个任务，否则阻塞）  


#### 3.FixThreadPool 固定线程数的线程池 
> 核心线程数=最大线程数==》均由用户指定  
> 存活时间：0  
> 存储队列：LinkedBlockingQueue  
>
>问题：CacheThreadPool与FixThreadPool使用的时候怎么选择？  
>阿里：两个都不用，自己估算并进行精确定义  
> 如果你的程序的使用线程忽高忽低，不稳定，那么可以考虑使用CacheThreadPool  
> 如果你的程序使用的线程数一直比较问题，你自己可以估算出大概多少线程可以处理，那么可以使用FixThreadPool  
>
#### 4.ScheduledThreadPool 定时任务线程池
> 用户自定义核心线程数，最大线程数=Integer.MAX_VALUE  
> 存活时间： 0  
> 存储队列：DelayedWorkQueue  
> 在使用时，可以指定延迟执行的时间  
>
#### 5.WorkStealingPool
> 每个线程都有自己单独的队列，当线程执行完自己的队列中的任务后，会到其他线程的队列中拿出任务进行执行  
>>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/workStealThreadPool.png) 

#### 6.ForkJoinPool
> 分而治之，然后汇总结果    

#### 并发VS并行
> 并行是并发的子集    
> 并发是指任务提交，并行则是指任务执行  