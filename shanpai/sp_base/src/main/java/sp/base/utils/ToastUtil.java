package sp.base.utils;

import android.view.Gravity;
import android.widget.Toast;

import sp.base.BaseApplication;

/**
 * author : huangyi
 * date   : 2018/11/11 18:00
 * email  : huangyi@jzkj.com
 * info   : Toast工具类
 */
public class ToastUtil {

    private static Toast mToast;

    public static void toastCenter(String message){
        if(mToast == null){
            mToast = Toast.makeText(BaseApplication.getApplication(),message,Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER,0,0);
        }else{
            mToast.setText(message);
        }
        mToast.show();
    }

}
