package com.jzkj.shanpai.activity.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.jaeger.library.StatusBarUtil;
import com.jzkj.shanpai.R;
import com.jzkj.shanpai.activity.CameraActivity;
import com.jzkj.shanpai.adapter.MainTabAdapter;
import com.jzkj.shanpai.base.BaseFragmentTabAdapter;
import com.jzkj.shanpai.util.ActivitySkipUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : huangyi
 * date   : 2018/9/27
 * email  : huangyi@jzkj.com
 * info   : 主页Activity
 */

public class HomeActivity extends FragmentActivity{

    private MainTabAdapter mTabAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        mTabAdapter = new MainTabAdapter(getSupportFragmentManager(), R.id.container, this);
        mTabAdapter.setmOnCheckedChangedIntercept(new BaseFragmentTabAdapter.OnCheckedChangedIntercept() {
            @Override
            public boolean onCheckedChangedEvent(int position) {
                Log.e("TAG","position = " + position);
                return true;
            }
        });
        mTabAdapter.setSelectItem(0);
    }

    @OnClick({R.id.radio_text})
    void onClick(View view){
        ActivitySkipUtil.goActivity(HomeActivity.this, CameraActivity.class);
    }

}
