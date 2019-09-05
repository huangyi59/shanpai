package com.jzkj.shanpai.study.android.bean;

public class Book {
    private static final String TAG = Book.class.getName();
    private String name;
    private String author;
    private int number;

    public Book(){

    }

    public Book(int number) {
        this.number = number;
    }

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private String declaredMethod(int index){
        String string = null;
        switch(index){
            case 0:
                string = "I am declaredMethod 1";
                break;
            case 1:
                string = "I am declaredMethod 2";
                break;
            case 2:
                string = "I am declaredMethod 2";
                break;
        }
        return string;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", number=" + number +
                '}';
    }

    // hashCode相同代表着这两个元素将存在数组中的相同的位置
    // 默认使用对象的地址计算
    @Override
    public int hashCode() {
        return 1;
    }

    //HashMap使用equals方法比较key是否相同 只能比较相同下标位置LinkedList中的元素
    @Override
    public boolean equals(Object obj) {
        return this.number == ((Book)obj).number;
    }
}
