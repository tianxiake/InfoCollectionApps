package com.example.liuyongjie.infocollectionapps.log.intf;


import com.example.liuyongjie.infocollectionapps.log.util.Author;
import com.example.liuyongjie.infocollectionapps.log.util.Business;

/**
 * @author NiBaoGang
 *         HuaYongWuXian
 *         2013-5-22
 */
public interface ILogger {

    public enum LOG_LEVEL {
        v,d,i,w,e
    }
    /**
     * 啰嗦级别输出,方便研发跟踪问题
     *
     * @param author 作者
     * @param business 业务
     * @param message 消息
     * @param params  参数
     *
     * @param message the content of log
     */
    void verbose(Author author, Business business, String message, Object... params);

    /**
     * 调试级别输出,方便QA深入查看
     *
     * @param author   作者
     * @param business 业务类型
     * @param param    分类参数
     * @param message  消息
     * @param params   消息参数
     */
    void debug(Author author, Business business, Object param, String message, Object... params);

    /**
     * 信息级别的输出,方便QA一般性查看
     *
     * @param author   作者
     * @param business 业务类型
     * @param param    分类参数
     * @param message  消息
     * @param params   消息参数
     */
    void info(Author author, Business business, Object param, String message, Object... params);

    /**
     * 警告级别的输出,方便提示研发查找一些异常
     * @param author 作者
     * @param t 异常
     */
    void warn(Author author, Throwable t);

    /**
     * 警告级别的输出,方便提示研发查找一些异常
     * @param author 作者
     * @param message 消息
     * @param t 异常
     */
    void warn(Author author, String message, Throwable t);

    /**
     * 警告级别的输出,方便提示研发查找一些异常
     *
     * @param author 作者
     * @param business 业务类型
     * @param message 消息
     * @param params 参数
     */
    void warn(Author author, Business business, String message, Object... params);

    /**
     * 错误级别输出,发布之前这个级别的log都需要解决的
     *
     * @param author 作者
     * @param t 异常
     */
    void error(Author author, Throwable t);

    /**
     * 错误级别输出,发布之前这个级别的log都需要解决的
     *
     * @param author 作者
     * @param message 消息
     * @param t 异常
     */
    void error(Author author, String message, Throwable t);

    /**
     * 错误级别输出,发布之前这个级别的log都需要解决的
     *
     * @param author 作者
     * @param message 消息
     * @param params 参数
     */
    void error(Author author, String message, Object... params);
}
