package com.cegz.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cegz.api.dao.AdvertiserRepository;
import com.cegz.api.model.Advertiser;
import com.cegz.api.service.AdvertiserService;
/**
 * 广告方接口实现类
 * @author lijiaxin
 * @date 2018年7月24日
 */
@Service("advertiserService")
public class AdvertiserServiceImpl implements AdvertiserService {
	
	@Autowired
	private AdvertiserRepository advertiserRepository;

	@Override
	public int save(Advertiser advertiser) {
		advertiserRepository.save(advertiser);
		return 1;
	}
	
}
