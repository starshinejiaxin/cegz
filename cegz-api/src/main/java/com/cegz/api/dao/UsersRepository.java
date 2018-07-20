package com.cegz.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cegz.api.model.Users;

/**
 * 用户持久化服务
 * @author lijiaxin
 * @date 2018年7月20日
 */
public interface UsersRepository extends JpaRepository<Users, Long>{
	int countByUserNameAndPassword(String userName, String password);
	Users findByUserName(String userName);
}
