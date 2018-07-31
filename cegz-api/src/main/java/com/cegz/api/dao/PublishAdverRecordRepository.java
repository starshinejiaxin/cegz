package com.cegz.api.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cegz.api.model.PublishAdverRecord;
/**
 * 广告发布记录
 * 
 * @author lijiaxin
 * @date 2018年7月30日
 */ 
public interface PublishAdverRecordRepository extends JpaRepository<PublishAdverRecord, Long>{
	@Modifying
	@Query(value = "update publish_advertisement_device set status = ?1, update_time = ?2 where id = ?3", nativeQuery = true)
	int updateByStatus(int status, Date updateTime, Long id);
}
