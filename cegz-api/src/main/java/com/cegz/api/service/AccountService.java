package com.cegz.api.service;

import com.cegz.api.model.Users;

/**
 * 账号服务
 * @author lijiaxin
 * @date 2018年7月19日
 */
public interface AccountService {
	/**
	 * 注册账号
	 * @param user
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月19日
	 */
	int regist(Users user);
	
	/**
	 * 登陆
	 * @param userName
	 * @param password
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月20日
	 */
	int login(String userName, String password);
	
	/**
	 * 获取账号记录数
	 * @param userName
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月20日
	 */
	Users getUserByName(String userName);
		
}
