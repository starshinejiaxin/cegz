package com.cegz.api.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cegz.api.dao.WalletRepository;
import com.cegz.api.model.Wallet;
import com.cegz.api.system.service.WalletSystemService;

/**
 * 钱包后台服务
 *
 * @author tenglong
 * @date 2018年8月1日
 */
@Service("walletSystemService")
public class WalletSystemServiceImpl implements WalletSystemService {

	@Autowired
	private WalletRepository walletRepository;

	@Override
	public Wallet getWalletByCreateUserId(Long id) {
		return walletRepository.getWalletByCreateUserIds(id);
	}

}
