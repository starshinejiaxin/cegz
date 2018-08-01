package com.cegz.api.system.service;

import java.util.Optional;

import com.cegz.api.model.DrivingRegistration;

/**
 * 行驶证后台服务
 * 
 * @author tenglong
 * @date 2018年7月31日
 */
public interface DrivingRegistrationSystemService {
	/**
	 * 获取行驶证单条数据
	 * 
	 * @return
	 * @author tenglong
	 * @date 2018年7月31日
	 */
	Optional<DrivingRegistration> getDrivingRegistrationById(Long id);

	/**
	 * 添加行驶证信息
	 * 
	 * @param drivingRegistration
	 * @return
	 * @author tenglong
	 * @date 2018年7月31日
	 */
	int save(DrivingRegistration drivingRegistration);
}
