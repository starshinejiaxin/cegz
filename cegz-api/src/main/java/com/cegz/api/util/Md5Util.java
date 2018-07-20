package com.cegz.api.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bouncycastle.util.encoders.Base64;

/**
 * md5加密解密工具
 * 
 * @author lijiaxin
 * @date 2018年7月19日
 */
public class Md5Util {
	
	/**
	 * md5加密
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @author lijiaxin
	 * @date 2018年7月19日
	 */
	public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// 确定计算方法
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		// 加密后的字符串
		byte[] bytes = Base64.encode(md5.digest(str.getBytes("utf-8")));
		String encodeStr = new String(bytes, "utf-8");
		return encodeStr;
	}

}
