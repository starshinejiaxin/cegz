package com.cegz.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cegz.api.model.Order;
/**
 * 订单持久化接口
 * @author lijiaxin
 * @date 2018年7月24日
 */
public interface OrderRepository extends JpaRepository<Order, Long>{
	
}
