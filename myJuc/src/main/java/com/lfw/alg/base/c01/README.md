### 算法基础
#### 1、评估算法的核心指标
>（1）时间复杂度（流程决定）  
>（2）额外空间复杂度（流程决定）  
>（3）常数项时间（实现细节决定）  
>常见常数时间的操作：  
>>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/常见常数时间的操作.png)
>附：  
>带符号右移 >> (最高位补0或1)  
>不带符号右移 >>> (与最高位无关)  
>
#### 2、排序算法
注：算法复杂度最终算式为等差数列的，复杂度就是O(N⌃2)(N平方)
下面三个排序算法的时间复杂度均为：O（N平方）
> (1) 选择排序:  
>把小的数往前排；找出这些数中的最小值，与第一个值相比较，如果比第一个值小，就将其与第一个值交换，否则不交换；下一轮就从第二个数到最后开始比较，以此类推  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/选择排序.png)
> (2) 冒泡排序:  
>把大的数往后排；即把当前数与后一个数相比较，如果比后一个大，就往交换后排   
>>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/冒泡排序.png)
>（3) 插入排序：  
>把小的数往前排；即把当前数与前面的数相比较，如果前面没有数或比其小，就不交换，反之交换  
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/插入排序2.png)
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/插入排序.png)
>
#### 3、算法拆分注意事项
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/算法拆分注意.png)
>

### 4、额外空间复杂度
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/额外空间复杂度.png)
>

### 5、问题最优解
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/问题最优解.png)
>
### 6、常见时间复杂度
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/常见时间复杂度.png)
>
### 7、二分法
>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/认识二分法.png)
>适合使用二分法查找的场景：  
>>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/认识二分法2.png)
>
### 8、认识异或运算
>>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/认识异或运算.png)
>>>![avatar](/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/image/认识异或运算2.png)
