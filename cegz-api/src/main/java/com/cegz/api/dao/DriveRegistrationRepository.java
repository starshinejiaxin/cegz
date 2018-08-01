package com.cegz.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cegz.api.model.DrivingRegistration;

/**
 * 行驶证持久化接口
 * 
 * @author lijiaxin
 * @date 2018年7月24日
 */
public interface DriveRegistrationRepository extends JpaRepository<DrivingRegistration, Long> {

	/**
	 * 通过车牌号查找驾驶证
	 * @param plateNumber
	 * @author tenglong
	 * @date 2018年8月1日
	 * @return
	 */
	@Query(value = "select * from driving_registration dr where dr.plate_number = ?1", nativeQuery = true)
	DrivingRegistration getDrivingRegistrationByPlateNumber(String plateNumber);
}
