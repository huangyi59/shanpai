package com.jzkj.shanpai.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.jzkj.shanpai.MyApp;
import com.jzkj.shanpai.R;
import com.jzkj.shanpai.activity.home.activity.HomeActivity;
import com.jzkj.shanpai.adapter.GuidePageAdapter;
import com.jzkj.shanpai.util.ActivitySkipUtil;
import com.jzkj.shanpai.widget.indicator.CirclePageIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : huangyi
 * date   : 2018/9/27
 * email  : huangyi@jzkj.com
 * info   : 引导页
 */

public class GuideActivity extends FragmentActivity {

    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.viewPage_indicator)
    CirclePageIndicator mViewIndicator;
    @BindView(R.id.iv_back)
    LinearLayout mBack;
    private int[] mGuideImages = new int[]{R.mipmap.guide1, R.mipmap.guide2};
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initViewPager();
    }

    private void initViewPager() {
        ArrayList<View> views = new ArrayList<>();
        for (int i = 0; i < mGuideImages.length; i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(getApplicationContext()).load(mGuideImages[i]).into(imageView);
            if (i == mGuideImages.length - 1) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!MyApp.isLogin){
                            goLoginPage();
                            return;
                        }
                        ActivitySkipUtil.goActivity(GuideActivity.this,HomeActivity.class);
                        finish();
                    }
                });
        }
            views.add(imageView);
        }

        GuidePageAdapter guidePageAdapter = new GuidePageAdapter(views);
        mViewPager.setOffscreenPageLimit(views.size());
        mViewPager.setAdapter(guidePageAdapter);
        mViewIndicator.setViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 进入登录页面
     */
    private void goLoginPage(){
        ActivitySkipUtil.goActivity(this, LoginActivity.class);
        overridePendingTransition(R.anim.common_anim_splash_in, R.anim.common_anim_splash_out);
        finish();
    }

}
