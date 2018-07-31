package com.cegz.api.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cegz.api.dao.SponsorRepository;
import com.cegz.api.model.Sponsor;
import com.cegz.api.system.service.SponsorSystemService;

/**
 * 保荐方后台服务
 * 
 * @author tenglong
 * @date 2018年7月30日
 */
@Service("sponsorSystemService")
public class SponsorSystemServiceImpl implements SponsorSystemService {
	@Autowired
	private SponsorRepository sponsorRepository;

	@Override
	public List<Sponsor> listSponsorExamine() {
		return sponsorRepository.getSponsorExamineList();
	}

}
