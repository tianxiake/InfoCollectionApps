package com.example.liuyongjie.infocollectionapps.log.util;

/**
 * @author NiBaoGang
 *         HuaYongWuXian
 *         2013年11月17日
 */
public enum Author {

    liuyongjie("lyj"),
    nibaogang("nbg");

    private final String cc;

    private Author(String cc) {
        this.cc = cc;
    }

    public String getCc() {
        return cc;
    }
}
