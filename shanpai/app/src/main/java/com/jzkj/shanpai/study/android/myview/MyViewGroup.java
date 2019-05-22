package com.jzkj.shanpai.study.android.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class MyViewGroup extends LinearLayout implements View.OnTouchListener {

    private static final String TAG = MyViewGroup.class.getSimpleName();

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void setListener() {
        setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * MotionEvent 点击事件的辅助类
     * 用来进行事件的分发，返回结果受当前View的onTouchEvent和下级dispatchTouchEvent方法的影响
     */
    int distanceX, distanceY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent");
        boolean intercepted = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
        }
        return intercepted;
    }

    /**
     * 用来判断是否拦截某个事件，如果当前View拦截了某个事件，那么在同一个事件序列当中，此方法不会再次调用
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 在dispatchTouchEvent方法中调用，用来处理点击事件，返回结果表示是否小号当前事件，如果不消耗，则在同一个事件序列中
     * 当前View无法再次接受到事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "执行onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    /**
     * 当一个View需要处理事件时，如果它设置了OnTouchListener，那么OnTouchListener中的onTouch方法会被调用
     * 返回false，当前View的onTouchEvent方法会被调用，返回true，onTouchEvent方法将不会被调用
     * 优先级 OnTouchListener ->onTouchEvent ->onClickListener
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

}
