package com.example.liuyongjie.infocollectionapps.log.core;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.abs.AbstractLogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;


/**
 * 
 * @author NiBaoGang HuaYongWuXian 2013-9-13
 * 
 */
public class AndroidLogger extends AbstractLogger {
    private int count;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public AndroidLogger(String tag) {
        super(tag);
    }

    @Override
    protected void log(LOG_LEVEL level, String author, String tag, String message, Throwable t) {
        if(level== LOG_LEVEL.v){
            v(tag,message,t);
        }else if(level== LOG_LEVEL.d){
            d(tag,message,t);
        }else if(level== LOG_LEVEL.i){
            i(tag,message,t);
        }else if(level== LOG_LEVEL.w){
            w(tag,message,t);
        }else {
            e(author,tag,message,t);
        }
    }

    private void v(String tag, String message, Throwable t) {
        if (t != null) {
            Log.v(tag, message, t);
        } else {
            Log.v(tag, message);
        }
    }

    private void d(String tag, String message, Throwable t) {
        if (t != null) {
            Log.d(tag, message, t);
        } else {
            Log.d(tag, message);
        }
    }

    private void i(String tag, String message, Throwable t) {
        if (t != null) {
            Log.i(tag, message, t);
        } else {
            Log.i(tag, message);
        }
    }

    private void w(String tag, String message, Throwable t) {
        if (t != null) {
            Log.w(tag, message, t);
        } else {
            Log.w(tag, message);
        }
    }

    private void e(String author, String tag, String message, Throwable t) {
        try {
            if (LoggerFactory.isEnable()) {
                if (t != null) {
                    Log.e(tag, message, t);
                } else {
                    Log.e(tag, message);
                }
                if(LoggerFactory.error_caidan) {
                    final Context context = LoggerFactory.getRealContext();
                    if (context != null) {
                        final String finalFull_message = message;
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, finalFull_message, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        }catch (Exception e){
            warn(Author.liuyongjie, e);
        }
    }
}
