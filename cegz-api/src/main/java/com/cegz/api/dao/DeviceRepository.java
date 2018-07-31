package com.cegz.api.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 设备持久化
 * 
 * @author lijiaxin
 * @date 2018年7月30日
 */
import com.cegz.api.model.Device;
public interface DeviceRepository extends JpaRepository<Device, Long>{
	@Query(value = "select * from device d  where d.status = 1 limit  ?1", nativeQuery = true)
	List<Device> listDeviceByLimit(Long size);
	
	@Query(value = "select * from device d where d.imei = ?1", nativeQuery = true)
	Device getDeviceByImei(String imei);
	
	@Modifying
	@Query(value = "update device set status = ?1, update_time = ?2 where id = ?3", nativeQuery = true)
	int updateByStatus(int status, Date updateTime, Long id);
}

