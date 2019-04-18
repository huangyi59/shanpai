package com.jzkj.shanpai.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.RadioButton;


import com.jzkj.shanpai.R;
import com.jzkj.shanpai.base.BaseFragmentTabAdapter;
import com.jzkj.shanpai.fragment.home.HomeFragment;
import com.jzkj.shanpai.fragment.my.MyFragment;

import java.util.ArrayList;
import sp.base.BaseFragment;


/**
 * author : huangyi
 * date   : 2018/9/27
 * email  : huangyi@jzkj.com
 * info   : MainTabAdapter
 */

    public class MainTabAdapter extends BaseFragmentTabAdapter<BaseFragment> {

    public MainTabAdapter(FragmentManager fragmentManager, int containerResId, FragmentActivity fragmentActivity) {
        super(fragmentManager, containerResId, fragmentActivity);
    }

    @Override
    public ArrayList<RadioButton> createTabView() {
        ArrayList<RadioButton> list = new ArrayList<>();
        list.add((RadioButton) findViewById(R.id.radio_0));
        list.add((RadioButton) findViewById(R.id.radio_1));
        return list;
    }

    @Override
    public BaseFragment getFragmentItem(int index) {

        switch (index) {
            case 1:
                return new MyFragment();
            default:
                return new HomeFragment();
        }
    }
}
