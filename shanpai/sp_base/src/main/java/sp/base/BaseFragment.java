package sp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import sp.base.utils.ReflectUtil;

/**
 * author : huangyi
 * date   : 2018/09/29/ 1:40
 * email  : huangyi@jzkj.com
 * info   : BaseFragment
 */

public abstract class BaseFragment<P extends BaseContract.BasePresenter,M extends BaseContract.BaseModel> extends Fragment implements View.OnClickListener {

    public String TAG = getClass().getSimpleName();
    protected P mPresenter;
    protected M mModel;
    public Activity mActivity;
    private Unbinder mBind;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(),null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mBind = ButterKnife.bind(this,view);
        mPresenter = ReflectUtil.getTypeInstance(this,0);
        mModel = ReflectUtil.getTypeInstance(this,1);
        if(this instanceof BaseContract.BaseView){
            mPresenter.attachVM(this,mModel,getActivity());
        }
        initView(view,savedInstanceState);
        getBundle(getArguments());
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View view, Bundle savedInstanceState);

    protected void getBundle(Bundle bundle){}

    protected void initOnClickListener(View rootView, int... ids) {
        for (int id : ids) {
            View view = rootView.findViewById(id);
            if (view != null) {
                view.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mBind != null){
            mBind.unbind();
        }
        if(mPresenter != null){
            mPresenter.detachVM();
        }
    }
}
