package com.cegz.api.service;

import java.util.List;

import com.cegz.api.model.DrivingRegistration;

/**
 * 行驶证服务
 * 
 * @author tenglong
 * @date 2018年8月1日
 */
public interface DrivingRegistrationService {
	/**
	 * 通过车牌号获取行驶证数据
	 * 
	 * @param plateNumber
	 * @return
	 * @author tenglong
	 * @date 2018年8月1日
	 */
	DrivingRegistration getDrivingRegistrationByPlateNumber(String plateNumber);

	/**
	 * 通过车牌号获取行驶证数据
	 * 
	 * @return
	 * @author tenglong
	 * @date 2018年8月1日
	 */
	List<DrivingRegistration> listDrivingRegistration(Long sponsorId);
}
