package com.jzkj.shanpai.study;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.jzkj.shanpai.R;

import sp.base.utils.LogUtil;

/**
 * 算法，算法的稳定性是指数组中相同的值，是否会产生位置交换
 */
public class ArithmeticActivity extends Activity {


    private TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arithmetic);
        tvShow = findViewById(R.id.tv_show);
        sort();
    }

    private void sort() {
        int array[] = {5, 4, 6, 3, 8,7};
        //bubbleSort(array);
        //selectSort(array);
        print(array);
        LogUtil.e("tag",binarySearch(array, 1,array.length)+"------");
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

    /**
     * //虚拟机栈 每一个方法都会创建一个栈帧 对应方法的出和入 局部变量表（基本数据类型，引用或则句柄） 方法的出入口 动态链接 操作数栈
     * 快速排序 选取数组中的一个数，将所有比它小的都放到它左边，所有比它大的数都放到右边 a1 a(n-1)
     */
    private void quickSort(int arr[], int low, int high) {
        if (arr == null)
            throw new NullPointerException("array is null");

        if (arr.length == 0)
            return;

        int left = low,right = high;

        if(left >= right){
            return;
        }

        while(low <= high){
            int index = partSort(arr,low,high);
            quickSort(arr,left,index-1);
            quickSort(arr,index+1,right);
        }
        //挡板 大的放在右边，小的放在左边
    }

    private int partSort(int arr[],int left,int right){
        int pivot = arr[left];
        while(left < right){
            while(left < right && arr[left] <= pivot){
                left ++;
            }
            while(left < right && arr[right] >= pivot){
                right --;
            }
            swap(arr,left,right);
        }
        //一轮快速排序后，交换left 和 pivot的位置
        swap(arr,left,pivot);
        return left;
    }

    /**
     * 二分查找 折半查找 序列有序 1，2，3，4，5     查询次数 1     2          3       4  ...     K
     * 时间复杂度 O(log2N)log以2为底n的对数       剩余次数 n/2   n/2*2      n/2*3   n/2*4     n/2*k >= 1
     * n/2*k >= 1
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

    /**
     * 选择排序  工作原理是每一次从待排序的数据元素中选出最小（或最大）的一个元素，存放在序列的起始位置
     * 再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。以此类推
     * 时间复杂度 (n-1) + (n-2) + ... 2 + 1 等差数列求和Sn = n(a1+an)/2 = O(n*n/2) 最优情况是O(n)
     * 空间复杂度 O(1)
     */
    private void selectSort(int arr[]) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    minIndex = j;
                }
            }
            if (i != minIndex) swap(arr, i, minIndex);
        }
    }

    /**
     * 冒泡排序   比较相邻的两个元素，较大者放后面
     * 时间复杂度 (n-1) + (n-2) + ... 2 + 1 等差数列求和Sn = n(a1+an)/2 = O(n*n/2) 最优情况是O(n)
     * 空间复杂度 O(1)
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

    private void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }


}
