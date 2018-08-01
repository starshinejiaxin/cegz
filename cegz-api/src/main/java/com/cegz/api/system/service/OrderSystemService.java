package com.cegz.api.system.service;

import com.cegz.api.model.Order;

/**
 * 订单后台服务
 * 
 * @author tenglong
 * @date 2018年8月1日
 */
public interface OrderSystemService {

	/**
	 * 保存订单
	 * 
	 * @return
	 * @author tenglong
	 * @date 2018年8月1日
	 */
	int save(Order order);
}
