package com.cegz.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cegz.api.model.CheckMoney;

/**
 * 账单持久化接口
 * 
 * @author lijiaxin
 * @date 2018年7月24日
 */
public interface CheckMoneyRepository extends JpaRepository<CheckMoney, Long> {
	/**
	 * 通過用戶id獲取賬單明細列表
	 */
	@Query(value = "select * from check_money c where 1=1 and c.is_deleted = 0 and c.create_user_id = ?1", nativeQuery = true)
	public List<CheckMoney> listCheckMoneyByUserId(Long userId);
}
