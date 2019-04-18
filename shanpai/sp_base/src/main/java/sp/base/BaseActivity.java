package sp.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import sp.base.utils.ReflectUtil;

/**
 * author : huangyi
 * date   : 2018/9/28 2:52
 * email  : huangyi@jzkj.com
 * info   : BaseActivity
 */
public abstract class BaseActivity<P extends BaseContract.BasePresenter, M extends BaseContract.BaseModel> extends AppCompatActivity implements BaseContract.BaseView, View.OnClickListener {

    protected P mPresenter;
    protected M mModel;
    private Unbinder unbinder;
    protected ProgressDialog mProgressDialog = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        init(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract void initView(Bundle savedInstanceState);

    private void init(Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this);
        mPresenter = ReflectUtil.getTypeInstance(this, 0);
        mModel = ReflectUtil.getTypeInstance(this, 1);
        if (this instanceof BaseContract.BaseView) {
            mPresenter.attachVM(this, mModel, this);
        }
        initView(savedInstanceState);
    }

    protected void setOnClickListener(int... viewId) {
        for (int id : viewId) {
            View view = findViewById(id);
            if (view != null) {
                view.setOnClickListener(this);
            }
        }
    }

    /**
     * show dialog
     */
    protected final void showProgressDialog(){
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(this, R.style.dialog_bg_transparent);
            mProgressDialog.setContentView(R.layout.dialog_loading);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        if(!mProgressDialog.isShowing()){
            mProgressDialog.show();
        }
    }

    /**
     * close dialog
     */

    protected final void closwProgessDialog(){
        if(mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (mPresenter != null) {
            mPresenter.detachVM();
        }
        if(mProgressDialog != null){
            mProgressDialog = null;
        }
    }

}
