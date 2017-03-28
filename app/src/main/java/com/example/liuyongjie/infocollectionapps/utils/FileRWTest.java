package com.example.liuyongjie.infocollectionapps.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by liuyongjie on 2017/3/28.
 */

public class FileRWTest {

    public boolean writeFile(String content, String filePath, boolean append) throws IOException {
        File file = new File(filePath);
        //判断文件是否存在
        if (!file.exists()) {
            //判断文件的的父目录是否存在
            if (!file.getParentFile().exists()) {
                //不存在则创建父目录
                boolean success = file.mkdirs();
                if (!success) {
                    return false;
                }
            }
            //
            boolean success = file.createNewFile();
            if (!success) {
                return false;
            }
            //创建指向文件的流,并指定写入的方式,追加还是覆盖
            FileOutputStream fileOutputStream = new FileOutputStream(file, append);
            //按字节流的方式去写入文件
            byte[] buffer = new byte[1024];
            fileOutputStream.write(buffer);

        }

        return false;
    }

}
