package com.example.liuyongjie.infocollectionapps.util;

import android.util.Base64;

import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by liuyongjie on 2016/11/22
 */
public class LYJAESUtil {

    private static ILogger log = LoggerFactory.getLogger("LYJAESUtil");
    private static final String CIPHER_MODE = "AES/CBC/PKCS5Padding";


    /**
     * 将字符串密钥编码成Base64编码的字节数组
     *
     * @param key AES密钥
     * @return 返回一个字节数组
     * @throws Exception 编码失败
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return Base64.decode(key, Base64.DEFAULT);
    }

    /**
     * 加密
     *
     * @param keyStr AES密钥
     * @param data   要加密的数据
     * @return 返回加密后的字节数组
     */
    public static byte[] getAESEncode(String keyStr, byte[] data, byte[] iv) {
        try {
            byte[] keyByte = decryptBASE64(keyStr);
            SecretKeySpec sKeySpec = new SecretKeySpec(keyByte, "AES");
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, new IvParameterSpec(iv));
            byte[] encodeByte = cipher.doFinal(data);
            return encodeByte;
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param keyStr AES密钥
     * @param data   要解密的数据
     * @return 返回解密后的数据
     */
    public static byte[] getAESDecode(String keyStr, byte[] data, byte[] iv) {
        try {
            byte[] keyByte = decryptBASE64(keyStr);
            SecretKeySpec sKeySpec = new SecretKeySpec(keyByte, "AES");
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, new IvParameterSpec(iv));
            byte[] decodeByte = cipher.doFinal(data);
            return decodeByte;
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

    /**
     * 生成AESKey
     *
     * @return
     */
    public static String createAESKey() {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(192, new SecureRandom());
            SecretKey secretKey = generator.generateKey();
//            secretKey.getEncoded();
//            byte[] b = Base64.encode(secretKey.getEncoded(),Base64.DEFAULT);
            return new String(Base64.encode(secretKey.getEncoded(), Base64.DEFAULT));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}
