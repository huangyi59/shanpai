package com.jzkj.shanpai.adapter.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jzkj.shanpai.fragment.home.category.CategoryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * author : huangyi
 * date   : 2018/10/09 14:44
 * email  : huangyi@jzkj.com
 * info   : 列表分类的Adapter
 */

public class CategoryAdapter extends FragmentPagerAdapter {

    private List<String> mList;
    private List<CategoryFragment> fragments;

    public CategoryAdapter(FragmentManager fm,List<String> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        if(mList == null)
            return 0;
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position);
    }

    public void setFragments(List<CategoryFragment> fragments) {
        this.fragments = fragments;
    }

}
