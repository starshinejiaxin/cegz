package com.cegz.api.system.service;

import java.util.List;

import com.cegz.api.model.Sponsor;

/**
 * 保荐方后台服务
 * 
 * @author tenglong
 * @date 2018年7月30日
 */
public interface SponsorSystemService {

	/**
	 * 获取保健方未审核列表
	 * 
	 * @return
	 * @author tenglong
	 * @date 2018年7月30日
	 */
	List<Sponsor> listSponsorExamine();
}
