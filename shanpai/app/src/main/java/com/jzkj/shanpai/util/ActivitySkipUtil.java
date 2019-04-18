package com.jzkj.shanpai.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * author : huangyi
 * date   : 2018/9/27 10:28
 * email  : huangyi@jzkj.com
 * info   :
 */

public class ActivitySkipUtil {

    public static void goActivity(Context context, Class<? extends Activity> classname) {
        goActivity(context, classname, null);
    }

    public static void goActivity(Context context, Class<? extends Activity> classname, Bundle bundle) {
        Intent intent = new Intent(context, classname);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
    public static void goActivity(Context context, Intent it) {
        if (it != null) {
            context.startActivity(it);
        }
    }

}
