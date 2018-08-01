package com.cegz.api.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cegz.api.dao.PriceRepository;
import com.cegz.api.model.Price;
import com.cegz.api.service.PriceService;

/**
 * 套餐服务
 *
 * @author lijiaxin
 * @date 2018年7月25日
 */
@Service("priceService")
@Transactional
public class PriceServiceImpl implements PriceService {
	@Autowired
	private PriceRepository priceRepository;

	@Override
	public Optional<Price> getPriceById(Long id) {
		return priceRepository.findById(id);
	}
	
	
}
