package com.jzkj.shanpai.fragment.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzkj.shanpai.R;
import com.jzkj.shanpai.adapter.home.CategoryAdapter;
import com.jzkj.shanpai.fragment.home.category.CategoryFragment;
import com.jzkj.shanpai.fragment.home.contract.HomeContract;
import com.jzkj.shanpai.fragment.home.modle.HomeModle;
import com.jzkj.shanpai.fragment.home.presenter.HomePersenter;
import com.jzkj.shanpai.widget.MyView;
import com.jzkj.shanpai.widget.tablayout.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import sp.base.BaseFragment;

public class HomeFragment extends BaseFragment<HomePersenter,HomeModle> implements HomeContract.view,View.OnClickListener {

    @BindView(R.id.tb_layout)
    TabLayout mTabLayout;
    @BindView(R.id.vp_layout)
    ViewPager mViewPager;
    private CategoryAdapter mAdapter;
    private List mList;
    private List<CategoryFragment> mFragments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
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
        mList.add("明星");
        mList.add("关注");
        mList.add("热点");
        mList.add("笑点");

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

    @Override
    public void showMessage(String msg) {

    }

}
