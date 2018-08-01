package com.cegz.api.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cegz.api.dao.AdvertiserRepository;
import com.cegz.api.model.Advertiser;
import com.cegz.api.system.service.AdvertiserSystemService;

/**
 * 广告主后台服务
 * 
 * @author tenglong
 * @date 2018年7月31日
 */
@Service("advertiserSystemService")
@Transactional
public class AdvertiserSystemServiceImpl implements AdvertiserSystemService {
	@Autowired
	private AdvertiserRepository advertiserRepository;

	@Override
	public List<Advertiser> listAdvertiserExamine() {
		return advertiserRepository.getAdvertiserExamineList();
	}

}
