package com.example.liuyongjie.infocollectionapps.util;

import android.util.Base64;

import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;
import com.example.liuyongjie.infocollectionapps.log.util.Business;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * xuliang
 */
public class RSAUtil {
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    private static final String KEY_FACTORY = "RSA";
    private static final String KEY_CIPHER = "RSA/ECB/PKCS1Padding";
    public static final String CHARSET = "UTF-8";
    private static ILogger log = LoggerFactory.getLogger("RSAUtil");
    private static final String public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTALK7WxEK0q79k355T1WWyqPiIEMstWgDt9Z/zRXrjjTrbha4XePkjeG0ssn8sqpT9yIIHD3II/7LHki8uixgmWogWaAGUArtJ24ICllCUcIytEG5eWQ9SPK3NTgT8w1jOxmKTosWqrTPUA98fbz8o+mfnfjruHN80BxWSzCpBwIDAQAB";
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;

    public static String getPublicKeyStr() {
        return public_key;
    }

    /**
     * RSA验签名检查
     *
     * @param content    待签名数据
     * @param sign       签名值
     * @param public_key 公钥
     * @return 布尔值
     */
    public static boolean verify(String content, String sign, String public_key) {
        try {
            log.verbose(Author.liuyongjie, Business.dev_test, "verify {} {} {}", content, sign, public_key);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decode(public_key, Base64.DEFAULT);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(CHARSET));
            return signature.verify(Base64.decode(sign, Base64.DEFAULT));
        } catch (Exception e) {
            log.error(Author.nibaogang, e);
        }

        return false;
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
        log.verbose(Author.liuyongjie, Business.dev_test, "Algorithm:{}", cipher.getProvider().getInfo());
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
     * 得到公钥
     *
     * @param key 公钥（经过base64编码）
     * @return
     * @throws Exception
     */
    private static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes = Base64.decode(key, Base64.DEFAULT);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY);
        return keyFactory.generatePublic(x509KeySpec);
    }


    /**
     * 生成RSA密钥对对象
     */
    private static KeyPair createKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024, new SecureRandom());
        return generator.generateKeyPair();
    }

    /**
     * 生成公钥字符串
     */
    public static String createPublicStr() {
        try {
            byte[] encodeByte = createKeyPair().getPublic().getEncoded();
            return new String(Base64.encode(encodeByte, Base64.DEFAULT));
        } catch (NoSuchAlgorithmException e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

    /**
     * 生成私钥字符串
     */
    public static String createPrivateStr() {
        try {
            byte[] encodeByte = createKeyPair().getPrivate().getEncoded();
            return new String(Base64.encode(encodeByte, Base64.DEFAULT));
        } catch (NoSuchAlgorithmException e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }
}
