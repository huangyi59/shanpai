package com.jzkj.shanpai.study;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.jzkj.shanpai.R;
import com.jzkj.shanpai.study.android.bean.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;


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

    //双向列表
    private LinkedList<String> mLinkedList;
    private HashMap<String, String> mHashMap = new HashMap<String, String>();
    private HashMap<Book, String> mHashMap1 = new HashMap<>();
    private ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
    private ArrayList<String> mList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arithmetic);
        tvShow = findViewById(R.id.tv_show);
        //dynamicArray();
        initLinkedList();
        testHashMap();
        testTreeNode1(initTreeNode());
        testTreeNode2(initTreeNode());
        testTreeNode3(initTreeNode());
        testTreeNode1(testTreeNode3(initTreeNode()));
    }

    /**
     * ArrayList基于动态数组
     */
    private void dynamicArray() {
        int arr[] = {1, 2, 3, 5, 4, 7, 6};
        System.arraycopy(arr, 0, arr, 0, arr.length - 1);
        int arrC[] = Arrays.copyOf(arr, 8);
        tvShow.setText(Arrays.toString(arrC));
        //数组扩展
        list.add("动态数组");
    }

    /**
     * 遍历单链表
     */
    private void initLinkedList() {
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
    }

    private TreeNode<String> initTreeNode(){
        TreeNode<String> treeNode = new TreeNode<>("A");
        treeNode.left = new TreeNode("B");
        treeNode.right = new TreeNode("C");

        treeNode.left.left = new TreeNode("D");
        treeNode.left.right = new TreeNode("E");

        treeNode.right.left = new TreeNode("F");
        treeNode.right.right = new TreeNode("G");
        return treeNode;
    }

    /**
     * 1.HashMap使用equals判断key是否与表中的key相等
     * 2.equals 默认比较对象的地址
     * 3.利用hashCode值进行运算生成数组的下标
     */
    private void testHashMap() {
        String a = new String("abc");
        String b = new String("abc");
        mHashMap.put(a, "1");
        mHashMap.put(b, "2");
        Log.e(TAG, mHashMap.toString());

        Book book = new Book(1);
        Book book1 = new Book(1);
        mHashMap1.put(book, "abc");
        mHashMap1.put(book1, "abc");
        Log.e(TAG, mHashMap1.toString() + "--" + book.hashCode() + "--" + book1.hashCode());

        concurrentHashMap.put("1", "1");
    }

    /**
     * 访问ArrayList优于LinkedList 添加LinkedList由于ArrayList
     */
    private void testList() {
        //基于动态数组
        mList = new ArrayList();
        //默认大小10 oldCapcitya = oldCapcitya + (oldCapcitya >> 1)
        mList.add("0");
        String a = mList.get(0);
        mList.remove("");

        //基于链表
        mLinkedList = new LinkedList<>();
        //直接在末尾将last.next -> E
        mLinkedList.add("0");
        mLinkedList.get(0);
        mLinkedList.remove();
    }

    /**
     * 二叉树的遍历 从左到右 广度优先
     */
    private void testTreeNode1(TreeNode<String> root) {
        if (root == null)
            return;

        List<String> list = new ArrayList<>();
        Queue<TreeNode<String>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode<String> treeNode = queue.poll();
            if (treeNode.left != null) {
                queue.offer(treeNode.left);
            }
            if (treeNode.right != null) {
                queue.offer(treeNode.right);
            }
            list.add(treeNode.item);
        }

        Log.e(TAG,list.toString());
    }

    /**
     * 二叉树的遍历 从上到下 深度优先
     */
    private void testTreeNode2(TreeNode<String> root) {
        if (root == null)
            return;

        List<String> list = new ArrayList<>();
        Stack<TreeNode<String>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode<String> treeNode = stack.pop();
            if (treeNode.right != null) {
                stack.push(treeNode.right);
            }
            if (treeNode.left != null) {
                stack.push(treeNode.left);
            }
            list.add(treeNode.item);
        }

        Log.e(TAG,list.toString());
    }

    /**
     * 二叉树的翻转
     */
    private TreeNode<String> testTreeNode3(TreeNode<String> root){
        if(root == null)
            return null;

        if(root.left != null){
            testTreeNode3(root.left);
        }
        if(root.right != null){
            testTreeNode3(root.right);
        }

        TreeNode<String> temp;
        temp = root.left;
        root.left = root.right;
        root.right = temp;

        Log.e(TAG,root.item);

        return root;
    }

    /**
     * 二叉树是n个节点的有限集合
     * 树只能有一个根节点 节点拥有子数目个数称为度
     * 满二叉树，每个节点都是满的 完全二叉树，允许最后一层的右子树缺失
     * 顺序存储只适合完全二叉树
     */
    static class TreeNode<E> {
        E item;
        TreeNode left;
        TreeNode right;

        public TreeNode(E item) {
            this.item = item;
        }
    }

}
