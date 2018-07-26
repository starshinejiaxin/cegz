package com.cegz.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cegz.api.dao.UsersRepository;
import com.cegz.api.model.Users;
import com.cegz.api.service.AccountService;
/**
 * 账号服务
 * @author lijiaxin
 * @date 2018年7月19日
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Autowired
	private UsersRepository usersRepository;
	@Override
	public int regist(Users user) {
		 usersRepository.save(user);
		 return 1;
	}
	@Override
	public int login(String userName, String password) {
		return usersRepository.countByUserNameAndPassword(userName, password);
	}
	@Override
	public Users getUserByName(String userName) {
		return usersRepository.findByUserName(userName);
	}
	
}
