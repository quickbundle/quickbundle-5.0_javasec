package org.quickbundle.project.secure;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

public class RmCryptoHelper {
	
	private final static String DES = "DES"; 
	
	public static byte[] desEncrypt(byte[] src, byte[] key) throws Exception {
		//DES算法要求有一个可信任的随机数源 
		SecureRandom sr = new SecureRandom(); 
		// 从原始密匙数据创建DESKeySpec对象 
		DESKeySpec dks = new DESKeySpec(key); 
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成 
		// 一个SecretKey对象 
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES); 
		SecretKey securekey = keyFactory.generateSecret(dks); 
		// Cipher对象实际完成加密操作 
		Cipher cipher = Cipher.getInstance(DES); 
		// 用密匙初始化Cipher对象 
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr); 
		// 现在，获取数据并加密 
		// 正式执行加密操作 
		return cipher.doFinal(src);
	}
	
	public static byte[] desDecrypt(byte[] src, byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源 
		SecureRandom sr = new SecureRandom(); 
		// 从原始密匙数据创建一个DESKeySpec对象 
		DESKeySpec dks = new DESKeySpec(key); 
		// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成 
		// 一个SecretKey对象 
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES); 
		SecretKey securekey = keyFactory.generateSecret(dks); 
		// Cipher对象实际完成解密操作 
		Cipher cipher = Cipher.getInstance(DES); 
		// 用密匙初始化Cipher对象 
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr); 
		// 现在，获取数据并解密 
		// 正式执行解密操作 
		return cipher.doFinal(src); 
	}
	
	public static String digestString(String pass, String algorithm) throws NoSuchAlgorithmException {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			byte digest[] = md.digest(pass.getBytes("iso-8859-1"));
			return new String(Base64.encodeBase64(digest));
		} catch (IOException ioe) {
			throw new RuntimeException((new StringBuilder()).append("Fatal error: ").append(ioe).toString(), ioe);
		} 
	}
	

	/**
	 * 对密码单向加密
	 * 
	 * @param password 密码
	 * @param seed 种子
	 * @return
	 */
	public static String digestPassword(String password, String seed) {
		if(seed == null) {
			seed = "";
		}
    	//return Md5Token.getInstance().getLongToken(Md5Token.getInstance().getLongToken(password) + seed);
		return encryptDesBase64(password + seed);
//	    return password;
    }
	
	private final static String desKey = "12345678";
	
	/**
	 * DES加密 + Base64编码(URL安全)
	 * @param value
	 * @return
	 */
	public static String encryptDesBase64(String value){
		try {
			byte[] s = RmCryptoHelper.desEncrypt(value.getBytes(), desKey.getBytes());
			String base64 = Base64.encodeBase64URLSafeString(s);
			return base64;
		} catch (Exception e) {
			e.printStackTrace();
			return value;
		}
	}
	
	/**
	 * Base64解码 + DES解密 
	 * @param value
	 * @return
	 */
	public static String decryptDesBase64(String value) {
		try {
			byte[] s = Base64.decodeBase64(value);
			String result = new String(RmCryptoHelper.desDecrypt(s, desKey.getBytes()));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return value;
		}
	}
	
	/**
	 * Base64编码(URL安全)
	 * @param value
	 * @return
	 */
	public static String encryptBase64(String value){
		try {
			String base64 = Base64.encodeBase64URLSafeString(value.getBytes());
			return base64;
		} catch (Exception e) {
			e.printStackTrace();
			return value;
		}
	}
	
	/**
	 * Base64解码
	 * @param value
	 * @return
	 */
	public static String decryptBase64(String value) {
		try {
			return new String(Base64.decodeBase64(value));
		} catch (Exception e) {
			e.printStackTrace();
			return value;
		}
	}
	
}
