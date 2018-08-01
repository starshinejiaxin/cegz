package com.cegz.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cegz.api.model.Advertiser;

/**
 * 广告方持久化接口
 * 
 * @author lijiaxin
 * @date 2018年7月24日
 */
public interface AdvertiserRepository extends JpaRepository<Advertiser, Long> {

	/**
	 * 查询广告主审核列表
	 * 
	 * @return
	 */
	@Query(value = "select a.id, a.city, a.company, a.name, a.phone, a.status from Advertiser a where 1=1 and a.isDeleted = 0")
	public List<Advertiser> getAdvertiserExamineList();
}
