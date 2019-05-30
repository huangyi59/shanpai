package com.jzkj.shanpai.study.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jzkj.shanpai.R;

/**
 * ViewActivity3
 * 操作整数基本数据类型中的单个“比特”(bir)，即二进制位，位运算符会对两个参数中对应的位执行布尔代数运算
 *
 * 一个整数按照绝对值大小转换成的二进制数，成为源码
 * 将二进制数按位取反，所得的新二进制数成为原二进制数的反码 0取1 1取0 反码是相互的 所以也可以称互为反码
 * 补码 反码 + 1 称为补码
 * View的最终大小是在layout阶段确定的
 *
 */
public class ViewActivity3 extends AppCompatActivity {
    private static final String TAG = ViewActivity3.class.getSimpleName();
    private LinearLayout mLinearLayout;
    private RelativeLayout mRelativeLayout;
    private TextView mTvTest;
    private ImageView mIvImage;

    private static final int MODE_SHIFT = 30;
    //最后一位为0代表正数 为1代表负数 高2代表SpecMode，低30代表SpecSize
    private static final int MODE_MASK = 0x3 << MODE_SHIFT;//0011 << 30 11000000 00000000 00000000 00000000
    private static final int UNSPECIFIED = 0 << MODE_SHIFT;//0000 << 30
    private static final int EXACTLY = 1 << MODE_SHIFT;//0001 << 30     01000000 00000000 00000000 00000000
    private static final int AT_MOST = 2 << MODE_SHIFT;//0010 << 30     10000000 00000000 00000000 00000000

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view3);
        Log.e(TAG, "MODE_MASK:" + MODE_MASK + ",UNSPECIFIED:" + UNSPECIFIED +
                ",EXACTLY:" + EXACTLY + ",AT_MOST:" + AT_MOST);
        mTvTest = findViewById(R.id.tv_test);
        mIvImage = findViewById(R.id.iv_test);
        RelativeLayout.MarginLayoutParams marginLayoutParams = (RelativeLayout.MarginLayoutParams) mTvTest.getLayoutParams();
        marginLayoutParams.leftMargin = 10;
        marginLayoutParams.rightMargin = 10;
        marginLayoutParams.topMargin = 10;
        marginLayoutParams.bottomMargin = 10;
        mTvTest.setLayoutParams(marginLayoutParams);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mIvImage.getLayoutParams();
        layoutParams.leftMargin = 10;
        layoutParams.rightMargin =10;
        layoutParams.topMargin = 10;
        layoutParams.bottomMargin = 10;
    }

}
