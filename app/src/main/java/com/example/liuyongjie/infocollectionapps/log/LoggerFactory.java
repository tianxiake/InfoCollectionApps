package com.example.liuyongjie.infocollectionapps.log;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.liuyongjie.infocollectionapps.log.core.AndroidLogger;
import com.example.liuyongjie.infocollectionapps.log.core.EmptyLogger;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author NiBaoGang HuaYongWuXian 2013-5-22
 */
public abstract class LoggerFactory {
    public static final boolean error_caidan = false;
    private static UACallback callback;
    private static final Map<String, ILogger> logMappings = new ConcurrentHashMap<String, ILogger>();
    private static final ILogger emptyLog = new EmptyLogger();
    private static Context realContext;
    private static boolean printLog;

    public static void initFlag(Context context) {
        try {
            SharedPreferences preferences = context.getSharedPreferences("pyramidney", Context.MODE_PRIVATE);
            printLog = preferences.getBoolean("print", false);
        } catch (Exception e) {
            e.printStackTrace();
            printLog = false;
        }
    }

    public static void setRealContext(Context realContext){
        LoggerFactory.realContext=realContext;
    }

    public static Context getRealContext() {
        if (LoggerFactory.realContext == null) {
            try {
                ClassLoader loader = Context.class.getClassLoader();
                Class<?> c = loader.loadClass("android.app.ActivityThread");
                Method m1 = c.getDeclaredMethod("currentActivityThread");
                m1.setAccessible(true);
                Object currentActivityThread = m1.invoke(null);
                Method m2 = c.getDeclaredMethod("getApplication");
                m2.setAccessible(true);
                Application application = (Application) m2.invoke(currentActivityThread);
                if (application != null) {
                    LoggerFactory.realContext = application.getApplicationContext();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return LoggerFactory.realContext;
    }

    public static boolean isEnable() {
        return Function.log || printLog;
    }

    public static UACallback getCallback() {
        return callback;
    }

    public static ILogger getLogger(String tag) {
        ILogger log = null;
        if (tag != null) {
            log = logMappings.get(tag);
            if (log == null) {
                log = new AndroidLogger(tag);
                logMappings.put(tag, log);
            }
//			return log;
//		}else {
//			return emptyLog;
        }
        return log;
    }
    /**
     * @author 宝刚
     */
    public interface UACallback {
        /**
         * 统计
         */
        void ua(String event, String[][] ua);
    }
}
