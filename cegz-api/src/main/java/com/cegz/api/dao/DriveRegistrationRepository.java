package com.cegz.api.dao;

import java.util.List;

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
	 * 
	 * @param plateNumber
	 * @author tenglong
	 * @date 2018年8月1日
	 * @return
	 */
	@Query(value = "select * from driving_registration dr where dr.plate_number = ?1", nativeQuery = true)
	DrivingRegistration getDrivingRegistrationByPlateNumber(String plateNumber);

	/**
	 * 查询订单分页
	 * 
	 * @param size
	 * @return
	 */
	@Query(value = "select * from driving_registration dr where 1=1 and sponsor_id = ?1 limit ?2, ?3", nativeQuery = true)
	List<DrivingRegistration> listDrivingRegistration(Long sponsorId, Long pageSize, Long pageCount);
}
