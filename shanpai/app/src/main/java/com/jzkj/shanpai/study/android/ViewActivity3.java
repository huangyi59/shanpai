package com.jzkj.shanpai.study.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jzkj.shanpai.R;

/**
 * ViewActivity3
 * 操作整数基本数据类型中的单个“比特”(bir)，即二进制位，位运算符会对两个参数中对应的位执行布尔代数运算
 *
 */
public class ViewActivity3 extends AppCompatActivity {
    private static final String TAG = ViewActivity3.class.getSimpleName();
    private LinearLayout mLinearLayout;
    private RelativeLayout mRelativeLayout;

    private static final int MODE_SHIFT = 30;
    //最后一位为0代表正数 为1代表负数
    private static final int MODE_MASK = 0x3 << MODE_SHIFT;//0011 << 30
    private static final int UNSPECIFIED = 0 << MODE_SHIFT;//0000 << 30
    private static final int EXACTLY = 1 << MODE_SHIFT;//0001 << 30
    private static final int AT_MOST = 2 << MODE_SHIFT;//0010 << 30

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view3);

        Log.e(TAG, "MODE_MASK:" + MODE_MASK + ",UNSPECIFIED:" + UNSPECIFIED +
                ",EXACTLY:" + EXACTLY + ",AT_MOST:" + AT_MOST);
    }

}
