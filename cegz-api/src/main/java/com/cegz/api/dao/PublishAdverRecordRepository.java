package com.cegz.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cegz.api.model.PublishAdverRecord;
/**
 * 广告发布记录
 * 
 * @author lijiaxin
 * @date 2018年7月30日
 */ 
public interface PublishAdverRecordRepository extends JpaRepository<PublishAdverRecord, Long>{
	
}
