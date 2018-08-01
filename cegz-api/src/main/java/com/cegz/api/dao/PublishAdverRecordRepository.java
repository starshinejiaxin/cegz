package com.cegz.api.dao;

import java.util.Date;
import java.util.List;

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
	
	@Query(value = "select count(id) from publish_advertisement_device where is_deleted = 0 and device_id = ?1", nativeQuery = true)
	int countDataByDevices(Long id);
	List<PublishAdverRecord> findByDeviceIdAndIsDeleted(Long id, byte isDeleted);
}
