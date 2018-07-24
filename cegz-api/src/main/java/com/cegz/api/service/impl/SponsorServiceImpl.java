package com.cegz.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cegz.api.dao.SponsorRepository;
import com.cegz.api.model.Sponsor;
import com.cegz.api.service.SponsorService;

/**
 * 保荐方服务
 * @author lijiaxin
 * @date 2018年7月23日
 */
@Service("sponsorService")
public class SponsorServiceImpl implements SponsorService {
	@Autowired
	private SponsorRepository sponsorRepository;

	@Override
	public Sponsor getSponSorById(Long id) {
		return sponsorRepository.getOne((Long)id);
	}

	@Override
	public int save(Sponsor sponsor) {
		sponsorRepository.save(sponsor);
		return 1;
	}
	
	
}
