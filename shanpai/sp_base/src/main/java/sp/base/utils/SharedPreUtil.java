package sp.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * author : huangyi
 * date   : 2018/9/28 2:00
 * email  : huangyi@jzkj.com
 * info   : SharedPreferences工具类
 */
public class SharedPreUtil {
    private static final String TAG = "SharedPreUtil";

    private static final String mSpName = "shanpai_shared";

    private static SharedPreferences mShared = null;

    private static SharedPreferences.Editor mEditor;

    public static synchronized void init(Context context){
        if(mShared == null){
            mShared = context.getSharedPreferences(mSpName,Context.MODE_PRIVATE);
            mEditor = mShared.edit();
        }
    }

    public static boolean putValue(String k, String v) {
        mEditor.putString(k, v);
        return mEditor.commit();
    }

    public static boolean putValue(String k, int v) {
        mEditor.putInt(k, v);
        return mEditor.commit();
    }

    public static boolean putValue(String k, boolean v) {
        mEditor.putBoolean(k, v);
        return mEditor.commit();
    }

    public static boolean putValue(String k, float v) {
        mEditor.putFloat(k, v);
        return mEditor.commit();
    }

    public static boolean putValue(String k, long v) {
        mEditor.putLong(k, v);
        return mEditor.commit();
    }

    public static String getValue(String key, String def) {
        return mShared.getString(key, def);
    }

    public static Integer getValue(String key, Integer def) {
        return mShared.getInt(key, def);
    }

    public static Boolean getValue(String key, Boolean def) {
        return mShared.getBoolean(key, def);
    }

    public static Float getValue(String key, Float def) {
        return mShared.getFloat(key, def);
    }

    public static Long getValue(String key, Long def) {
        return mShared.getLong(key, def);
    }

}
