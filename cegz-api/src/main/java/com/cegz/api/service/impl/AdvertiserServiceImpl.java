package com.cegz.api.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cegz.api.dao.AdvertisementRepository;
import com.cegz.api.dao.AdvertisementTypeRepository;
import com.cegz.api.dao.AdvertiserRepository;
import com.cegz.api.dao.CheckMoneyRepository;
import com.cegz.api.dao.OrderRepository;
import com.cegz.api.dao.WalletRepository;
import com.cegz.api.model.Advertisement;
import com.cegz.api.model.AdvertisementType;
import com.cegz.api.model.Advertiser;
import com.cegz.api.model.CheckMoney;
import com.cegz.api.model.Order;
import com.cegz.api.model.Wallet;
import com.cegz.api.service.AdvertiserService;

/**
 * 广告方接口实现类
 * 
 * @author lijiaxin
 * @date 2018年7月24日
 */
@Service("advertiserService")
@Transactional
public class AdvertiserServiceImpl implements AdvertiserService {

	@Autowired
	private AdvertiserRepository advertiserRepository;

	@Autowired
	private AdvertisementRepository advertisementRepository;

	@Autowired
	private AdvertisementTypeRepository adverTypeRepository;

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CheckMoneyRepository checkMoneyRepository;

	@Override
	public int save(Advertiser advertiser) {
		advertiserRepository.save(advertiser);
		return 1;
	}

	@Override
	public Optional<AdvertisementType> getTypeById(Long id) {
		return adverTypeRepository.findById(id);
	}

	@Override
	public int publishAdver(List<Advertisement> listAdver, List<Order> listOrder, Wallet wallet) {
		// 广告
		for (int i = 0; i < listAdver.size(); i++) {
			advertisementRepository.save(listAdver.get(i));
		}
		// 订单
		for (int j = 0; j < listOrder.size(); j++) {
			Order order = orderRepository.save(listOrder.get(j));
			// 设置账单
			CheckMoney check = new CheckMoney();
			check.setCreateTime(new Date());
			check.setCreateUserId(order.getCreateUserId());
			check.setOrder(order);
			check.setMoney(-order.getRealMoney());
			// 账单类型 1 支出， 2 收入
			check.setType(1);
			checkMoneyRepository.save(check);
		}
		walletRepository.save(wallet);
		return 1;
	}

	@Override
	public Optional<Order> getOrderById(Long id) {
		return orderRepository.findById(id);
	}

	@Override
	public Optional<Advertiser> getAdvertiserById(Long id) {
		return advertiserRepository.findById(id);
	}

	@Override
	public List<Order> listOrder(Long createUserId) {
		return orderRepository.listOrderByLimit(createUserId);
	}
}
