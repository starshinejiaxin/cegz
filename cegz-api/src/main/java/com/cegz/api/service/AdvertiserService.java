package com.cegz.api.service;

import com.cegz.api.model.Advertiser;

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
}
