package com.cegz.api.service;

import java.util.Optional;

import com.cegz.api.model.Price;

/**
 * 套餐服务
 *
 * @author lijiaxin
 * @date 2018年7月25日
 */
public interface PriceService {
	/**
	 * 获取套餐信息
	 * @param id
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月25日
	 */
	Optional<Price> getPriceById(Long id);
}
