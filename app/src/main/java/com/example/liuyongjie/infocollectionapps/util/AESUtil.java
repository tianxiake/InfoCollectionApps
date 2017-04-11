package com.example.liuyongjie.infocollectionapps.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



/**
 * AESUtil.java
 * 
 * @author xuliang
 */
public class AESUtil {
	
	/**
	 * 加密算法 AES
	 */
	private static final String KEY_FACTORY = "AES";  
	private static final String KEY_CIPHER = "AES/CBC/PKCS5Padding";  
//	private static final String KEY_CIPHER = "AES/CBC/NoPadding";  

	/**
	 * 加密
	 * @param data	要加密的数据
	 * @param key	加密使用的秘钥
	 * @param iv	加密使用的向量
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, String key, String iv) throws Exception {
        return create(Cipher.ENCRYPT_MODE, key, iv).doFinal(data);
	}

	/**
	 * 解密
	 * @param data	要解密的数据
	 * @param key	解密使用的秘钥
	 * @param iv	解密使用的向量
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, String key, String iv) throws Exception {
        return create(Cipher.DECRYPT_MODE, key, iv).doFinal(data);
	}
	
	/**
	 * 创建Cipher对象
	 * @param mode	模式
	 * @param key	秘钥
	 * @param iv	向量
	 * @return
	 * @throws Exception
	 */
	private static Cipher create(int mode, String key, String iv) throws Exception {
		Cipher cipher = Cipher.getInstance(KEY_CIPHER);
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), KEY_FACTORY);
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(mode, skeySpec, new IvParameterSpec(iv.getBytes()));
        return cipher;
	}
	
	public static void main(String[] args) throws Exception {

		byte[] data = "12345678".getBytes("UTF-8");
		String key = StringUtils.randomString(16);
		String iv = StringUtils.randomString(16);
		data = AESUtil.encrypt(data, key, iv);

		key = StringUtils.randomString(16);
		data = AESUtil.decrypt(data, key, iv);
		System.out.println(new String(data));
	}
}