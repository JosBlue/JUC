### 算法基础
#### 1、评估算法的核心指标
>（1）时间复杂度（流程决定）  
>（2）额外空间复杂度（流程决定）  
>（3）常数项时间（实现细节决定）  
>常见常数时间的操作：  
>>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/alg-常见常数时间的操作.png)
>附：  
>带符号右移 >> (最高位补0或1)  
>不带符号右移 >>> (与最高位无关)  
>
#### 2、排序算法
注：算法复杂度最终算式为等差数列的，复杂度就是O(N⌃2)(N平方)
下面三个排序算法的时间复杂度均为：O（N平方）
> (1) 选择排序:  
>把小的数往前排；找出这些数中的最小值，与第一个值相比较，如果比第一个值小，就将其与第一个值交换，否则不交换；下一轮就从第二个数到最后开始比较，以此类推  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/alg-选择排序.png)
> (2) 冒泡排序:  
>把大的数往后排；即把当前数与后一个数相比较，如果比后一个大，就往交换后排   
>>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/alg-冒泡排序.png)
>（3) 插入排序：  
>把小的数往前排；即把当前数与前面的数相比较，如果前面没有数或比其小，就不交换，反之交换  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/alg-插入排序2.png)
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/alg-插入排序.png)
>
#### 3、算法拆分注意事项
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/alg-算法拆分注意.png)
>

### 4、额外空间复杂度
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/alg-额外空间复杂度.png)
>

### 5、问题最优解
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/alg-问题最优解.png)
>
### 6、常见时间复杂度
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/alg-常见时间复杂度.png)
>
### 7、二分法
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/alg-认识二分法.png)
>适合使用二分法查找的场景：  
>>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/alg-认识二分法2.png)
>
### 8、认识异或运算
>>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/alg-认识异或运算.png)
>>>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/alg-认识异或运算2.png)


异或运算，满足交换律和结合律
说人话，就是同样一批数，不管是什么样的顺序，异或起来的结果，一定是一样的。
为什么呢，因为和结果相关的，只有和1是否为偶数或者奇数个有关，因为奇数个1，那就肯定是1，偶数个，就是0

三行代码交换两个值
比如：
a='we';
b='23';
则：
a=a^b;
b=a^b;
a=a^b;
这样操作之后，a与b的值就交换了
首先要知道：a^a=0; b^b=0
也就是说，自己异或自己，就等于0
然后0^任何数，还是等于任何数，就理解上面说的了

注意：要这么干的前提，是两个值所指向的内存区域是不一样的，哪怕是值是一样的都没问题
如果指向的区域是一样的，这样搞就不行了，那就会变成0了


排序算法汇总：
一、插入排序
（1）直接插入排序
时间复杂度最坏：O(N^2)
时间复杂度最好：O(N)
空间复杂度：O(1)

（2）希尔排序
时间复杂度最坏：O(N^2)
时间复杂度最好：O(N)
空间复杂度：O(1)

（3）折半插入排序（减少数据的比较次数）
时间复杂度：O(N^2)
空间复杂度：O(1)


二、选择排序
(1)直接选择排序
时间复杂度最坏：O(N^2)
时间复杂度最好：O(N^2)
空间复杂度：O(1)

（2）堆排序
堆排序的核心是堆调整算法。首先根据初始输入数据，利用堆调整算法shiftDown()形成初始堆；
然后，将堆顶元素与堆尾元素交换，缩小堆的范围并重新调整为堆，如此往复。堆排序是一种不稳定的排序算法，其实现如下：
时间复杂度最好：O(nlogN)
空间复杂度：O(1)


三、交换算法
（1）冒泡排序
时间复杂度最坏：O(N^2)
时间复杂度最好：O(N)
空间复杂度：O(1)

（2）快速排序
通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小(划分过程)，然后再按此方法对这两部分数据分别进行快速排序(快速排序过程)。
整个排序过程可以递归进行，以此达到整个数据变成有序序列。快速排序是一种不稳定的排序算法。
时间复杂度最坏：O(N^2)
时间复杂度最好：O(nlogN)
空间复杂度：O(lgN)

四、归并排序（递归）
时间复杂度最坏：O(N^2)
时间复杂度最好：O(N)
空间复杂度：O(1)


五、分配排序(基数排序)
基本思想：空间换时间
时间复杂度：O(d*(r+n))或者 O(dn),d 的大小一般会受到 n的影响
空间复杂度：O(rd + n)或者 O(n)

　直接插入排序、直接选择排序和冒泡排序是基本的排序方法，它们平均情况下的时间复杂度都是O(n^2)，实现也比较简单，它们对规模较小的元素序列很有效。

　　快速排序、堆排序和归并排序是高效的排序方法，它们平均情况下的时间复杂度都是O(nlgn)，其中快速排序是最通用的高效排序算法，但其是不稳定的；归并排序是上述几种排序算法中唯一与初始序列无关的，而且时间复杂度总是O(nlgn)，但其空间复杂度是O(n)，是一种稳定的排序算法；堆排序的时间复杂度总是O(nlgn)，空间复杂度是O(1)，也是不稳定的。它们对规模较大的元素序列很有效。

　　希尔排序的效率介于基本排序方法与高效排序方法之间，是一种不稳定的排序算法。它们各有所长，都拥有特定的使用场景。基数排序虽然具有线性增长的时间复杂度，但实际上开销并不比快速排序小很多，应用相对不太广泛。

　　因此，在实际应用中，我们必须根据实际任务的特点和各种排序算法的特性来做出最合适的选择。







