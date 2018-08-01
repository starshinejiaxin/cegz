package com.cegz.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cegz.api.model.Wallet;

/**
 * 钱包持久化接口
 * @author lijiaxin
 * @date 2018年7月24日
 */
public interface WalletRepository extends JpaRepository<Wallet, Long>{

	/**
	 * 通过钱包中创建者外键查询钱包数据
	 * @param id : 创建者id
	 * @return
	 */
	@Query(value = "select * from wallet w where 1=1 ", nativeQuery = true)
	Wallet getWalletByCreateUserIds(Long id);
}
