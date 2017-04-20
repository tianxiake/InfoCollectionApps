package com.example.liuyongjie.infocollectionapps.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * Created by liuyongjie on 2017/4/19.
 */

public class SharePrefenceUtil {
    /**
     * 通过偏好设置存储文件,存储的数据放在Map中,Map的value支持的类型为int,boolean,String,long,float,其他类型会被丢弃
     *
     * @param context
     * @return
     */
    public static boolean saveBySharePrefrence(Context context, String name, Map<String, Object> data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        Set<String> keySet = data.keySet();
        for (String key : keySet) {
            Object o = data.get(key);
            Class clazz = o.getClass();
            if (clazz == String.class) {
                String value = (String) o;
                edit.putString(key, value);
            } else if (clazz == Integer.class) {
                int value = (int) o;
                edit.putInt(key, value);
            } else if (clazz == Boolean.class) {
                boolean value = (boolean) o;
                edit.putBoolean(key, value);
            } else if (clazz == Long.class) {
                long value = (long) o;
                edit.putLong(key, value);
            } else if (clazz == Float.class) {
                float value = (float) o;
                edit.putFloat(key, value);
            } else {
                continue;
            }
        }
        return edit.commit();
    }
}
