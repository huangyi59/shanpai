package com.jzkj.shanpai.study.android;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jzkj.shanpai.R;
import com.jzkj.shanpai.impl.Factory;
import com.jzkj.shanpai.study.android.bean.TwoTuple;
import com.jzkj.shanpai.study.android.util.ReflectUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 类是程序的一部分 每个类都有一个class对象 构造器也是类的静态方法 forName（）是取得Class对象的引用的一种方法
 * Class引用表示的就是它所指向的对象的确切类型
 * 泛化的Class引用
 * Class引用添加泛型语法的原因仅仅是为了提供编译期类型检查
 *
 * Class类与java.lang.reflect类库一起对反射的概念进行了支持，该类库包含了Field，Method及Constructor类，每个类都实现了Member接口
 * RTTI和反射之间真正的区别在于，对于RTTI来说，编译器在编译时打开和检查.class文件 对于反射机制来说 .class文件在编译时是不可获取的，
 * 所以是在运行时打开和检查.class文件
 *
 * 你可以将方法的参数类型设为基类，那么该方法就可以接受从这个基类中导出的任何类作为参数
 * 在你创建参数化类型的一个实例时，编译器会为你负责转型操作，并且保证类型的正确性 编译期确保类型安全
 *
 * 泛型与其他的类型差不多 不确切的类型
 * Java泛型是使用擦除来实现的，这意味着当你在使用泛型时，任何具体的类型信息都被擦除了 擦除移除了类型信息
 * 由于泛型的擦除，泛型不能用于显示地引用运行时类型的操作之中，列入instanceof new 转型
 *
 * new T()的尝试将无法实现，部分原因是因为擦除,另一部分原因是因为编译器不能验证T具有默认构造
 *
 */
public class ClassActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ClassActivity.class.getName();
    private List<Class<? extends Activity>> mList;
    private Class[] classes = {ClassActivity.class,AppCompatActivity.class,Activity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calss);
        try {

            ReflectUtil.reflectNewInstance();

            ReflectUtil.reflectPrivateConstructor();

            ReflectUtil.reflectPrivateField();

            ReflectUtil.reflectPrivateMethod();

            TwoTuple<String,String> twoTuple = new TwoTuple<>("1","2");
            String a = twoTuple.a;

            //使用泛型方法时，通常不必指明参数类型，因为编译器会为我们找出具体的类型，这称为类型参数推断
            //类型推断只对赋值操作有效
            testGeneric(0);
            testGeneric("");

            List<String> list = new ArrayList<>();
            Log.e(TAG, Arrays.toString(list.getClass().getTypeParameters()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private <T> void testGeneric(T e){
        String a = e.getClass().getName();
    }

    private void test(){
        Class intClass = int.class;
        Class genericInClass = Integer.TYPE;

        //向下转型是安全的 向上转型是不安全
        Building building = new House();

        Class<House> houseType = House.class;
        House house = houseType.cast(building);
    }

    class Building{}
    class House extends Building{}

    @Override
    public void onClick(View v) {

    }

}
