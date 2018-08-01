package com.cegz.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cegz.api.model.Order;
/**
 * 订单持久化接口
 * @author lijiaxin
 * @date 2018年7月24日
 */
public interface OrderRepository extends JpaRepository<Order, Long>{
	
	/**
	 * 查询订单分页
	 * @param size
	 * @return
	 */
	@Query(value = "select * from order_advertiser o where 1=1 and create_user_id = ?1 limit ?2, ?3", nativeQuery = true)
	List<Order> listOrderByLimit(Long createUserId, Long pageSize, Long pageCount);
}
