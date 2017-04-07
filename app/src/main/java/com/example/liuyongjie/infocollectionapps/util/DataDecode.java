package com.example.liuyongjie.infocollectionapps.util;

/**
 * copy from DataSwitch 模块中也需要解密算法，使用相同的解密算法
 * @author  niyongliang on 2016/3/23.
 */
public class DataDecode {
    private final static byte[] key = ("lsfiuaefuaed;lsduuv[oeropareiura3jrlakrj270q93284lka?><?<}{Q{!@@)#(#&^$%@&@(#^@&I&DWUEWEEwe" +
            "2$^(J456{}|?@#$FTEE@#$@#$By^NiBaoGang").getBytes();
    private long index;

    public void skip(long byteCount){
        index+=byteCount;
    }

    public int switchData(int b){
        return  b ^ key[(int)(index++ % key.length)];
    }

    public void switchData(byte[] data,int offset,int count){
        int end=offset+count;
        for (int i = offset; i < end; i++) {
            data[i] = (byte) (data[i] ^ key[(int)(index++ % key.length)]);
        }
    }
}
