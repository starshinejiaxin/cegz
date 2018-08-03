package com.cegz.api.controller;
/**
 * 权限控制类
 *
 * @author lijiaxin
 * @date 2018年7月26日
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cegz.api.config.pojo.ServerAck;
import com.cegz.api.model.Users;
import com.cegz.api.redis.RedisUtil;
import com.cegz.api.service.AccountService;
import com.cegz.api.util.Constant;
import com.cegz.api.util.ResultData;
import com.cegz.api.util.StringUtil;
import com.cegz.api.util.TokenUtil;
import com.cegz.api.util.URLConnectionUtil;

@RestController
@RequestMapping("auth")
public class AuthController {

	@Autowired
	private ServerAck serverAck;

	/**
	 * 版本号
	 */
	@Value("${server.version}")
	private String serverVersion;

	/**
	 * apk版本号
	 */
	@Value("${apk.version}")
	private String apkVersion;

	@Autowired
	private AccountService accountService;

	@Value("${oss.access-key}")
	private String accessKey;

	@Value("${oss.secret-key}")
	private String secretKey;

	@Value("${oss.all-bucket}")
	private String bucket;

	@Value("${message.url}")
	private String messageUrl;

	@Value("${message.account}")
	private String account;

	@Value("${message.pwd}")
	private String pwd;

	@Value("${message.status}")
	private Boolean needStatus;

	@Value("${apk.url}")
	private String apkUrl;

	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 版本校验
	 * 
	 * @param version
	 * @return
	 */
	@PostMapping("checkApkVersion")
	public ResultData checkApkVersion(String version) {
		if (StringUtil.isEmpty(version)) {
			return serverAck.getParamError().setMessage("版本号不能为空");
		}
		if (apkVersion != null && !apkVersion.equals(version)) {
			return serverAck.getEmptyData().setMessage(apkUrl);
		}
		return serverAck.getSuccess();
	}

	@PostMapping("getUpToken")
	public ResultData getOssUpToken(String token, String version) {
		if (StringUtil.isEmpty(version)) {
			return serverAck.getParamError().setMessage("版本号不能为空");
		}
		if (serverVersion != null && !serverVersion.equals(version)) {
			return serverAck.getParamError().setMessage("版本错误");
		}
		if (StringUtil.isEmpty(token)) {
			return serverAck.getParamError().setMessage("token不能为空");
		}
		try {
			// 用户信息查询
			String str = TokenUtil.decodeToken(Constant.DES_KEY, token);
			if (StringUtil.isEmpty(str)) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String[] datas = str.split(":");
			if (datas.length < 1) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String userName = datas[0];
			Users users = accountService.getUserByName(userName);
			if (users == null) {
				return serverAck.getParamError().setMessage("token无效");
			}
			String uptoken = TokenUtil.getUpToken(accessKey, secretKey, bucket);
			return serverAck.getSuccess().setData(uptoken);

		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}

	@PostMapping("getCode")
	/**
	 * 获取code
	 * 
	 * @param phone
	 * @param version
	 * @return
	 * @author Administrator
	 * @date 2018年7月31日
	 */
	public ResultData getPhoneCode(String phone, String version) {
		if (StringUtil.isEmpty(phone)) {
			return serverAck.getParamError().setMessage("手机号不能为空");
		}
		if (!StringUtil.isMobile(phone)) {
			return serverAck.getParamError().setMessage("手机号格式有误");
		}
		if (StringUtil.isEmpty(version)) {
			return serverAck.getParamError().setMessage("版本号不能为空");
		}
		if (serverVersion != null && !serverVersion.equals(version)) {
			return serverAck.getParamError().setMessage("版本错误");
		}
		try {
			// 获取随机验证码
			Random random = new Random();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < 6; i++) {
				int part = random.nextInt(10);
				sb.append(part);
			}

			Map<String, Object> paramMap = new HashMap<>(16);
			paramMap.put("account", account);
			paramMap.put("pswd", pwd);
			paramMap.put("mobile", phone);
			paramMap.put("needstatus", needStatus);
			paramMap.put("msg", "您的验证码是" + sb + ",在10分钟内有效。非本人操作请忽略本短信");
			String ret = URLConnectionUtil.doGet(messageUrl, paramMap);
			redisUtil.set(phone, sb.toString(), 6000);
			System.out.println(ret);
			return serverAck.getSuccess();

		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}

	}
}
