package com.cegz.api.system.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cegz.api.dao.OrderRepository;
import com.cegz.api.dao.WalletRepository;
import com.cegz.api.model.Order;
import com.cegz.api.model.Wallet;
import com.cegz.api.system.service.OrderSystemService;

/**
 * 订单后台服务
 * 
 * @author tenglong
 * @date 2018年8月1日
 */
@Service("orderSystemService")
@Transactional
public class OrderSystemServiceImpl implements OrderSystemService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private WalletRepository walletRepository;

	@Override
	public int save(Order order) {
		orderRepository.save(order);

		// 审核订单时：通过--》调发布接口；不通过--》订单实际金额返回到钱包
		if (order.getStatus() == 1) {// 通过 ---> 进行中
			// 调用发布接口
			System.out.println("调用发布接口");
		} else if (order.getStatus() == 3) {// 不通过 ---> 失败
			Long id = order.getCreateUserId().getId();
			Wallet wallet = walletRepository.getWalletByCreateUserIds(id);
			wallet.setMoney(order.getRealMoney() + wallet.getMoney());
			walletRepository.save(wallet);
		}
		return 1;
	}

}
