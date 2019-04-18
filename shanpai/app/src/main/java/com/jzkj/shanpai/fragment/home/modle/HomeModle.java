package com.jzkj.shanpai.fragment.home.modle;

import android.support.v4.app.FragmentActivity;

import com.jzkj.shanpai.fragment.home.contract.HomeContract;

import java.util.ArrayList;
import java.util.List;

public class HomeModle implements HomeContract.Modle {

    private FragmentActivity mActivity;
    private HomeContract.Persenter mPesenter;
    private List<String> list;


    @Override
    public void onStart(FragmentActivity activity, HomeContract.Persenter presenter) {
        this.mActivity = activity;
        this.mPesenter = presenter;
    }

    @Override
    public void getListData() {
        mPesenter.setListData(initList());
    }

    private List<String> initList(){
        list = new ArrayList<>();
        list.add("周俊贤");
        list.add("沙丽");
        list.add("朱满要");
        list.add("黄毅");
        list.add("范先涛");
        list.add("张永生");
        list.add("李娅");
        list.add("刘婷");
        list.add("欧雪");
        list.add("周登正");
        list.add("徐婷婷");
        list.add("毕红晴");
        return list;
    }

}
