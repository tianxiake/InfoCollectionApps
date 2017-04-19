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
    public boolean saveBySharePrefrence(Context context, String name, Map<String, Object> data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_APPEND);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        Set<String> keySet = data.keySet();
        for (String key : keySet) {
            Object o = data.get(key);
            Class clazz = o.getClass();
            if (clazz == String.class) {
                String value = (String) o;
                edit.putString(key, value);
            } else if (clazz == int.class) {
                int value = (int) o;
                edit.putInt(key, value);
            } else if (clazz == boolean.class) {
                boolean value = (boolean) o;
                edit.putBoolean(key, value);
            } else if (clazz == long.class) {
                long value = (long) o;
                edit.putLong(key, value);
            } else if (clazz == float.class) {
                float value = (float) o;
                edit.putFloat(key, value);
            } else {
                continue;
            }
        }
        return edit.commit();
    }
}
