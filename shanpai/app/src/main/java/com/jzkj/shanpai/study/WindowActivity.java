package com.jzkj.shanpai.study;

import android.app.Dialog;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jzkj.shanpai.R;

/**
 * 安卓中所有的视图都是通过Window来呈现的，不管是Activity还是Dialog、还是Toast,他们的视图实际上是附加在Window上
 * Window的具体实现位于WindowManagerSerivce中，windowManager和Win都为Service的交互是一个IPC过程 setContentView，底层也是通过Window来完成的
 * 应用window Activity 系统window toast 子window dialog
 * window的层级范围 应用window1-99 子window1000-1999 系统window2000-2999
 * 每一个Window都对应着一个View和一个ViewRootImpl，Window和View通过ViewRootimpl来建立联系 Window并不是实际存在的，View才是Window存在的实体
 * <p>
 * WindowManager操作Window的过程更像是在操作Window中的View 有视图的地方就有Window
 * WindowManager也是接口继承了ViewManager接口
 * <p>
 * Window的内部机制，Window并不是实际存在的，他是以View的形式存在的
 * WindowManagerGlobe托管 addView requestLayout来完成异步刷新请求 scheduleTraversals实际是View绘制的入口
 * WindowSession最终来完成Window的添加过程,调用addToDisplay交由WindowManagerService处理
 * <p>
 * Activity的创建过程，ActivityThread中的performLaunchActivity来完成整个启动过程
 * PhoneWindow在Activity的attch方法中创建，这个时候DecorView并没有被WindowManager识别
 * 在Activity的handleResumeActivity方法中，首先调用Activity的onResume方法，接着调用makeVisible,在makeVisible，
 * DecorView真正完成了添加和显示两个过程
 * setContentView的过程 PhoneWindow的setContenView installDecor创建DecorView -》genearLayout将ViewGroup添加到DecorView
 * 添加成功后，再与window建立关联
 *
 * Toast属于系统Window
 *
 */
public class WindowActivity extends AppCompatActivity {

    private Button mButton;
    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager mWindowMangager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);
        mButton = findViewById(R.id.btn_test);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testDialog();
            }
        });
    }

    private void testWindow() {
        mButton = new Button(this);
        mButton.setText("button");
        mLayoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                0, 0, PixelFormat.TRANSLUCENT);
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mLayoutParams.x = 100;
        mLayoutParams.y = 300;
        mWindowMangager.addView(mButton, mLayoutParams);
        mLayoutParams.type = 1;//type表示window的类型 应用Window 1-99 子window 1000-1999 系统window 2000 -2999
    }

    /**
     * 只能用Activity的上下文对象，Activity有应用token，applicaiton上下文没有应用token，会报token为null的错
     * 系统Window比较特殊他可以不需要token
     */
    private void testDialog() {
        Dialog dialog = new Dialog(this);
        //dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);
        TextView textView = new TextView(this);
        textView.setWidth(500);
        textView.setGravity(Gravity.CENTER);
        textView.setText("TEST WINDOW");
        dialog.setContentView(textView);
        dialog.show();
    }

    /**
     * Toast属性系统window 内部视图有两种方式指定，一种是系统默认的样式，另外一种是通过setView方法来指定一个自定义View
     */
    private void testToast(){
        Toast toast = new Toast(this);
        toast.show();
        toast.cancel();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

}
