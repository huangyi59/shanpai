package com.jzkj.shanpai.fragment.my;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.jzkj.shanpai.R;
import com.jzkj.shanpai.activity.home.activity.HomeActivity;
import com.jzkj.shanpai.adapter.home.CategoryAdapter;
import com.jzkj.shanpai.fragment.home.category.CategoryFragment;
import com.jzkj.shanpai.widget.tablayout.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import sp.base.BaseFragment;

public class MyFragment extends BaseFragment {

    @BindView(R.id.my_tb_layout)
    TabLayout mTabLayout;
    @BindView(R.id.my_vp_layout)
    ViewPager mViewPager;
    private CategoryAdapter mAdapter;
    private List mList;
    private List<CategoryFragment> mFragments;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        init();
    }

    private void init(){
        initdata();
        mAdapter = new CategoryAdapter(getFragmentManager(),mList);
        mAdapter.setFragments(mFragments);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mAdapter);
    }

    private void initdata(){
        mList = new ArrayList();
        mList.add("作品");
        mList.add("私密");
        mList.add("喜欢");

        mFragments = new ArrayList();
        for(int i = 0 ;i<4 ; i++){
            Bundle bundle = new Bundle();
            CategoryFragment fragment = new CategoryFragment();
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }
    }

    @Override
    public void onClick(View v) {

    }

}
