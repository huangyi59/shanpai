package com.jzkj.shanpai.study;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import com.jzkj.shanpai.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


/**
 * @author huangyi
 * 常用数据结构
 * java中3中运算符介绍
 * <<   左移运算符，num << 1,相当于num乘以2
 * >>   右移运算符，num >> 1,相当于num除以2
 * >>>  无符号右移，忽略符号位，空位都以0补齐
 */
public class DataStructeActivity extends Activity {

    private static final String TAG = DataStructeActivity.class.getSimpleName();

    private TextView tvShow;
    //Array是有序的，基于动态数组
    ArrayList<String> list = new ArrayList<>();
    LinkedList<String> linkedList = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arithmetic);
        tvShow = findViewById(R.id.tv_show);
        //dynamicArray();
        linkedList();
    }

    /**
     * ArrayList基于动态数组
     */
    private void dynamicArray() {
        int arr[] = {1, 2, 3, 5, 4, 7, 6};
        int b[] = new int[7];
        System.arraycopy(arr, 0, arr, 0, arr.length - 1);
        int arrC[] = Arrays.copyOf(arr, 8);
        tvShow.setText(Arrays.toString(arrC));
        //数组扩展
        list.add("动态数组");
    }

    /**
     * 遍历单链表
     */
    private void linkedList() {
        Node<String> head = null;
        head = new Node<>("node");
        Node<String> node1 = new Node<>("node1");
        head.next = node1;
        Node<String> node2 = new Node<>("node2");
        node1.next = node2;
        printLinkedList(head);
        resertLikedList(head);
    }

    /**
     * 单链表的反转
     */
    private Node<String> resertLikedList(Node<String> head) {
        if (head == null) return null;
        Node<String> resertNode = null;
        Node<String> preNode = null;
        Node<String> cur = head;
        while (cur != null) {

            Node<String> next = cur.next;

            if (next == null) resertNode = cur;

            cur.next = preNode;
            preNode = cur;
            cur = next;

        }
        printLinkedList(resertNode);
        return resertNode;
    }


    private void printLinkedList(Node<String> head){
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.item.toString() + ",");
            head = head.next;
        }
        tvShow.setText(sb.toString());
    }

    private static class Node<E> {
        E item;
        Node<E> next;

        Node(E element) {
            this.item = element;
            this.next = null;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }

    private void linkedList2(){
        //Node2<String> node1 = new Node2<>("");
    }

    private static class Node2<E>{
        E item;
        Node2<E> prev;
        Node2<E> next;
        Node2(Node2<E> prev,E element,Node2<E> next){
            this.item = element;
            this.prev = prev;
            this.next = next;
        }
    }

}
