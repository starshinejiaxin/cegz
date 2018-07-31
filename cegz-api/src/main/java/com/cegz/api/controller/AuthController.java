package com.cegz.api.controller;
/**
 * 权限控制类
 *
 * @author lijiaxin
 * @date 2018年7月26日
 */

import java.util.HashMap;
import java.util.Map;

import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cegz.api.config.pojo.ServerAck;
import com.cegz.api.model.Users;
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
	@GetMapping("getCode")
	/**
	 * 获取code
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
			Map<String, Object> paramMap = new HashMap<>(16);
			paramMap.put("account", account);
			paramMap.put("pswd", pwd);
			paramMap.put("mobile", phone);
			paramMap.put("needstatus", needStatus);
			paramMap.put("msg", "您的验证码是123456");
			String ret = URLConnectionUtil.doGet(messageUrl, paramMap);
			System.out.println(ret);
			return serverAck.getSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
		
	}
}
