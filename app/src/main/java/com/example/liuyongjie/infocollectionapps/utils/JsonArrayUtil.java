package com.example.liuyongjie.infocollectionapps.utils;


import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by liuyongjie on 2017/3/24.
 */

public class JsonArrayUtil<E> {
    private static ILogger log = LoggerFactory.getLogger("JsonArrayUtil");

    /**
     * 给定任意一个集合，可以构建一个JSonArray对象
     *
     * @param list
     * @return
     */
    public JSONArray creatJsonArray(List<E> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray();
            E e = null;
            Class clazz = null;
            for (int i = 0; i < list.size(); i++) {
                e = list.get(i);
                clazz = e.getClass();
                Field[] fields = clazz.getDeclaredFields();
                JSONObject jsonObject = new JSONObject();
                for (int j = 0; j < fields.length; j++) {
                    //忽略编译产生的属性
                    if (fields[i].isSynthetic()) {
                        continue;
                    }
                    //忽略serialVersionUID
                    if ("serialVersionUID".equals(fields[j].getName())) {
                        continue;
                    }
                    fields[j].setAccessible(true);
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
        return jsonArray;
    }
}
