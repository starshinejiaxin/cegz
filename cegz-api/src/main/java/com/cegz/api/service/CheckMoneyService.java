package com.cegz.api.service;

import java.util.List;

import com.cegz.api.model.CheckMoney;

/**
 * 账单明细服务
 *
 * @author tenglong
 * @date 2018年8月2日
 */
public interface CheckMoneyService {

	/**
	 * 通过用户id获取账单明细
	 * 
	 * @param userId
	 * @return
	 */
	List<CheckMoney> listCheckMoneyByUserId(Long userId);
}
