package com.jzkj.shanpai.study;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jzkj.shanpai.R;
import com.jzkj.shanpai.study.android.bean.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import sp.base.utils.LogUtil;

/**
 * @author huangyi
 * 算法，算法的稳定性是指数组中相同的值，是否会产生位置交换
 * 时间复杂度：一个算法中的语句执行次数称为语句频度或时间频度。记为T(n)
 * 空间复杂度：一个算法的空间复杂度(Space Complexity)S(n)定义为该算法所耗费的存储空间
 * 虚拟机栈 每一个方法都会创建一个栈帧 对应方法的出和入 局部变量表（基本数据类型，引用或则句柄） 方法的出入口 动态链接 操作数栈
 * <p>
 * ArrayList的扩容 grow方法，Arrays.copyOf Sytem.arraycopy
 * 1 2
 * 1 2
 */
public class ArithmeticActivity extends Activity {
    private static final String TAG = ArithmeticActivity.class.getSimpleName();

    private TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arithmetic);
        tvShow = findViewById(R.id.tv_show);
        //sort();
    }



    private void sort() {
        int array[] = {5, 3, 6, 4, 8, 7};
        //bubbleSort(array);
        //selectSort(array);
        //quickSort(array, 0, array.length - 1);
        //mergeSort(array);
        print(array);
        LogUtil.e("tag", binarySearch(array, 1, array.length) + "------");
    }

    /**
     * 冒泡排序   比较相邻的两个元素，较大者放后面
     * 时间复杂度 (n-1) + (n-2) + ... 2 + 1 等差数列求和Sn = n(a1+an)/2 = O(n*n/2) 最优情况是O(1)
     * 空间复杂度 O(1)
     * 稳定性：稳定
     */
    private void bubbleSort(int[] arr) {
        boolean flag = false;
        //外层循环控制排序趟数
        for (int i = 0; i < arr.length - 1; i++) {
            //内层循环控制每一趟排序多少次
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    flag = true;
                }
            }
            if (!flag) break;
        }
    }

    /**
     * 选择排序  工作原理是每一次从待排序的数据元素中选出最小（或最大）的一个元素，存放在序列的起始位置
     * 再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。以此类推
     * 时间复杂度 (n-1) + (n-2) + ... 2 + 1 等差数列求和Sn = n(a1+an)/2 = O(n*n/2) 最优情况是O(n)
     * 空间复杂度 O(1)
     * 稳定性：稳定
     */
    private void selectSort(int arr[]) {
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    minIndex = j;
                    //保证其稳定性
                    if (i != minIndex) swap(arr, i, minIndex);
                }
            }
        }
    }

    /**
     * 快速排序 选取数组中的一个数，将所有比它小的都放到它左边，所有比它大的数都放到右边 a1 a(n-1) 一轮排序结束后将pivot的和left
     * 所在位置交换
     * 时间复杂度： 比较趟数 1      2       3      4    .... k
     * n    n/2*2   n/2*3  n/2*4     n/2*k >=1 每一次一分为二，2^K = n,k=log(n),每一次最多交换n次 O（nlog(n)）
     * <p>
     * 空间复杂度： O(logn) -->递归的栈空间 快速排序每次递归都会返回一个中间值的位置，必须使用栈。所以空间复杂度就是栈用的空间
     * <p/>
     * 稳定性：不稳定
     */
    private void quickSort(int arr[], int left, int right) {
        if (arr == null)
            throw new NullPointerException("array is null");

        if (arr.length == 0)
            return;

        if (left >= right)
            return;

        int low = left, high = right, pivot = arr[left];

        while (low != high) {
            while (low < high && arr[high] >= pivot)
                high--;
            if (high > low)
                arr[low] = arr[high];//a[i]已经赋值给temp,所以直接将a[j]赋值给a[i],赋值完之后a[j],有空位
            while (low < high && arr[low] <= pivot)
                low++;
            if (low < high)
                arr[high] = arr[low];
        }
        arr[low] = pivot;//把基准插入,此时i与j已经相等R[low..pivotpos-1].keys≤R[pivotpos].key≤R[pivotpos+1..high].keys
        quickSort(arr, left, low - 1);/*递归左边*/
        quickSort(arr, low + 1, right);/*递归右边*/
    }

    /**
     * 二分查找 折半查找 序列有序 1，2，3，4，5     查询次数 1     2          3       4  ...     K
     * 时间复杂度 O(log2N)log以2为底n的对数       剩余次数 n/2   n/2*2      n/2*3   n/2*4     n/2*k >= 1
     * n/2*k >= 1
     * 空间复杂度 O（1）
     */
    private int binarySearch(int arry[], int target, int size) {
        int low = 0, high = size - 1, mid;
        //防止遗漏，比如只有一个数据的时候
        while (low <= high) {
            //防止溢出 int是32位，最大值是65535 low+high/2 - low/ （low+high）/2
            mid = low + (high - low) / 2;
            if (target == arry[mid]) {
                return mid;
            } else if (target > arry[mid]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    public void mergeSort(int arr[]) {
        //在排序前，先建好一个长度等于原数组长度的临时数组，避免递归中频繁开辟空间
        int temp[] = new int[arr.length];
        Sort(arr, 0, arr.length - 1, temp);
    }

    /**
     * 归并排序：该算法采用经典的分治（divide-and-conquer）策略（分治法将问题分(divide)成一些小的问题然后递归求解
     * 时间复杂度：O(nlog(n))
     * 空间复杂度：O(log(n)+n) -->递归的栈空间  每次递归都会释放掉所占的辅助空间
     * 稳定性：稳定排序
     */
    private void Sort(int arr[], int left, int right, int temp[]) {
        if (arr == null) throw new NullPointerException("arr is null");

        if (arr.length == 0) return;

        if (left >= right) return;

        //Integer.MAX_VALUE 0x7fffffff 0111 + 28个1 2^31 - 1
        int mid = left + (right - left) / 2;
        Sort(arr, left, mid, temp);//左边归并排序，使得左子序列有序
        Sort(arr, mid + 1, right, temp);//右边归并排序，使得右子序列有序
        merge(arr, left, mid, right, temp);//将两个有序子数组合并操作
    }

    private void merge(int[] arr, int left, int mid, int right, int[] temp) {
        int i = left;//左序列指针
        int j = mid + 1;//右序列指针
        int t = 0;//临时数组指针
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[t++] = arr[i++];
            } else {
                temp[t++] = arr[j++];
            }
        }
        while (i <= mid) {//将左边剩余元素填充进temp中
            temp[t++] = arr[i++];
        }
        while (j <= right) {//将右序列剩余元素填充进temp中
            temp[t++] = arr[j++];
        }
        t = 0;
        //将temp中的元素全部拷贝到原数组中
        while (left <= right) {
            arr[left++] = temp[t++];
        }
    }

    private void print(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i != array.length - 1) {
                sb.append(array[i]).append(",");
            } else {
                sb.append(array[i]);
            }
        }
        tvShow.setText(sb.toString());
    }

    private void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

}
