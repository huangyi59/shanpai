package com.jzkj.shanpai.study.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.jzkj.shanpai.R;

/**
 * Activity创建的时候会将DecorView和ViewRootImpl建立关联
 * View的绘制流程是从ViewRoot的performTraversals
 * <p>
 * 系统内部通过MeasureSpec来进行View的测量 在View测量的时候，系统会将LayoutParams在父容器的约束下转换成对应的MeasureSpec，然后根据MesureSpec来确定
 * View测量后的宽高，LayoutParams需要和父容器一起才能决定View的MeasureSpec，从而进一步决定View的宽高
 * <p>
 * DecorView的MeasureSpec由其窗口的尺寸和其自身的LayoutParams来共同决定，
 * 对于普通View，其MeasureSpec由父容器的MeasureSpec和自身的LayoutParams来共同决定 MeasureSpec一旦确定后，onMeasuer中就可以确定View的测量宽高
 * <p>
 * 子元素的MeasureSpec的创建与父容器的MeasureSpec和本身的LayoutParams有关，此外还和View的margin和padding有关
 * <p>
 * ViewRootImpl ->measureHierarchy -performMeasure ->measure -onMeasure
 *  LinearLayout measure -onMeasure —measureVertical(遍历子View) ->measureChildBeforeLayout ->measureChildWithMargins ->getChildMeasureSpec ->childMeasure
 * View的measure过程和Activity的生命周期方法不是同步的，无法保证Activity执行了onCreate、onStart、onResume时某个View已经测量完毕
 * <p>
 * View的测量其实就是对其宽高的测量 viewgroup（LinearLayout） -onmeasure measureChildren measure
 * <p>
 * layout过程确定View本身的位置，调用setFrame方法确定四个顶点的位置 setChildFrame确定每个子View的位置
 *
 * draw负责将View画在屏幕上 1.绘制背景 2.绘制自己 3.绘制children 4.绘制装饰
 *
 * invalidate 不会调用onMeasure、onLayout方法 requestLayout会调用
 *
 */
public class ViewActivity2 extends AppCompatActivity {

    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view2);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void getWidthAndHeight() {
        //通过post可以将一个runnable投递到消息队列的尾部
        mView.post(new Runnable() {
            @Override
            public void run() {
                int width = mView.getMeasuredWidth();
                int height = mView.getMeasuredHeight();
            }
        });
        ViewTreeObserver observer = mView.getViewTreeObserver();
        //当View树的状态发生改变或者View树内部的View可见性发生改变时，onGlobLayout方法将被回调
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = mView.getMeasuredWidth();
                int height = mView.getMeasuredHeight();
            }
        });
    }

    /**
     * View已经初始化完毕
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //if (hasFocus) {
        //    int width = mView.getMeasuredWidth();
        //    int height = mView.getMeasuredHeight();
        //}
    }

}
