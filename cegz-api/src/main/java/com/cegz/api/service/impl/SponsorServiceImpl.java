package com.cegz.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cegz.api.dao.SponsorRepository;
import com.cegz.api.model.Sponsor;
import com.cegz.api.service.SponsorService;

/**
 * 保荐方服务
 * 
 * @author lijiaxin
 * @date 2018年7月23日
 */
@Service("sponsorService")
public class SponsorServiceImpl implements SponsorService {
	@Autowired
	private SponsorRepository sponsorRepository;

	@Override
	public Optional<Sponsor> getSponSorById(Long id) {
		return sponsorRepository.findById((Long) id);
	}

	@Override
	public int save(Sponsor sponsor) {
		sponsorRepository.save(sponsor);
		return 1;
	}

	@Override
	public List<Sponsor> listSponsor() {
		return sponsorRepository.findAll();
	}

	@Override
	public List<Sponsor> listSponsorExamine() {
		return sponsorRepository.getSponsorExamineList();
	}

}
