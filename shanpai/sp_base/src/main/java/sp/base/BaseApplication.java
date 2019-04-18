package sp.base;

import android.app.Application;

import sp.base.utils.SharedPreUtil;

public class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";

    private static BaseApplication mContext;

    public static BaseApplication getApplication(){
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        //初始化SharedPreferences
        SharedPreUtil.init(this);
    }

}
