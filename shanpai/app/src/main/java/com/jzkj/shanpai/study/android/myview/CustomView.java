package com.jzkj.shanpai.study.android.myview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * mesure完成以后，getMeasuredWidth,getMeasuredHeight获取View测量后的宽高
 *
 * layout决定了View的四个顶点的坐标和实际View的宽高 通过getTop getBottom getLeft getRight拿到4个顶点的位置
 * 通过getWidth getHeight拿到View最终的宽高
 *
 * daraw方法完成后View的内容才能呈现在屏幕上
 *
 * LayoutParams需要和父容器一起才能决定View的MeasureSpec
 */
public class CustomView extends View {

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
