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

public class JsonArrayUtil<E> {
    private static final ILogger log = LoggerFactory.getLogger("JsonArrayUtil");

//    /**
//     * 给定任意一个List<E>对象，可以构建一个JSONArray对象,这个JSONArray对象中包含一个个的JSONObject对象就是List<E>中的数据
//     *
//     * @param list
//     * @return
//     */
//    public JSONArray getJsonArray(List<E> list, E object) {
//        if (list == null || list.isEmpty()) {
//            return null;
//        }
//        JSONArray jsonArray = new JSONArray();
//        try {
////            jsonArray = new JSONArray();
////            E e = null;
//            Class clazz = object.getClass();
//            Field[] fields = clazz.getDeclaredFields();
//            log.verbose(Author.liuyongjie, Business.dev_test, "list长度{}", list.size());
//            for (int i = 0; i < list.size(); i++) {
////                e = list.get(i);
//                JSONObject jsonObject = new JSONObject();
//                for (int j = 0; j < fields.length; j++) {
//                    //忽略编译产生的属性
//                    if (fields[j].isSynthetic()) {
//                        continue;
//                    }
//                    //忽略serialVersionUID
//                    if ("serialVersionUID".equals(fields[j].getName())) {
//                        continue;
//                    }
//                    fields[j].setAccessible(true);
//                    jsonObject.put(fields[j].getName(), fields[j].get(object));
//                }
//                jsonArray.put(jsonObject);
//            }
//            return jsonArray;
//        } catch (JSONException e) {
//            log.error(Author.liuyongjie, e);
//        } catch (Exception e) {
//            log.error(Author.liuyongjie, e);
//        }
//        return null;
//    }


    public JSONArray getJsonArray(List<E> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        JSONArray jsonArray = new JSONArray();
        try {
//            jsonArray = new JSONArray();
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

}
