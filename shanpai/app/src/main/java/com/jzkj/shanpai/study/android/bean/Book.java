package com.jzkj.shanpai.study.android.bean;

public class Book {
    private static final String TAG = Book.class.getName();
    private String name;
    private String author;

    public Book(){

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
                '}';
    }
}
