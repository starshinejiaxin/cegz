package com.cegz.api.service;

import java.util.List;
import java.util.Optional;

import com.cegz.api.model.Advertisement;
import com.cegz.api.model.AdvertisementType;
import com.cegz.api.model.Advertiser;
import com.cegz.api.model.Order;
import com.cegz.api.model.Wallet;

/**
 * 广告方接口
 * @author lijiaxin
 * @date 2018年7月24日
 */
public interface AdvertiserService {
	
	/**
	 * 保存广告方信息
	 * @param advertiser
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月24日
	 */
	int save(Advertiser advertiser);
	
	/**
	 * 获取广告类型
	 * @param Id
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月25日
	 */
	Optional<AdvertisementType> getTypeById(Long id);
	
	/**
	 * 发布广告
	 * @param listAdver
	 * @param listOrder
	 * @param wallet
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月25日
	 */
	int publishAdver(List<Advertisement> listAdver, List<Order> listOrder, Wallet wallet);
	
	/**
	 * 获取订单信息
	 * @param id
	 * @return
	 * @author Administrator
	 * @date 2018年7月30日
	 */
	Optional<Order> getOrderById(Long id);
}
