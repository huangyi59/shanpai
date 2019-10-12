package com.jzkj.shanpai.study.android.bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DProxyInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //proxy 动态生成的代理对象
        return method.invoke(proxy,args);
    }

}
