package com.jzkj.shanpai.study.android;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jzkj.shanpai.R;

/**
 * <p>
 * ActivityStack 负责栈内的Activity的状态同步 ActivityManagerService简称AMS
 * onStart onStop 是从Activity是否可见的来回调
 * onResume onPause 是从Activity是否位于前台这个角度来回调的
 * Android官方文档对onResume的解释有这么一句，不能在onPause中做重量级的操作，因为必须onPause执行完成以后新的activity才能onResume，
 * 尽量在onStop中操作
 * <p/>
 *
 * <p>
 * 横竖屏切换，先销毁在创建 不做特殊处理 activity —>onSaveInstanceState(onStop之前调用) -> onDestory
 * |
 * V
 * onCreate ->onRestoreInstanceState(onStart之后)
 * 横竖屏切换时不想销毁再创建，配置configChanges 方法 执行onConfigurationChanged的方法
 * 前台任务栈 后台任务栈
 * TaskAffinity属性 翻译为任务相关性 默认情况所有Activity所需的任务栈的名字为应用的包名
 * 单独设置不起作用，需要配合SingleTask, allowTaskReparenting 添加到启动他的那个Activity的任务栈
 * TaskAffinity属性和SingleTask结合存放单独栈  TaskAffinity属性和allowTaskReparenting结合
 * <p/>
 *
 * <p>
 * IntentFilter的匹配规则
 * 设置多个action,匹配一个即可
 * 设置多个category设置多个，都要匹配才行
 * data由两部分组成
 * </p>
 *
 * <p>
 *     startActivityForReult(Intent intent, int requestCode) code > 0
 *     4.0和5.0之间的版本 startActivityForReult目标Activity为singleTask onActivityResult会被提前调用
 * </p>
 *
 */
public class AboutActivity extends Activity {

    private final String DEFALUT_SAVE = "DEFAULT_SAVE";
    private final String TAG = AboutActivity.class.getSimpleName();
    private TextView mTvTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arithmetic);
        /**
         * Activity正常启动 savedInstanceState = null 异常销毁 bundle不为空
         */
        String exceptionString = getExceptionString(savedInstanceState);
        //Log.e(TAG, exceptionString);
        Log.e(TAG, "----------------" + this.getTaskId());

        mTvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent();
            }
        });
    }

    private void startIntent() {
        Intent intent = new Intent();
        intent.setClass(this, AboutActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //data的匹配规则 URI mineType URI的默认值为content或则file
        intent.setDataAndType(Uri.parse("file://"),"video/mpeg");
        startActivity(intent);
    }

    private void start(){
        Intent intent = new Intent(this,ProgressActivity.class);
        startActivityForResult(intent,-1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "----------------onStart------------------");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "----------------onRestart------------------");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "----------------onResume------------------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "----------------onPause------------------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "----------------onStop------------------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "----------------onDestroy------------------");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DEFALUT_SAVE, "异常销毁.");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String exceptionString = getExceptionString(savedInstanceState);
        Log.e(TAG, exceptionString);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    private String getExceptionString(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("DEFAULT_SAVE")) {
            return savedInstanceState.getString("DEFAULT_SAVE");
        }
        return "";
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG, "onConfigurationChanged：" + newConfig.orientation);
    }

}
