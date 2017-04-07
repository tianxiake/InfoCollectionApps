package com.example.liuyongjie.infocollectionapps.util;


import android.util.Base64;

import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


/**
 * create by liuyongjie on 2016/11/22.
 */
public class RSAUtil {
    private static ILogger log = LoggerFactory.getLogger("RSAUtil");
//    private static final String KEY_CIPHER = "RSA/ECB/PKCS1Padding";

    /**
     * 后的一个公钥对象
     *
     * @param key RSA公钥字符串
     * @return 返回一个PublicKey对象
     */
    public static PublicKey getPublicKey(String key) {
        byte[] keyBytes;
        try {
//            keyBytes = org.bouncycastle.util.encoders.Base64.decode(key);
            keyBytes = Base64.decode(key, Base64.DEFAULT);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            publicKey.toString();
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            log.error(Author.liuyongjie, e);
        } catch (InvalidKeySpecException e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

    /**
     * 返回一个私钥对象
     *
     * @param key RSA私钥字符串
     * @return f返回一个PrivateKey对象
     */
    public static PrivateKey getPrivateKey(String key) {
        byte[] keyBytes;
        try {
            keyBytes = Base64.decode(key, Base64.DEFAULT);
//            keyBytes = org.bouncycastle.util.encoders.Base64.decode(key);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException e) {
            log.error(Author.liuyongjie, e);
        } catch (InvalidKeySpecException e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

    /**
     * 加密
     *
     * @param key  加密的密钥
     * @param data 待加密的明文数据
     * @return 加密后的数据
     */
    public static byte[] encrypt(Key key, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
//            Cipher cipher = Cipher.getInstance(KEY_CIPHER);
            //Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 获得加密块大小，如:加密前数据为128个byte，而key_size=1024 加密块大小为127
            // byte,加密后为128个byte;
            // 因此共有2个加密块，第一个127 byte第二个为1个byte
            int blockSize = cipher.getBlockSize();
            int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
            int leavedSize = data.length % blockSize;
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];
            int i = 0;
            while (data.length - i * blockSize > 0) {
                if (data.length - i * blockSize > blockSize)
                    cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
                else
                    cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
                // 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到ByteArrayOutputStream中
                // ，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了OutputSize所以只好用dofinal方法。
                i++;
            }
            return raw;
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param key 解密的密钥
     * @param raw 已经加密的数据
     * @return 解密后的明文
     */
    public static byte[] decrypt(Key key, byte[] raw) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
//            Cipher cipher = Cipher.getInstance(KEY_CIPHER);
            //Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            int blockSize = cipher.getBlockSize();
            ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
            int j = 0;
            while (raw.length - j * blockSize > 0) {
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
                j++;
            }
            return bout.toByteArray();
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

    /**
     * 生成公私钥匙秘钥对对象
     */
    public static KeyPair createKeyPairs() {
        // create the keys
        KeyPairGenerator generator = null;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024, new SecureRandom());
            KeyPair pair = generator.generateKeyPair();
//            PublicKey pubKey = pair.getPublic();
//            PrivateKey priKey = pair.getPrivate();
//            byte[] pk = pubKey.getEncoded();
//            String strPubKey = new String(Base64.encode(pk, Base64.DEFAULT));
            return pair;
        } catch (Exception e) {
            log.error(Author.liuyongjie, "生成RSA公钥私钥异常", e);
        }
        return null;
    }

//    private static final String KEY_FACTORY = "RSA";
//    private static final String KEY_CIPHER = "RSA/ECB/PKCS1Padding";
//
//    /**
//     * <p> 公钥加密 </p>
//     *
//     * @param data      源数据
//     * @param publicKey 公钥（经过base64编码）
//     */
//    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
//            throws Exception {
//        PublicKey key = getPublicKey(publicKey);
//        // 对数据加密
//        Cipher cipher = Cipher.getInstance(KEY_CIPHER);
////        log.verbose(Author.niyongliang, Business.dev_test, "Algorithm:{}", cipher.getProvider().getInfo());
//        cipher.init(Cipher.ENCRYPT_MODE, key);
//        int inputLen = data.length;
//        int offSet = 0;
//        byte[] cache;
//        int i = 0;
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        // 对数据分段加密
//        while (inputLen - offSet > 0) {
//            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
//                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
//            } else {
//                cache = cipher.doFinal(data, offSet, inputLen - offSet);
//            }
//            out.write(cache, 0, cache.length);
//            i++;
//            offSet = i * MAX_ENCRYPT_BLOCK;
//        }
//        byte[] encryptedData = out.toByteArray();
//        out.close();
//        return encryptedData;
//    }


}
