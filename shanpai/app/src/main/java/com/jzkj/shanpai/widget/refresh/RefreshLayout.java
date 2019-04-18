package com.jzkj.shanpai.widget.refresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * author : huangyi
 * date   : 2018/10/
 * email  : huangyi@jzkj.com
 * info   : 支持自动刷新、上拉加载的SwipeRefreshLayout
 */

public class RefreshLayout extends SwipeRefreshLayout{

    private RecyclerView mRecyclerView;

    private int mScaledTouchSlop;

    private boolean isLoading;

    private OnLoadListener mLoadListener;

    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public interface OnLoadListener{
        void onLoad();
    }

    public void setmRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(mRecyclerView == null && mRecyclerView.getChildCount() >0){
            if(getChildAt(0) instanceof RecyclerView){
                mRecyclerView = (RecyclerView)getChildAt(0);
                setListViewScoll();
            }
        }
    }

    /**
     * 在分发事件的时候处理子控件的触摸事件
     */
    private float mDownY, mUpY;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 移动的起点
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 移动过程中判断时候能下拉加载更多
                if (canLoadMore()) {
                    // 加载数据
                    loadMore();
                }
                break;
            case MotionEvent.ACTION_UP:
                // 移动的终点
                mUpY = getY();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void setListViewScoll(){
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(canLoadMore()){
                  loadMore();
                }
            }
        });
    }

    private void loadMore(){
        setLoading(true);
        if(mLoadListener != null){
            mLoadListener.onLoad();
        }
    }

    /**
     * 判断RecyclerView是否滑动到了最底部
     */
    private boolean canLoadMore(){
        // 1. 是上拉状态
        boolean condition1 = (mDownY - mUpY) >= mScaledTouchSlop;
        // 2. 当前页面可见的item是最后一个条目
        boolean condition2 = false;
        if (mRecyclerView != null && mRecyclerView.getAdapter() != null) {

            condition2 = mRecyclerView.canScrollVertically(1);
        }
        // 3. 正在加载状态
        boolean condition3 = !isLoading;

        return condition1 && condition2 && condition3;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

}
