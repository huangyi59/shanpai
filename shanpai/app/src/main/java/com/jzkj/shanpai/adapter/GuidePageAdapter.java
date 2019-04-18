package com.jzkj.shanpai.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * author : huangyi
 * date   : 2018/9/27
 * email  : huangyi@jzkj.com
 * info   : 引导页adapter
 */

public class GuidePageAdapter extends PagerAdapter {

    private List<View> views;

    public GuidePageAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        if(views != null)
            return views.size();
        else
            return 0;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(views.get(position), 0);
        return views.get(position);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
}
