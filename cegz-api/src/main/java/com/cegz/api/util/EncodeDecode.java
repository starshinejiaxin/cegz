package com.cegz.api.util;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.util.encoders.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * DES加密解密
 * @author lijiaxin
 * @date 2018年4月9日
 */
public class EncodeDecode {
	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
	  /**
	   * 加密格式处理
	   * @author lijiaxin
	   * @createDate 2018年4月9日上午11:25:30
	   * @param key
	   * @param data
	   * @return
	   * @throws Exception
	   * @check
	   */
	  public static String encode(String key,String data) 
	  {
	    try {
			return new String(encode(key, data.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	  }
	  /**
	   * DES算法，加密
	   *
	   * @param data 待加密字符串
	   * @param key 加密私钥，长度不能够小于8位
	   * @return 加密后的字节数组，一般结合Base64编码使用
	   * @throws CryptException 异常
	   */
	  public static byte[] encode(String key,byte[] data) throws Exception {
		  try {
			  DESKeySpec dks = new DESKeySpec(key.getBytes());
	  
			  SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			  //key的长度不能够小于8位字节
			  Key secretKey = keyFactory.generateSecret(dks);	
			  Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			  IvParameterSpec iv = new IvParameterSpec(key.getBytes());
			  AlgorithmParameterSpec paramSpec = iv;
			  cipher.init(Cipher.ENCRYPT_MODE, secretKey,paramSpec);
			  byte[] bytes = cipher.doFinal(data);
			  return Base64.encode(bytes);
	    } catch (Exception e) {
	    		throw new Exception(e);
	    }
	  }
	  /**
	   * DES算法，解密
	   *
	   * @param data 待解密字符串
	   * @param key 解密私钥，长度不能够小于8位
	   * @return 解密后的字节数组
	   * @throws Exception 异常
	   */
	  public static byte[] decode(String key,byte[] data) throws Exception {
		  try {
			 DESKeySpec dks = new DESKeySpec(key.getBytes());
			 SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			 //key的长度不能够小于8位字节
			 Key secretKey = keyFactory.generateSecret(dks);
			 Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			 IvParameterSpec iv = new IvParameterSpec(key.getBytes());
			 AlgorithmParameterSpec paramSpec = iv;
			 cipher.init(Cipher.DECRYPT_MODE, secretKey,paramSpec);
			 return cipher.doFinal(data);
	    } catch (Exception e) {
	    		throw new Exception(e);
	    }
	 }
	  /**
	   * 解密格式处理
	   * @author lijiaxin
	   * @createDate 2018年4月9日上午11:25:00
	   * @param key
	   * @param data
	   * @return
	   * @check
	   */
	  public static String decode(String key, String data) {
		  try {
			return new String(decode(key,Base64.decode(data.getBytes())));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	  }
	 /**
	  * 进行url编码
	  * @author lijiaxin
	  * @createDate 2018年4月9日下午2:45:37
	  * @param url
	  * @return
	  * @check
	  */
	  public static String encodeUrl(String url){
		  try {
			return URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	  }
	  /**
	   * 进行url解码
	   * @param url
	   * @return
	   */
	  public static String decodeUrl(String url){
		  try {
			return URLDecoder.decode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	  }
	  public static void main(String[] args) throws Exception
	  {
		String encodeStr = EncodeDecode.encode("!@#$%^fg","a09ef8f4-8419-4938-bf38-018722c221af1523256961156");
		encodeStr = encodeUrl(encodeStr);
		System.out.println(decodeUrl(encodeStr));
	    System.out.println("明：5KQ63dJNHjA= ；密：" + encodeStr);
	    String sign = "/XcohYs3o44x7ibjOxb6fcOZ+g+33dPCVHKnCgbE6K2AfUVn1maZIVgDIU8uVNWA";
	    System.out.println(decode("!@#$%^fg",sign));
	  }
}
