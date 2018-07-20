package com.cegz.api.util;

import java.util.UUID;

/**
 * token工具
 * @author lijiaxin
 * @date 2018年7月20日
 */
public class TokenUtil {
	/**
	 * DES 加密
	 * @param key
	 * @param userName
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月20日
	 */
	public static String getToken(String key, String userName) {
		if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(key)) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(userName);
		sb.append(":");
		sb.append(System.currentTimeMillis());
		String str = sb.toString();
		return EncodeDecode.encode(key, str);
		
	}
	
	/**
	 * 解密token
	 * @param key
	 * @param token
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月20日
	 */
	public static String decodeToken(String key, String token) {
		if (StringUtil.isEmpty(token) || StringUtil.isEmpty(key)) {
			return null;
		}
		String str = EncodeDecode.decode(key, token);
		if (StringUtil.isEmpty(str)) {
			return null;
		}
		return str;
	}
	/**
	 * 校验实效性
	 * @param key
	 * @param token
	 * @param minute
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月20日
	 */
	public static boolean vaildTokenTime(String key, String token, int minute) {
		String str = decodeToken(key, token);
		if (StringUtil.isEmpty(str)) {
			return false;
		}
		String [] datas = str.split(":");
		if (datas.length != 2) {
			return false;
		}
		long times = Long.parseLong(datas[1]);
		long nowTimes = System.currentTimeMillis();
		long currentTimes = nowTimes - times;
		if (currentTimes > minute * 60 *1000) {
			return false;
		}
		return true;
	}
	
	/**
	 * 获取uuid
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月20日
	 */
	public static String getUUID() {
		 String uuid = UUID.randomUUID().toString().replaceAll("-","");  
		 return uuid;
	}
}
