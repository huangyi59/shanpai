package com.jzkj.shanpai.study.android.impl;

import com.jzkj.shanpai.study.android.bean.JavaBean;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AccessApiService {
    @GET("http://www.baidu.com/")
    Call<JavaBean> getCall();
}
