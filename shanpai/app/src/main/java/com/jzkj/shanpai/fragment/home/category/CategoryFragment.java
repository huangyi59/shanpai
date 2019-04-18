package com.jzkj.shanpai.fragment.home.category;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jzkj.shanpai.R;
import com.jzkj.shanpai.widget.refresh.RefreshLayout;

import butterknife.BindView;
import sp.base.BaseFragment;

/**
 * author : huangyi
 * date   : 2018/10/09 14:38
 * email  : huangyi@jzkj.com
 * info   : 关注、推荐、同城
 */

public class CategoryFragment extends BaseFragment implements RefreshLayout.OnRefreshListener,RefreshLayout.OnLoadListener {

    @BindView(R.id.layout_refresh)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark));
        mRefreshLayout.setmRecyclerView(mRecyclerView);
        this.onRefresh();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        },2000);
    }

    @Override
    public void onLoad() {
        Log.e("TAG","上拉加载...");
    }
}
