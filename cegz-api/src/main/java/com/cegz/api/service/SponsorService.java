package com.cegz.api.service;

import java.util.Optional;

import com.cegz.api.model.Sponsor;
/**
 * 保荐方服务
 * @author lijiaxin
 * @date 2018年7月23日
 */
public interface SponsorService {
	/**
	 * 通过id获取数据
	 * @param id
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月23日
	 */
	Optional<Sponsor> getSponSorById(Long id);
	
	/**
	 * 添加保荐方信息
	 * @param sponsor
	 * @return
	 * @author lijiaxin
	 * @date 2018年7月24日
	 */
	int save(Sponsor sponsor);
}
