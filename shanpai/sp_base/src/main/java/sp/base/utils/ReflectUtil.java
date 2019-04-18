package sp.base.utils;

import android.util.Log;

import java.lang.reflect.ParameterizedType;

/**
 * author : huangyi
 * date   : 2018/9/28 15:38
 * email  : huangyi@jzkj.com
 * info   : 通过反射获取类的实例
 */

public class ReflectUtil {

    private static final String TAG = "ReflectUtil";

    /**
     * ParameterizedType 表示参数化类型，如 Collection<String>
     * getActualTypeArguments()返回表示此类型实际类型参数的 Type 对象的数组
     * getGenericSuperclass()返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type
     *
     */
    public static <T> T getTypeInstance(Object o,int i){
        try{
           return ((Class<T>)((ParameterizedType)(o.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            Log.e(TAG,"反射构建实例失败");
        }
        return null;
    }

}
