package com.cegz.api.system.service;

import java.util.List;

import com.cegz.api.model.Advertiser;

/**
 * 广告主后台服务
 * 
 * @author tenglong
 * @date 2018年7月31日
 */
public interface AdvertiserSystemService {
	/**
	 * 获取广告主未审核列表
	 * 
	 * @return
	 * @author tenglong
	 * @date 2018年7月31日
	 */
	List<Advertiser> listAdvertiserExamine();
}
