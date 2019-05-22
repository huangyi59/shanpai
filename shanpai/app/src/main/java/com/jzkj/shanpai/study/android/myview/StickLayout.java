package com.jzkj.shanpai.study.android.myview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 当Header显示是或则ListView滑动到顶部时由StickLayout拦截事件
 * 当Header隐藏，这时要分情况，如果ListView已经滑动到顶部，并且当前手势是向下滑动的，由StickLayout拦截事件
 */
public class StickLayout extends LinearLayout{

    private static final String TAG = StickLayout.class.getSimpleName();
    public StickLayout(Context context) {
        super(context);
    }

    public StickLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StickLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
