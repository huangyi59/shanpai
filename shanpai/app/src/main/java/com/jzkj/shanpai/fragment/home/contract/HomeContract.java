package com.jzkj.shanpai.fragment.home.contract;

import android.view.View;

import java.util.List;

import sp.base.BaseContract;

/**
 * author : huangyi
 * date   : 2018/10/12 17:41
 * email  : huangyi@jzkj.com
 * info   : HomeContract
 */
public interface HomeContract{

    interface view extends BaseContract.BaseView{

    }

    abstract class Persenter extends BaseContract.BasePresenter<View,Modle>{
        public abstract void setListData(List<String> list);
    }

    interface Modle extends BaseContract.BaseModel<Persenter>{
        void getListData();
    }

}
