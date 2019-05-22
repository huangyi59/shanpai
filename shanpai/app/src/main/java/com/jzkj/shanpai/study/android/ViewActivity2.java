package com.jzkj.shanpai.study.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jzkj.shanpai.R;

/**
 * View的绘制流程是从ViewRoot的performTraversals
 * 系统内部通过MeasureSpec来进行View的测量 在View测量的时候，系统会将LayoutParams在父容器的约束下转换成对应的MeasureSpec，然后根据MesureSpec来确定
 * View测量后的宽高，LayoutParams需要和父容器一起才能决定View的MeasureSpec，从而进一步决定View的宽高
 *
 * DecorView的MeasureSpec由其窗口的尺寸和其自身的LayoutParams来共同决定，
 * 对于普通View，其MeasureSpec由父容器的MeasureSpec和自身的LayoutParams来共同决定 MeasureSpec一旦确定后，onMeasuer中就可以确定View的测量宽高
 *
 * 子元素的MeasureSpec的创建与父容器的MeasureSpec和本身的LayoutParams有关，此外还和View的margin和padding有关
 */
public class ViewActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view2);
    }


    private void getScreen(){

    }

}
