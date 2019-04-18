package sp.base.utils;

import android.util.Log;

/**
 * author : huangyi
 * date   : 2018/9/27
 * email  : huangyi@jzkj.com
 * info   : 打印Log
 */

public class LogUtil {

    public static void d(String tag,String log){
        Log.d(tag,log);
    }

    public static void e(String tag,String log){
        Log.e(tag,log);
    }

}
