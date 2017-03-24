package com.example.liuyongjie.infocollectionapps.log.util;


/**
 * Created by nibaogang on 16/4/8.
 * 业务类型
 */
public enum Business {
    dev_test("研发测试"),
    stat("统计相关"),
    encryption("加密"),
    protocol("协议相关"),
    update("软件更新"),
    refresh("下拉刷新"),
    comment("评论"),
    persist("持久化操作"),
    view("自定义view"),
    download("下载");

    /**
     *
     * @param name  分类描述
     */
    private Business(String name){
    }
}
