package com.jzkj.shanpai.study;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.jzkj.shanpai.R;
import com.jzkj.shanpai.study.android.bean.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
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
    ArrayList<String> mList = new ArrayList<>();
    //双向列表
    private LinkedList<String> mLinkedList;

    private TreeMap<String, String> mTreeMap = new TreeMap<>();
    // HashMap是无序的 LinkedHashMap 记录插入顺序 访问顺序
    private HashMap<String, String> mHashMap = new HashMap<String, String>();
    private HashMap<Book, String> mHashMap1 = new HashMap<>();
    private LinkedHashMap<String, String> mLinkedHashMap = new LinkedHashMap<>();
    private ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();

    //内部使用HashMap实现的
    private HashSet<String> mHashSet = new HashSet<>();
    private LinkedHashSet<String> mLinkedHashSet = new LinkedHashSet<>();
    private TreeSet<String> mTreeSet = new TreeSet<>();

    //栈
    private Stack<String> mStack = new Stack<String>();

    //队列
    private Queue<String> mQueue = new LinkedList<>();
    private PriorityQueue<String> mPriortyQueue = new PriorityQueue<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arithmetic);
        tvShow = findViewById(R.id.tv_show);
        //dynamicArray();
        initLinkedList();
        testArray();

        //testTreeNode1(initTreeNode());
        //testTreeNode2(initTreeNode());
        //testTreeNode3(initTreeNode());
        //testTreeNode1(testTreeNode3(initTreeNode()));
        //testTreeNode4(initTreeNode());
        //testTreeNode5(initTreeNode());
        //testTreeNode6(initTreeNode());

        testMap();
        testQueue();
    }

    private void testArray() {
        int array[] = {1, 3, 2, 4, 5, 7, 9};
        int arrayCopy[] = Arrays.copyOf(array, array.length * 2);
        Log.e(TAG, Arrays.toString(arrayCopy));

        int arrayCopy1[] = new int[10];
        System.arraycopy(array, 0, arrayCopy1, 0, array.length);
        Log.e(TAG, Arrays.toString(arrayCopy1));
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
        mList.add("动态数组");
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


    /**
     * 打印链表
     */
    private void printLinkedList(Node<String> head) {
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

    private TreeNode<String> initTreeNode() {
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

        Log.e(TAG, list.toString());
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

            Log.e(TAG, treeNode.item);
        }
    }

    /**
     * 二叉树的翻转
     */
    private TreeNode<String> testTreeNode3(TreeNode<String> root) {
        if (root == null)
            return null;

        if (root.left != null) {
            testTreeNode3(root.left);
        }
        if (root.right != null) {
            testTreeNode3(root.right);
        }

        TreeNode<String> temp;
        temp = root.left;
        root.left = root.right;
        root.right = temp;

        Log.e(TAG, root.item);

        return root;
    }

    /**
     * 二叉树的先序遍历 根节点 左子树，右子树 中 左 右
     */
    private TreeNode<String> testTreeNode4(TreeNode<String> root) {
        if (root == null)
            return null;

        Log.e(TAG, root.item);

        if (root.left != null)
            testTreeNode4(root.left);

        if (root.right != null)
            testTreeNode4(root.right);

        return root;
    }

    /**
     * 二叉树的中序遍历 根节点 左子树，根节点，右子树  左 中 右
     */
    private TreeNode<String> testTreeNode5(TreeNode<String> root) {
        if (root == null)
            return null;

        testTreeNode5(root.left);
        Log.e(TAG, root.item);
        testTreeNode5(root.right);

        return root;
    }

    /**
     * 二叉树的后序遍历 左子树 右子树 根节点  左 右 中
     */
    private TreeNode<String> testTreeNode6(TreeNode<String> root) {
        if (root == null)
            return null;

        testTreeNode5(root.left);
        testTreeNode5(root.right);

        Log.e(TAG, root.item);

        return root;
    }

    /**
     * 二叉树是n个节点的有限集合 树的层次称为树的高度或深度
     * 树只能有一个根节点 节点拥有子数目个数称为度
     * 满二叉树，每个节点都是满的 完全二叉树，允许最后一层的右子树缺失
     * 顺序存储只适合完全二叉树
     * <p>
     * 红黑二叉树 自平衡树 根节点是黑色 不能出现两个连续的红色节点，允许出现两个黑色的节点
     */
    static class TreeNode<E> {
        E item;
        TreeNode left;
        TreeNode right;

        public TreeNode(E item) {
            this.item = item;
        }
    }

    /**
     * 1.HashMap使用equals判断key是否与表中的key相等
     * 2.equals 默认比较对象的地址
     * 3.利用hashCode值进行运算生成数组的下标
     */
    private void testMap() {
        String a = new String("abc");
        String b = new String("abc");
        /**
         * 第一个判断 如果链表数组为空，或者长度为0 重新创建链表数组
         * 第二个判断 判断当前链表是否已经在数组中存在 不存在直接创建 存在赋值给p，执行下一步
         * 如果链表中已经有该元素直接覆盖，并把当前key对应的value赋值给原来的value值，没有直接添加到链表的末尾节点
         *
         * 链表是 Node<K,V> implements Map.Entry<K,V>的子类 TreeNode 是Node的子类
         * 当数据过大时，会将链表转换成红黑二叉树
         */
        mHashMap.put(a, "1");
        mHashMap.put(b, "2");
        Log.e(TAG, mHashMap.toString());

        Book book = new Book(1);
        Book book1 = new Book(1);
        mHashMap1.put(book, "abc");
        mHashMap1.put(book1, "abc");
        mHashMap1.get(book1);
        Log.e(TAG, mHashMap1.toString() + "--" + book.hashCode() + "--" + book1.hashCode());

        mLinkedHashMap.put("1", "a");
        mLinkedHashMap.get("1");

        mTreeMap.put("1", "2");
        mTreeMap.get("1");
        mTreeMap.put("123", "123");

        concurrentHashMap.put("1", "1");

        mHashSet.add("123");
        mLinkedHashSet.add("123");
        mTreeSet.add("123");
    }

    private void testStack() {
        //数组实现的
        mStack.push("a");
        String a = mStack.pop();
    }

    private void testQueue() {
        // 将一个元素插入到队尾 插入失败 返回false 多个用户同时访问一个关联表
        synchronized (mQueue) {
            mQueue.offer("123");
            mQueue.add("234");
        }

        //获取队列头
        mQueue.peek();
        mQueue.element();

        //移除并返回队列头
        mQueue.poll();
        mQueue.remove();

        mPriortyQueue.add(new String("123"));

        int a = 3;
        Log.e(TAG, Integer.toBinaryString(a >>> 1) + "-----" +Integer.toBinaryString(a));

    }


}
