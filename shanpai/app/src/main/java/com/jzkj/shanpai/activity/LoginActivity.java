package com.jzkj.shanpai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jaeger.library.StatusBarUtil;
import com.jzkj.shanpai.R;
import com.jzkj.shanpai.activity.home.activity.HomeActivity;
import com.jzkj.shanpai.util.ActivitySkipUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sp.base.utils.ToastUtil;

/**
 * author : huangyi
 * date   : 2018/11/11
 * email  : huangyi@jzkj.com
 * info   : 登录页面
 */
public class LoginActivity extends Activity {

    //取消登录
    @BindView(R.id.iv_cancle_login)
    ImageView ivCancle;

    //输入手机号码
    @BindView(R.id.ed_input_phone_number)
    EditText edInputPhoneNumber;

    //输入验证码
    @BindView(R.id.ed_input_phone_code)
    EditText edInputPhoneCode;

    //获取验证码
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;

    @BindView(R.id.layout_other_login)
    LinearLayout layoutOtherLogin;

    @BindView(R.id.layout_login)
    LinearLayout layoutLogin;

    private CountDownTimer mCountDownTimer;

    private final int TIME_INTERVAL = 1000;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucent(this, getResources().getColor(R.color.transparent));
    }

    @OnClick({R.id.iv_cancle_login, R.id.tv_get_code, R.id.btn_login})
    void onCLick(View v) {
        int index = v.getId();
        switch (index) {
            //取消登录
            case R.id.iv_cancle_login:
                break;
            //获取验证码
            case R.id.tv_get_code:
                countTimer();
                break;
            //登录
            case R.id.btn_login:
                login();
                break;
            default:
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        int phoneLength = edInputPhoneNumber.getText().toString().trim().length();
        int codeLength = edInputPhoneCode.getText().toString().trim().length();
        if (phoneLength == 0) {
            ToastUtil.toastCenter("手机号码不能为空！");
            return;
        }
        if (codeLength == 0) {
            ToastUtil.toastCenter("验证码不能为空！");
            return;
        }
        if (phoneLength < 11) {
            ToastUtil.toastCenter("手机号码输入错误！");
            return;
        }
        if (codeLength < 6) {
            ToastUtil.toastCenter("手机验证码输入错误！");
            return;
        }
        goHomePage();
    }

    /**
     * 进入首页
     */
    private void goHomePage() {
        ActivitySkipUtil.goActivity(this, HomeActivity.class);
        overridePendingTransition(R.anim.common_anim_splash_in, R.anim.common_anim_splash_out);
        finish();
    }

    /**
     * 倒计时
     */
    private void countTimer() {
        layoutOtherLogin.setVisibility(View.GONE);
        layoutLogin.setVisibility(View.VISIBLE);
        mCountDownTimer = new CountDownTimer(61 * TIME_INTERVAL, TIME_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                int sec = (int) (millisUntilFinished / 1000);
                updateTimerInterval(sec);
                tvGetCode.setClickable(false);
            }

            @Override
            public void onFinish() {
                updateTimerInterval(0);
                layoutOtherLogin.setVisibility(View.VISIBLE);
                layoutLogin.setVisibility(View.GONE);
                tvGetCode.setClickable(true);
            }
        };
        mCountDownTimer.start();
    }

    /**
     * 更新倒计时时间
     */
    private void updateTimerInterval(int interval) {
        if (interval == 0) {
            tvGetCode.setText("获取验证码");
            return;
        }
        tvGetCode.setText(interval + "s");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancleCountTimer();
    }

    /**
     * 取消倒计时，将引用置为空
     */
    private void cancleCountTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

}
