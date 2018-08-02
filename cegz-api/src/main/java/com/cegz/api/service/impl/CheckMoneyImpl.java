package com.cegz.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cegz.api.dao.CheckMoneyRepository;
import com.cegz.api.model.CheckMoney;
import com.cegz.api.service.CheckMoneyService;

/**
 * 账单明细服务
 * 
 * @author tenglong
 * @date 2018年8月2日
 */
@Service("checkMoneyService")
public class CheckMoneyImpl implements CheckMoneyService {
	@Autowired
	private CheckMoneyRepository checkMoneyRepository;

	@Override
	public List<CheckMoney> listCheckMoneyByUserId(Long userId) {
		return checkMoneyRepository.listCheckMoneyByUserId(userId);
	}

}
