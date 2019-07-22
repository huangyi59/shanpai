package com.jzkj.shanpai.study.android.util;

import android.util.Log;

import com.jzkj.shanpai.study.android.bean.Book;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtil {

    private static final String TAG = ReflectUtil.class.getName();

    //创建对象
    public static void reflectNewInstance(){
        try {
            Class<?> classBook = Class.forName(Book.class.getName());
            Object objectBook = classBook.newInstance();
            Book book = (Book) objectBook;
            book.setName("Android开发艺术探索");
            book.setAuthor("huangyi");
            Log.e(TAG,"reflectPrivateConstructor tag = " + Book.class.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //反射私有造方法
    public static void reflectPrivateConstructor(){
        try {
            Class<?> classBook = Class.forName(Book.class.getName());
            Constructor<?> constructorBook = classBook.getDeclaredConstructor(String.class,String.class);
            constructorBook.setAccessible(true);
            Object objectBook = constructorBook.newInstance("Android开发艺术探索","huangyi");
            Book book = (Book) objectBook;
            Log.e(TAG,"reflectPrivateConstructor tag = " + Book.class.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //反射私有属性
    public static void reflectPrivateField(){
        try {
            Class<?> classBook = Class.forName(Book.class.getName());
            Object objectBook = classBook.newInstance();
            Field field = classBook.getDeclaredField("TAG");
            field.setAccessible(true);
            field.set(objectBook,"123456");
            //返回指定对象此Field表示的字段值
            String tag = (String) field.get(objectBook);
            Log.e(TAG,"reflectPrivateField tag = " + tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //反射私有方法
    public static void reflectPrivateMethod(){
        try {
            Class<?> classBook = Class.forName(Book.class.getName());
            Object objectBook = classBook.newInstance();
            Method method = classBook.getMethod("declaredMethod",int.class);
            method.setAccessible(true);
            String string = (String) method.invoke(objectBook,0);
            Log.e(TAG,"reflectPrivateMethod tag = " + string);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
