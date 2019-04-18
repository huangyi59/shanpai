package com.jzkj.shanpai.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * author : huangyi
 * date   : 2018/9/27
 * email  : huangyi@jzkj.com
 * info   : 获取设备、应用相关信息
 */

public class DeviceUtil {

    /**
     * 获取app最新更新时间
     *
     * @return
     */
    public static long getLastInstallApp(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        for (PackageInfo info : packageInfos) {
            if (info.packageName.equals(packageName)) {
                return info.lastUpdateTime;
            }
        }
        return -1;
    }

}
