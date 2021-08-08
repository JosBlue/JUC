# JUC
多线程与高并发

## 一、创建多线程的几种方式：
1. 继承Thread类
2. 实现Runnable接口
3. 通过线程池的方式创建线程
> 注意：通过java8的方式创建线程并不是一种创建多线程的方式，他只是Thread的方式一种java8的写法

## 二、多线程中常见的几种方法：
1. sleep
> 将一个线程休眠一定时长，单位为ms
2. join
> 加入一个线程：如有一个线程为t1，在他执行过程中，加入t2线程，t2.join();t1线程会等待t2线程执行结束后再继续执行，可以用来控制线程的执行先后顺序
3. yeild
> 将一个线程挂起
4. interrupt
> 打断一个线程，或者说终止一个线程，会有异常抛出，所以需要使用try{}catch(){}代码块将其异常捕获，然后执行相关的业务逻辑
5. wait
> 线程等待被唤醒
6. notify
> 唤醒线程
7. notifyAll
> 唤醒所有线程

## 三、多线程的几种状态
1. new 线程被创建
2. runnable 线程就绪和执行状态
3. waitting 线程等待状态
4. TimeWaitting 等待线程休眠时间结束
5. block 线程阻塞状态
6. temminer 线程终止状态
可通过getState()方法获取当前线程的状态

## 更多信息，请看每个对应包下的readme










