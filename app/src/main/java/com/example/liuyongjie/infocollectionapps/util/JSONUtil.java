package com.example.liuyongjie.infocollectionapps.util;


import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;
import com.example.liuyongjie.infocollectionapps.log.util.Business;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * Created by liuyongjie on 2017/3/24.
 */

public class JSONUtil<E> {
    private static final ILogger log = LoggerFactory.getLogger("JSONUtil");

    /**
     * 给定任意一个List<E>对象，可以构建一个JSONArray对象,这个JSONArray对象中包含一个个的JSONObject对象就是List<E>中的数据
     * 其中过滤掉了编译时产生的字段和serialVersionUID和对象中的常量部分
     *
     * @param list
     * @return
     */
    public JSONArray createJSONArray(List<E> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        JSONArray jsonArray = new JSONArray();
        try {
            E e = null;
            Class clazz = null;
            log.verbose(Author.liuyongjie, Business.dev_test, "list长度{}", list.size());
            for (int i = 0; i < list.size(); i++) {
                e = list.get(i);
                clazz = e.getClass();
                Field[] fields = clazz.getDeclaredFields();
                if (fields == null) {
                    throw new NullPointerException("fields is null");
                }
                JSONObject jsonObject = new JSONObject();
                for (int j = 0; j < fields.length; j++) {
                    //过滤掉指定的字段
                    Class type = fields[j].getType();
                    if (type != String.class && type != long.class && type != int.class && type != float.class && type != double.class && type != boolean.class) {
                        continue;
                    }
                    //忽略编译产生的属性
                    if (fields[j].isSynthetic()) {
                        continue;
                    }
                    //忽略serialVersionUID
                    if ("serialVersionUID".equals(fields[j].getName())) {
                        continue;
                    }
                    fields[j].setAccessible(true);
                    if (fields[j].get(e) == null) {
                        continue;
                    }
                    if (Modifier.isFinal(fields[j].getModifiers())) {
                        continue;
                    }
                    jsonObject.put(fields[j].getName(), fields[j].get(e));
                }
                jsonArray.put(jsonObject);
            }
            return jsonArray;
        } catch (JSONException e) {
            log.error(Author.liuyongjie, e);
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

    /**
     * 反射这个对象中的字段,然后构建一个JSONObject对象
     *
     * @param object
     * @return
     */
    public JSONObject createJSONObject(Object object) {
        if (object == null) {
            return null;
        }
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null || fields.length <= 0) {
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject();
            for (int i = 0; i < fields.length; i++) {
                //过滤掉指定的字段
                Class type = fields[i].getType();
                if (type != String.class && type != long.class && type != int.class && type != float.class && type != double.class && type != boolean.class) {
                    continue;
                }
                //忽略编译产生的属性
                if (fields[i].isSynthetic()) {
                    continue;
                }
                //忽略serialVersionUID
                if ("serialVersionUID".equals(fields[i].getName())) {
                    continue;
                }
                fields[i].setAccessible(true);
                //过滤null的字段
                if (fields[i].get(object) == null) {
                    continue;
                }
                //过滤掉final字段
                if (Modifier.isFinal(fields[i].getModifiers())) {
                    continue;
                }
                jsonObject.put(fields[i].getName(), fields[i].get(object));
            }
            return jsonObject;
        } catch (IllegalAccessException e) {
            log.error(Author.liuyongjie, e);
        } catch (JSONException e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

}
