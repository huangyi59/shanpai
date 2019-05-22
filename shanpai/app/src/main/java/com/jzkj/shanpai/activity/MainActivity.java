package com.jzkj.shanpai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.jzkj.shanpai.R;
import com.jzkj.shanpai.activity.home.activity.HomeActivity;
import com.jzkj.shanpai.study.StudyActity;
import com.jzkj.shanpai.util.ActivitySkipUtil;
import com.jzkj.shanpai.util.DeviceUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sp.base.utils.SharedPreUtil;

public class MainActivity extends Activity {

    @BindView(R.id.iv_advertising)
    ImageView mIvSplash;
    @BindView(R.id.tv_timer)
    TextView mTime;

    private CountDownTimer mCountDownTimer;
    private boolean mIsFirstLaunch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    @OnClick({R.id.iv_advertising,R.id.tv_timer})
    public void onClick(View view){
        int id = view.getId();
        switch(id){
            case R.id.iv_advertising:
                break;
            case R.id.tv_timer:
                goHomePage();
                closeCountTimer();
                break;
            default:
                break;
        }
    }

    private void init(){
        //是否是第一次安装或者更新覆盖安装
        long lastInstallApp = DeviceUtil.getLastInstallApp(getApplicationContext(), getPackageName());
        long lastInstallTimeSp = SharedPreUtil.getValue("lastInstallApp", 1L);
        if (lastInstallApp > lastInstallTimeSp) {
            SharedPreUtil.putValue("lastInstallApp", lastInstallApp);
            mIsFirstLaunch = true;
            mTime.setVisibility(View.GONE);
        }
        startCountDown(mIsFirstLaunch ? 1 : 1);
    }

    /**
     * 倒计时启动
     */
    private void startCountDown(int sec) {
        mCountDownTimer = new CountDownTimer(sec * 1000, 500) {
            private int second;

            @Override
            public void onTick(long millisUntilFinished) {
                second = (int) (millisUntilFinished / 1000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTime.setText("跳过 " + second + "s");
                    }
                });
            }

            @Override
            public void onFinish() {
                //倒计时结束
                goToNext();
            }
        };
        mCountDownTimer.start();
    }

    /**
     * 展示引导页
     */
    public void showGuide() {
        ActivitySkipUtil.goActivity(this, GuideActivity.class);
        overridePendingTransition(R.anim.common_anim_splash_in, R.anim.common_anim_splash_out);
        finish();
    }

    private void goToNext() {
//        if (mIsFirstLaunch) {
//            showGuide();
//        } else {
//            if(!MyApp.isLogin){
//                goLoginPage();
//                return;
//            }
//            goHomePage();
//        }
        goHomePage();
    }

    /**
     * 进入首页
     */
    private void goHomePage() {
        ActivitySkipUtil.goActivity(this, StudyActity.class);
        overridePendingTransition(R.anim.common_anim_splash_in, R.anim.common_anim_splash_out);
        finish();
    }

    /**
     * 进入登录页面
     */
    private void goLoginPage(){
        ActivitySkipUtil.goActivity(this, LoginActivity.class);
        overridePendingTransition(R.anim.common_anim_splash_in, R.anim.common_anim_splash_out);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeCountTimer();
    }

    private void closeCountTimer(){
        if(mCountDownTimer != null){
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

}
