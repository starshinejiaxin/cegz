package com.cegz.api.util;

/**
 * 常量类
 * @author gaowang
 * @date 2018/04/04
 */
public class Constant {

	/**
	 * 返回码：成功
	 */
	public final static int RETURN_CODE_SUCCESS = 200;
	
	/**
	 * 数据为空
	 */
	public final static int DATA_CODE_NULL = 201;
	/**
	 * 返回码：参数为空
	 */
	public final static int RETURN_CODE_NULL = 400;
	
	/**
	 * 返回码：校验错误
	 */
	public final static int RETURN_CODE_VERIFY_FAILED = 401;
	
	/**
	 * 返回码：服务器数据库异常
	 */
	public final static int RETURN_CODE_DATABASE_EXCEPTION = 500;
	
	/**
	 * 返回码：参数异常
	 */
	public final static int RETURN_CODE_PARAMETER_EXCEPTION = 501;
	
	/**
	 * session key
	 */
	public final static String SESSION_KEY = "CEGZ_USER_SESSION_KEY";
	
	/**
	 * DES密码
	 */
	public final static String DES_KEY = "QE@#WW1%";
	
	/**
	 * 静态文件地址
	 */
	public final static String STATIC_DIR = "static/";
	
	/**
	 * 身份证图片目录
	 */
	public final static String CARD_IMG_DRI = "images/idcard";
	
	/**
	 * 驾驶证图片目录
	 */
	public final static String DRIVE_LICENSE_IMG_DRI = "images/drive_license";
	
	/**
	 * 行驶证图片目录
	 */
	public final static String DRIVE_REGISTRATION_IMG_DRI = "images/drive_registration";
}
