package com.jzkj.shanpai.study.android.bean;

/**
 * 静态代理
 */
public class SimpleProxy implements Interface {

    private Interface mInterface;

    public SimpleProxy(Interface mInterface) {
        this.mInterface = mInterface;
    }

    @Override
    public void dosomething() {
        if(mInterface == null)
            return;

        mInterface.dosomething();
    }

    @Override
    public void somethingElse() {
        if(mInterface == null)
            return;

        mInterface.dosomething();
    }

}
