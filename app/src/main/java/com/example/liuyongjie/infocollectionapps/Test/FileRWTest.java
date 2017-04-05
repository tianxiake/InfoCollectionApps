package com.example.liuyongjie.infocollectionapps.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by liuyongjie on 2017/3/28.
 */

public class FileRWTest {

    public boolean writeFileUserByteStream(String content, String filePath, boolean append) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
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
                fileOutputStream = new FileOutputStream(file, append);
                //按字节流的方式去写入文件
                //            byte[] buffer = new byte[1024];
                byte[] bytes = content.getBytes();
                fileOutputStream.write(bytes);
                fileOutputStream.flush();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
        return false;
    }

    public boolean readFileToFile(String startFilePath, String endFilePath) {
        File file = new File(startFilePath);
        try {
            FileReader reader = new FileReader(file);
            FileWriter writer = new FileWriter(endFilePath);
            char[] chars = new char[1024];
            int length = 0;
            while ((length = reader.read(chars)) != -1) {
                writer.write(chars, 0, length);
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void fileTest() {
//        try {
        File file = new File("/sdcard/Android/test.txt");
//            FileOutputStream fileOutputStream = new FileOutputStream(file);
//            byte[] bytes = new byte[]{99, 100, 99, 10};
//            for (int i = 0; i < 5; i++) {
//                fileOutputStream.write(bytes);
//                fileOutputStream.write(10);
//            }
//            BufferedReader reader = new BufferedReader(new FileReader(file));
//            String string = null;
//            while ((string = reader.readLine()) != null) {
////                String string = reader.readLine();
//                Log.d("TAG", string);
//            }
//
//            PrintStream printStream = new PrintStream(new FileOutputStream(file));
//            String content = "hello,world";
//            for (int i = 0; i < 5; i++) {
//                printStream.print(content);
//                printStream.print("\n");
//            }
//            BufferedReader reader = new BufferedReader(new FileReader(file));
//            String string = null;
//            while ((string = reader.readLine()) != null) {
////                String string = reader.readLine();
//                Log.d("TAG", string);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }



}
