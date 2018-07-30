package com.cegz.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 设备持久化
 * 
 * @author lijiaxin
 * @date 2018年7月30日
 */
import com.cegz.api.model.Device;
public interface DeviceRepository extends JpaRepository<Device, Long>{
	@Query(value = "select * from device d  where d.status = 0 limit  ?1", nativeQuery = true)
	List<Device> listDeviceByLimit(Long size);
}
