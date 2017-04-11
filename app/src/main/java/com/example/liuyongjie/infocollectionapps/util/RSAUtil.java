package com.example.liuyongjie.infocollectionapps.util;


import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


/**
 * RSA加密工具类
 *
 * @author xuliang
 */
public class RSAUtil {

    private static final ILogger log = LoggerFactory.getLogger("RSAUtil");

    /**
     * 编码
     */
    private static final String CHARSET = "UTF-8";

    /**
     * 签名算法
     */
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    /**
     * 加密算法RSA
     */
    private static final String KEY_FACTORY = "RSA";
    private static final String KEY_CIPHER = "RSA/ECB/PKCS1Padding";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 生成钥匙对
     *
     * @throws Exception
     */
    public static void genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_FACTORY);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        System.out.println("=================== Public Key =====================");
        System.out.println(android.util.Base64.encodeToString(publicKey.getEncoded(), android.util.Base64.DEFAULT));
        System.out.println("=================== Private Key =====================");
        System.out.println(android.util.Base64.encodeToString(privateKey.getEncoded(), android.util.Base64.DEFAULT));
    }

    /**
     * RSA签名
     *
     * @param content    待签名数据
     * @param privateKey 私钥（经过base64编码）
     * @return 签名值
     */
    public static String sign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(KEY_FACTORY);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes(CHARSET));
            byte[] signed = signature.sign();
            return android.util.Base64.encodeToString(signed, android.util.Base64.DEFAULT);
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

    /**
     * RSA验签名检查
     *
     * @param content    待签名数据
     * @param sign       签名值
     * @param public_key 公钥（经过base64编码）
     * @return 布尔值
     */
    public static boolean verify(String content, String sign, String public_key) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY);
            byte[] encodedKey = Base64.decode(public_key);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(CHARSET));
            return signature.verify(Base64.decode(sign));
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }

        return false;
    }

    /**
     * 用私钥解密
     *
     * @param data       密文
     * @param privateKey 私钥（经过base64编码）
     * @return 解密后的字符串
     */
    public static byte[] decryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        PrivateKey key = getPrivateKey(privateKey);
        Cipher cipher = Cipher.getInstance(KEY_CIPHER);
        cipher.init(Cipher.DECRYPT_MODE, key);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密  
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 用公钥解密
     *
     * @param data      密文（经过base64编码）
     * @param publicKey 公钥（经过base64编码）
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        PublicKey key = getPublicKey(publicKey);
        Cipher cipher = Cipher.getInstance(KEY_CIPHER);
        cipher.init(Cipher.DECRYPT_MODE, key);
        int inputLen = data.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 对数据分段解密  
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data      源数据
     * @param publicKey 公钥（经过base64编码）
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        PublicKey key = getPublicKey(publicKey);
        // 对数据加密  
        Cipher cipher = Cipher.getInstance(KEY_CIPHER);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        int inputLen = data.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 对数据分段加密  
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data       源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
            throws Exception {
        PrivateKey key = getPrivateKey(privateKey);
        Cipher cipher = Cipher.getInstance(KEY_CIPHER);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密  
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }


    /**
     * 得到公钥
     *
     * @param key 公钥（经过base64编码）
     * @return
     * @throws Exception
     */
    private static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes = Base64.decode(key);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY);
        return keyFactory.generatePublic(x509KeySpec);
    }


    /**
     * 得到私钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    private static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes = Base64.decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY);
        return keyFactory.generatePrivate(keySpec);
    }

}
