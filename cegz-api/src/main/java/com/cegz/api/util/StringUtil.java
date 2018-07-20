package com.cegz.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字符串处理工具
 * @author lijiaxin
 * @date 2018年7月19日
 */
public class StringUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);

	/**
	 * 将yyyyMMddHHmmssMM时间从小时起转换成毫秒
	 * 
	 * @param time
	 * @return
	 */
	public static long timeToMsSecond(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date;
		try {
			date = sdf.parse(time);
			return date.getTime();
		} catch (ParseException e) {
			LOGGER.info(e.getMessage(), e);
		}
		return 0;
	}

	private static final Pattern VERSION_CHECK = Pattern.compile("^[0-9]+.[0-9]+.[0-9]+$");

	/**
	 * 检查当版本号，如果版本号正确，则返回下一次的版本号
	 * 
	 * @param version
	 *            版本号格式：0.0.0
	 * @return
	 */
	public static String versionCheck(String version) {
		Pattern pattern = VERSION_CHECK;
		boolean isValid = pattern.matcher(version).matches();
		if (isValid) {
			String[] versions = version.split("\\.");
			int lastNum = Integer.valueOf(versions[2]);
			if (++lastNum > 10) {
				int midNum = Integer.valueOf(versions[1]);
				if (++midNum > 10) {
					return (Integer.valueOf(versions[0]) + 1) + ".0.0";
				}
				return versions[0] + "." + midNum + ".0";
			}
			return versions[0] + "." + versions[1] + "." + lastNum;
		} else {
			return null;
		}
	}

	// 获取小时
	public static int getHour(long second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(second);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	// 将对象转化为字符串
	public static String dataToString(Object object) {
		if (object == null) {
			return "0";
		}
		return String.valueOf(object);
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isEmpty(String data) {
		if (null == data || data.trim().length() == 0 || "null".equals(data)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断数据字符数组是否为空，如果为空则返回字符串为空的名字 字符串名字以逗号隔开
	 * 
	 * @param stringNames
	 * @param datas
	 * @return
	 */
	public static String isEmpty(String stringNames, String[] datas) {
		String[] names = stringNames.split(",");
		if (names.length > datas.length) {
			return names[datas.length];
		}
		for (int i = 0; i < names.length; i++) {
			if (isEmpty(datas[i])) {
				return names[i];
			}
		}
		return null;
	}

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");

	// 邮箱地址
	public static boolean isEmail(String s) {
		if (s.length() > 30) {
			return false;
		}
		Pattern pattern = EMAIL_PATTERN;
		Matcher isNum = pattern.matcher(s);
		return isNum.matches();
	}

	private static final Pattern MOBILE_PATTERN = Pattern
			.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}");

	/**
	 * 验证手机号码格式是否正确
	 * 
	 * @param mobiles
	 * @return true 表示正确 false表示不正确
	 */
	public static boolean isMobile(String mobile) {
		Pattern p = MOBILE_PATTERN;
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^([0-9]{4}|[0-9]{3})-[0-9]{6,}");

	/**
	 * 验证座机电话号码
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isPhoneNumber(String phone) {
		// 判断长度
		if (phone.length() > 20) {
			return false;
		}
		Pattern p = PHONE_NUMBER_PATTERN;
		Matcher m = p.matcher(phone);
		return m.matches();
	}

	// 完整的判断中文汉字和符号
	public static boolean isHasChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];

			Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
			if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
					|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
					|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
					|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
					|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
					|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
					|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
				return true;
			}

		}
		return false;
	}

	/**
	 * 
	 * 验证邮编
	 * 
	 * @param postCode
	 * @return
	 */
	public static boolean isPostCode(String postCode) {
		return postCode.matches("([0-9]-)*[0-9]{6,}");
	}

	// 判断包含特殊字符
	public static boolean isHasSepcChar(String strName) {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(strName);
		if (m.replaceAll("").trim().length() < strName.length()) {
			return true;
		} else {
			return false;
		}
	}

	// 判断字符串是否只包含中文
	public static boolean isOnlyChinese(String strName) {
		String regex = "^[\u4E00-\u9FFF()]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(strName);
		return matcher.matches();
	}
	/**
	 * 判断是否包含中文
	 * @author lijiaxin
	 * @createDate 2018年4月12日上午11:55:54
	 * @param str
	 * @return
	 * @check
	 */
	public static boolean isContainChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;		
	}
	// 判断是否是身份证号码
	public static boolean isIDCardNum(String idStr) {
		// 转化为大写字母
		idStr = idStr.toUpperCase();

		String ai = "";
		String[] valCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
		String[] wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
		// 长度检测
		if (idStr.length() != 15 && idStr.length() != 18) {
			// 身份证号码长度应该为15位或18位。
			return false;
		}
		// 身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。
		if (idStr.length() == 18) {
			ai = idStr.substring(0, 17);
		} else if (idStr.length() == 15) {
			ai = idStr.substring(0, 6) + "19" + idStr.substring(6, 15);
		}
		if (isNumeric(ai) == false) {
			return false;
		}
		// 检测日期
		// 年份
		String strYear = ai.substring(6, 10);
		// 月份
		String strMonth = ai.substring(10, 12);
		// 月份
		String strDay = ai.substring(12, 14);
		if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
			// 身份证生日无效。
			return false;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				// 身份证生日不在有效范围。
				return false;
			}
		} catch (NumberFormatException e) {
			LOGGER.info(e.getMessage(), e);
		} catch (java.text.ParseException e) {
			LOGGER.info(e.getMessage(), e);
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			// 身份证月份无效
			return false;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			// 身份证日期无效
			return false;
		}
		// 判断地区码
		@SuppressWarnings("rawtypes")
		Hashtable h = getAreaCode();
		if (h.get(ai.substring(0, 2)) == null) {
			// 身份证地区编码错误。
			return false;
		}
		// 判断最后一位的值
		int totalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			totalmulAiWi = totalmulAiWi + Integer.parseInt(String.valueOf(ai.charAt(i))) * Integer.parseInt(wi[i]);
		}
		int modValue = totalmulAiWi % 11;
		String strVerifyCode = valCodeArr[modValue];
		ai = ai + strVerifyCode;

		if (idStr.length() == 18) {
			if (ai.equals(idStr) == false) {
				// 身份证无效，不是合法的身份证号码
				return false;
			}
		} else {
			return true;
		}

		return true;
	}

	private static final Pattern DATE_PATTERN = Pattern.compile(
			"^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");

	// 判断是否为日期 以空格/中划线/斜杠分隔
	public static boolean isDate(String strDate) {
		Pattern pattern = DATE_PATTERN;
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	private static final Pattern TIME_PATTERN = Pattern.compile(
			"^(20[123][0-9])((0[1-9])|(1[012]))((0[1-9])|([12][0-9])|(3[01]))((0[0-9])|(1[0-9])|(2[0-3]))([0-5][0-9])([0-5][0-9])$");

	/**
	 * 验证时间有效性，时间格式：yyyyMMddHHmmss
	 * 
	 * @param time
	 * @return
	 */
	public static boolean isTime(String time) {
		if (time.length() != 14) {
			return false;
		}
		// 年 (20[123][0-9])
		// 月 ((0[1-9])|(1[012]))
		// 日 ((0[1-9])|([12][0-9])|(3[01]))
		// 时 ((0[0-9])|(1[0-9])|(2[0-3]))
		// 分 ([0-5][0-9])
		// 秒 ([0-5][0-9])
		Pattern pattern = TIME_PATTERN;
		Matcher m = pattern.matcher(time);
		return m.matches();
	}

	/**
	 * 身份证地区编码
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Hashtable getAreaCode() {
		Hashtable hashtable = new Hashtable();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	private static final Pattern NUMERIC_PATTERN = Pattern.compile("[0-9]*");

	// 判断是否为数字
	public static boolean isNumeric(String str) {
		Pattern pattern = NUMERIC_PATTERN;
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	private static final Pattern ALL_NUMBER_PATTERN = Pattern.compile("-?[0-9]+.*[0-9]*");

	/**
	 * 判断是小数正数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isAllNumber(String str) {
		Pattern pattern = ALL_NUMBER_PATTERN;
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}


	public static boolean isObjectEmpty(Object object) {
		if (null == object) {
			return true;
		}
		return false;
	}
	
	/**
	 * 验证参数是否为空
	 * @author lijiaxin
	 * @createDate 2018年3月30日上午10:53:56
	 * @param strs
	 * @return true 不为空， false 为空
	 * @check
	 */
	public static boolean vaildParamEmpty(String [] strs) {
		boolean flag = false;
		if (strs != null && strs.length > 0) {
			flag = true;
			for (int i = 0; i < strs.length; i++) {
				if (StringUtil.isEmpty(strs[i])) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 获取第一个参数为空的位置
	 * @author lijiaxin
	 * @createDate 2018年4月3日下午3:18:21
	 * @param strs
	 * @return
	 * @check
	 */
	public static int paramEmptyPosition(String [] strs) {
		int offset = 0;
		if (strs != null && strs.length > 0) {
			for (int i = 0; i < strs.length; i++) {
				if (StringUtil.isEmpty(strs[i])) {
					offset = i;
					break;
				}
			}
		}
		return offset;
	}

}
