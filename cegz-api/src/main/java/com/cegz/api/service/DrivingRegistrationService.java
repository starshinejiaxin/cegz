package com.cegz.api.service;

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
}
