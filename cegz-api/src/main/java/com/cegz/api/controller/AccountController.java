package com.cegz.api.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

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
import com.cegz.api.util.Md5Util;
import com.cegz.api.util.ResultData;
import com.cegz.api.util.StringUtil;
import com.cegz.api.util.TokenUtil;

/**
 * 账号控制类
 * 
 * @author lijiaxin
 * @date 2018年7月19日
 */
@RestController
@RequestMapping("/user")
public class AccountController {
	@Autowired
	private ServerAck serverAck;

	@Autowired
	private AccountService accountService;

	/**
	 * 版本号
	 */
	@Value("${server.version}")
	private String serverVersion;

	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 账号注册
	 * 
	 * @param userName
	 * @param password
	 * @param phone
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月19日
	 */
	@PostMapping("regist")
	public ResultData regist(String userName, String password, String code, String version) {

		if (StringUtil.isEmpty(version)) {
			return serverAck.getParamError().setMessage("版本号不能为空");
		}
		if (serverVersion != null && !serverVersion.equals(version)) {
			return serverAck.getParamError().setMessage("版本错误");
		}
		if (StringUtil.isEmpty(userName)) {
			return serverAck.getParamError().setMessage("账号不能为空");
		}
		if (!StringUtil.isMobile(userName)) {
			return serverAck.getParamError().setMessage("用户名必须为手机号");
		}
		if (StringUtil.isEmpty(password)) {
			return serverAck.getParamError().setMessage("密码不能为空");
		}
		if (password.length() < 6) {
			return serverAck.getParamError().setMessage("密码长度不能小于6位");
		}
		if (StringUtil.isEmpty(code)) {
			return serverAck.getParamError().setMessage("验证码不能为空");
		}
		try {
			// 验证验证码
			String vaildCode = (String) redisUtil.get(userName);
			if (!code.equals(vaildCode)) {
				return serverAck.getParamError().setMessage("验证码错误");
			}
			// 验证账号是否存在
			userName = userName.trim();
			password = password.trim();
			Users userStatus = accountService.getUserByName(userName);
			if (userStatus != null) {
				return serverAck.getParamError().setMessage("账号已经存在");
			}
			// 数据处理
			Users user = new Users();
			user.setUserName(userName);
			String encodePassword = Md5Util.EncoderByMd5(password);
			user.setPassword(encodePassword);
			user.setPhone(userName);
			user.setCreateTime(new Date());
			// 生成uuid
			String uuid = TokenUtil.getUUID();
			user.setUuid(uuid);
			// 数据存储
			int ret = accountService.regist(user);
			if (ret != 0) {
				return serverAck.getSuccess();
			}
			return serverAck.getFailure();
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}

	}

	/**
	 * 登陆
	 * 
	 * @param userName
	 * @param password
	 * @param session
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月20日
	 */
	@PostMapping("login")
	public ResultData login(String userName, String password, String version, HttpSession session) {
		if (StringUtil.isEmpty(version)) {
			return serverAck.getParamError().setMessage("版本号不能为空");
		}
		if (serverVersion != null && !serverVersion.equals(version)) {
			return serverAck.getParamError().setMessage("版本错误");
		}
		if (StringUtil.isEmpty(userName)) {
			return serverAck.getParamError().setMessage("账号不能为空");
		}
		if (StringUtil.isEmpty(password)) {
			return serverAck.getParamError().setMessage("密码不能为空");
		}
		try {
			password = Md5Util.EncoderByMd5(password).trim();
			int ret = accountService.login(userName.trim(), password);
			if (ret != 0) {
				// 登陆成功
				session.setAttribute(Constant.SESSION_KEY, userName);
				// 获取token
				String token = TokenUtil.getToken(Constant.DES_KEY, userName);
				return serverAck.getSuccess().setMessage("登陆成功").setData(token);
			}
			return serverAck.getFailure().setMessage("登陆失败，账号或密码有误");
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}

	/**
	 * 登出
	 * 
	 * @param userName
	 * @param password
	 * @param session
	 * @return
	 * @author tenglong
	 * @date 2018年8月3日
	 */
	@PostMapping("loginOut")
	public ResultData loginOut(String version, HttpSession session) {
		if (StringUtil.isEmpty(version)) {
			return serverAck.getParamError().setMessage("版本号不能为空");
		}
		if (serverVersion != null && !serverVersion.equals(version)) {
			return serverAck.getParamError().setMessage("版本错误");
		}
		try {
//			Object object = session.getAttribute(Constant.SESSION_KEY);
//			if(object == null) {
//				return serverAck.getEmptyData().setMessage("该账号已登出，请勿重复提交");
//			}
			session.removeAttribute(Constant.SESSION_KEY);
			return serverAck.getFailure().setMessage("登出成功");
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}

	/**
	 * 修改密码
	 * 
	 * @param userName
	 * @param validateCode    验证码
	 * @param passWord
	 * @param confirmPassWord 确认密码
	 * @return
	 * @author tenglong
	 * @date 2018年8月3日
	 */
	@PostMapping("updatePassWord")
	public ResultData updatePassWord(String userName, String validateCode, String passWord, String confirmPassWord,
			String version, HttpSession session) {
		if (StringUtil.isEmpty(version)) {
			return serverAck.getParamError().setMessage("版本号不能为空");
		}
		if (serverVersion != null && !serverVersion.equals(version)) {
			return serverAck.getParamError().setMessage("版本错误");
		}
		if (StringUtil.isEmpty(userName)) {
			return serverAck.getParamError().setMessage("账号不能为空");
		}
		if (StringUtil.isEmpty(validateCode)) {
			return serverAck.getParamError().setMessage("验证码不能为空");
		}
		if (StringUtil.isEmpty(passWord)) {
			return serverAck.getParamError().setMessage("密码不能为空");
		}
		if (StringUtil.isEmpty(confirmPassWord)) {
			return serverAck.getParamError().setMessage("确认密码不能为空");
		}
		if (!passWord.equals(confirmPassWord)) {
			return serverAck.getParamError().setMessage("密码和确认密码不一致");
		}
		try {
			Users user = accountService.getUserByName(userName);
			if (user == null) {
				return serverAck.getEmptyData().setMessage("手机号未注册");
			}
			Object object = redisUtil.get(userName);
			if (object == null) {
				return serverAck.getEmptyData().setMessage("修改失败，请重新获取验证码");
			}
			if (!validateCode.equals(object)) {
				return serverAck.getFailure().setMessage("验证码错误");
			}
			passWord = Md5Util.EncoderByMd5(passWord).trim();
			user.setPassword(passWord);
			int ret = accountService.regist(user);
			if (ret != 0) {
				session.removeAttribute(Constant.SESSION_KEY);
				return serverAck.getSuccess().setMessage("操作成功");
			}
			return serverAck.getFailure().setMessage("操作失败，账号异常，请联系管理员");
		} catch (Exception e) {
			e.printStackTrace();
			return serverAck.getServerError();
		}
	}
}
