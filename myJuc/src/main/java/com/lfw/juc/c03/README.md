### 1. ReentrantLock
>（1）reentrantlock为可重入锁，可替代synchronized    
>（2）reentrantlock对于锁，需要手动释放，不会自动释放。因此在使用reentrantlock时，遇到异常，一定要在finally中添加释放锁的操作  
>   synchronized会自动释放锁  
>（3）reentrantlock可以进行"尝试锁定"==》tryLock，返回值为布尔型，true表示锁定成功，false则表示失败。  
>   对于尝试锁定失败，或者在指定时间内无法获取锁，可以根据业务自主判断是否继续执行，如果不进行判断，系统会继续执行后续逻辑，需要注意  
>（4）lockInterruptibly()方法能够中断等待获取锁的线程    
>   在一个线程等待锁的过程中，可以被打断  
>（5）reentrantlock可以设置指定为公平锁  
>   但是这种公平是一种相对公平，设置为公平锁后，reentrantlock会从队列中顺序获取线程进行处理。  
>   synchronized是非公平锁  
>
>### 2. ReentrantLock 与 synchronized区别
>（1）synchronized是非公平锁，reentrantlock是公平锁    
>（2）synchronized会自动释放锁，reentrantlock需要手动释放  
>（3）synchronized没法对interrupt做出响应，reentrantlock可以对interrupt做出响应  
>（4）reentrantlock可以进行尝试锁定，然后根据结果值再进行后续处理，synchronized没法处理  
>
>### 3.lock 与 lockInterruptibly比较区别在于：
> lock 优先考虑获取锁，待获取锁成功后，才响应中断。  
> lockInterruptibly 优先考虑响应中断，而不是响应锁的普通获取或重入获取。  
>
>### 4.CountDownLatch 这个类使一个线程等待其他线程各自执行完毕后再执行。
> 是通过一个计数器来实现的，计数器的初始值是线程的数量。  
>每当一个线程执行完毕后，计数器的值就-1，当计数器的值为0时，表示所有线程都执行完毕，然后在闭锁上等待的线程就可以恢复工作了。
               
### 5.CyclicBarrier 循环栅栏 
> 大概的意思就是一个可循环利用的屏障。它的作用就是会让所有线程都等待完成后才会继续下一步行动。  


#### CountDownLatch和CyclicBarrier区别：
> 1.countDownLatch是一个计数器，线程完成一个记录一个，计数器递减，只能只用一次  
> 2.CyclicBarrier的计数器更像一个阀门，需要所有线程都到达，然后继续执行，计数器递增，提供reset功能，可以多次使用  
 