package com.example.liuyongjie.infocollectionapps.util;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by liuyongjie on 2017/5/2.
 */

public class PermissionUtil {

    /**
     * Android 6.0以下检查是否含有某个权限（Android6.0以下权限不是运行时的）
     *
     * @param context        上下文
     * @param permissionName 权限的名字是字符串
     * @return 包含返回true否则返回false
     */
    public boolean checkPermission(Context context, String permissionName) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = getPackageName(context);
        return PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(permissionName, packageName);

    }

    /**
     * 获取当前应用的包名
     *
     * @param context 上下文
     * @return 返回当前应用的包名
     */
    private String getPackageName(Context context) {
        return context.getPackageName();
    }
}
