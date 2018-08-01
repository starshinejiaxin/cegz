package com.cegz.api.system.service;

import com.cegz.api.model.Wallet;

/**
 * 钱包后台服务
 * 
 *
 * @author tenglong
 * @date 2018年8月1日
 */
public interface WalletSystemService {
	/**
	 * 通过钱包中创建者外键查询数据
	 */
	Wallet getWalletByCreateUserId(Long id);

}
