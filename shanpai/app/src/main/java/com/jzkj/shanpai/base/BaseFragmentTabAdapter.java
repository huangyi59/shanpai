package com.jzkj.shanpai.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import java.util.ArrayList;

import sp.base.BaseFragment;

/**
 * author : huangyi
 * date   : 2018/10/08 15:04
 * email  : huangyi@jzkj.com
 * info   : BaseFragmentTabAdapter
 */

public abstract class BaseFragmentTabAdapter<FRG extends BaseFragment> implements CompoundButton.OnCheckedChangeListener {
    private FragmentManager mFragmentManager;
    private int mContainerResId;
    private View mView;
    public FragmentActivity mFragmentActivity;

    private SparseArray<FRG> fragments = new SparseArray<>();
    private ArrayList<RadioButton> mTabs;
    private int mSelectItem = -1;
    private OnTabChangeListener onTabChangeListener;
    private OnCheckedChangedIntercept mOnCheckedChangedIntercept;
    private int mLastCheckTab = 0;


    public BaseFragmentTabAdapter(FragmentManager fragmentManager, int containerResId, View view) {
        mFragmentManager = fragmentManager;
        mContainerResId = containerResId;
        mView = view;
        mTabs = createTabView();
        initListener();
    }

    public BaseFragmentTabAdapter(FragmentManager fragmentManager, int containerResId, FragmentActivity fragmentActivity) {
        mFragmentManager = fragmentManager;
        mContainerResId = containerResId;
        mFragmentActivity = fragmentActivity;
        mTabs = createTabView();
        initListener();
    }


    protected <TView extends View> TView findViewById(@NonNull int id) {
        if (mView != null) {
            return (TView) mView.findViewById(id);
        } else if (mFragmentActivity != null) {
            return (TView) mFragmentActivity.findViewById(id);
        }
        return null;
    }

    private void initListener() {
        for (int i = 0; i < mTabs.size(); i++) {
            mTabs.get(i).setOnCheckedChangeListener(this);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (!onClickBaseEvent(buttonView))
                for (int i = 0; i < mTabs.size(); i++) {
                    if (buttonView.getId() == mTabs.get(i).getId()) {
                        if (mOnCheckedChangedIntercept != null) {
                            if (mOnCheckedChangedIntercept.onCheckedChangedEvent(i)) {
                                setSelectItem(i);
                            }
                        } else {
                            setSelectItem(i);
                        }
                    }
                }
        }

        if (!isChecked) {
            for (int i = 0; i < mTabs.size(); i++) {
                if (buttonView.getId() == mTabs.get(i).getId()) {
                    mLastCheckTab = i;
                }
            }
        }
    }

    public interface OnCheckedChangedIntercept {
        boolean onCheckedChangedEvent(int position);
    }

    public void setmOnCheckedChangedIntercept(OnCheckedChangedIntercept mOnCheckedChangedIntercept) {
        this.mOnCheckedChangedIntercept = mOnCheckedChangedIntercept;
    }

    public int getmLastCheckTab() {
        return mLastCheckTab;
    }

    public void setSelectItem(int index) {
        setSelectItem(index, null);
    }

    public void setSelectItem(int index, Bundle bundle) {
        if (index < 0 || index > mTabs.size()) {
            Log.e(getClass().getSimpleName(), "IndexOutOfBoundsException : " + getClass().getSimpleName() + " setSelectItem(int index)");
            return;
        }
        showFragment(index, bundle);
        mSelectItem = index;
        mTabs.get(mSelectItem).setChecked(true);
        if (onTabChangeListener != null) {
            onTabChangeListener.onTabChange(mSelectItem, getSelectItem());
        }
    }

    private void showFragment(int position, Bundle bundle) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        for (int i = 0; i < mTabs.size(); i++) {
            if (i == position) {
                FRG f = fragments.get(i);
                if (f == null) {
                    f = initFragment(i);
                }
                if (bundle != null) {
                    f.setArguments(bundle);
                }
                fragmentTransaction.show(f);
            } else {
                FRG f = fragments.get(i);
                if (f != null) {
                    fragmentTransaction.hide(f);
                }
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private FRG initFragment(int index) {
        FRG fragment = fragments.get(index);
        if (fragment == null) {
            fragment = getFragmentItem(index);
            FRG f = (FRG) mFragmentManager.findFragmentByTag(fragment.TAG);
            if (f == null) {
                addFragment(fragment);
            } else {
                fragment = f;
            }
            fragments.put(index, fragment);
        }

        return fragment;
    }


    private void addFragment(FRG fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(mContainerResId, fragment, fragment.TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public FRG getSelectItem() {
        return fragments.get(mSelectItem);
    }

    public int getSelectPosition() {
        return mSelectItem;
    }

    public void setOnTabChangeListener(OnTabChangeListener onTabChangeListener) {
        this.onTabChangeListener = onTabChangeListener;
    }

    public interface OnTabChangeListener {
        void onTabChange(int position, BaseFragment fragment);
    }

    public int getCount() {
        return mTabs.size();
    }

    private boolean onClickBaseEvent(View paramView) {
        boolean isBaseClick = false;
        return isBaseClick;
    }

    protected FRG findFragmentByTag(Class<? extends FRG> tag) {
        FRG fragment = (FRG) mFragmentManager.findFragmentByTag(tag.getSimpleName());
        if (fragment == null) {
            try {
                fragment = tag.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fragment;
    }

    public abstract ArrayList<RadioButton> createTabView();

    public abstract FRG getFragmentItem(int index);
}
