package com.jzkj.shanpai.study.android.myview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * scrollTo相对于初始位置 scrollBy相对于上次移动的最后位置移动
 */
public class ScroView extends View {

    private static final String TAG = ScroView.class.getSimpleName();

    private Scroller mScroller;

    public ScroView(Context context) {
        super(context);
        init();
    }

    public ScroView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScroView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
    }

    int mLastX, mLastY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算滑动距离
                int delatX = x - mLastX;
                int delatY = y - mLastY;
//                smoothScrollTo(delatX,delatY);
//                layout(getLeft()+delatX,getTop()+delatY,getRight()+delatX,getBottom()+delatY);
//                offsetLeftAndRight(delatX);
//                offsetTopAndBottom(delatY);
//                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
//                layoutParams.leftMargin = getLeft() + delatX;
//                layoutParams.topMargin = getTop() + delatY;
//                setLayoutParams(layoutParams);
//                ((View)getParent()).scrollBy(-delatX,-delatY);
//                ((View)getParent()).scrollTo(-delatX,-delatY);
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * ViewGroup在分发绘制自己孩子的时候，会对其子view调用
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        //滚动是否结束
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    public void smoothScrollTo(int destX, int destY) {
        //view 在水平方向或则竖直方向的移动距离
        int scrollX = getScrollX();
        int scrollY = getScrollY();

        int delatX = destX - scrollX;
        int delatY = destY - scrollY;
        mScroller.startScroll(scrollX, scrollY, delatX, delatY,3000);
        invalidate();
    }

}
