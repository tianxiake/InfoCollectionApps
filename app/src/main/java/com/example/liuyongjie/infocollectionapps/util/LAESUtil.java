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
 * LAESUtil.java
 *
 * @author xuliang
 */
public class LAESUtil {

    private static final ILogger log = LoggerFactory.getLogger("LAESUtil");

    /**
     * 加密
     *
     * @param data
     * @param key
     * @param iv   iv字节长度必须是16 否则报异常 <p>java.security.InvalidAlgorithmParameterException: expected IV length of 16 but was 24</p>
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, String key, String iv) throws Exception {
        return create(Cipher.ENCRYPT_MODE, key, iv).doFinal(data);
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @param iv
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, String key, String iv) throws Exception {
        return create(Cipher.DECRYPT_MODE, key, iv).doFinal(data);
    }

    /**
     * 创建Cipher对象
     *
     * @param mode <p>java.security.InvalidKeyException: Unsupported key size: 33 bytes AES密钥的长度不能因为受编码影响</p>
     * @param key
     * @param iv
     * @return
     * @throws Exception
     */
    private static Cipher create(int mode, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = key.getBytes(); //这行注释掉的原因是，生成AES key时用的Base64编码，取得时候要先解码，否则长度就变成了
//        byte[] raw = Base64.decode(key, Base64.DEFAULT);
        SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(mode, keySpec, new IvParameterSpec(iv.getBytes()));
        return cipher;
    }

    /**
     * 生成AES key
     */
    public static String createAESKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(192, new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            return new String(Base64.encode(secretKey.getEncoded(), Base64.DEFAULT));
        } catch (NoSuchAlgorithmException e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }


}
